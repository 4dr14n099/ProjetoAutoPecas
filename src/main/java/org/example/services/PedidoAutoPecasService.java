package org.example.services;

import org.example.entities.PedidoAutoPecas;
import org.example.entities.Cliente;
import org.example.entities.FormaPagamento;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.repositories.PedidoAutoPecasRepository;
import org.example.repositories.ClienteRepository;
import org.example.repositories.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoAutoPecasService {

    @Autowired
    private PedidoAutoPecasRepository pedidoAutoPecasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<PedidoAutoPecas> listarTodos() {
        return pedidoAutoPecasRepository.findAll();
    }

    public PedidoAutoPecas salvar(PedidoAutoPecas pedido) {
        // Valida se o cliente existe antes de salvar
        if (pedido.getCliente() != null && pedido.getCliente().getCliId() != null) {
            Cliente cliente = clienteRepository.findById(pedido.getCliente().getCliId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + pedido.getCliente().getCliId()));
            pedido.setCliente(cliente);
        }
        
        // Valida se a forma de pagamento existe antes de salvar e carrega a entidade completa
        if (pedido.getFormaPagamento() != null && pedido.getFormaPagamento().getFormId() != null) {
            FormaPagamento formaPagamento = formaPagamentoRepository.findById(pedido.getFormaPagamento().getFormId())
                    .orElseThrow(() -> new ResourceNotFoundException("Forma de pagamento não encontrada com id " + pedido.getFormaPagamento().getFormId()));
            pedido.setFormaPagamento(formaPagamento);
        } else {
            pedido.setFormaPagamento(null);
        }
        
        return pedidoAutoPecasRepository.save(pedido);
    }

    public PedidoAutoPecas buscarPorId(Long id) {
        Optional<PedidoAutoPecas> obj = pedidoAutoPecasRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id " + id));
    }

    public PedidoAutoPecas atualizar(Long id, PedidoAutoPecas pedido) {
        return pedidoAutoPecasRepository.findById(id)
                .map(pedidoExistente -> {
                    pedido.setPedId(id);
                    
                    // Valida se o cliente existe antes de atualizar e carrega a entidade completa
                    if (pedido.getCliente() != null && pedido.getCliente().getCliId() != null) {
                        Cliente cliente = clienteRepository.findById(pedido.getCliente().getCliId())
                                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + pedido.getCliente().getCliId()));
                        pedido.setCliente(cliente);
                    } else {
                        // Mantém o cliente existente se não foi fornecido
                        pedido.setCliente(pedidoExistente.getCliente());
                    }
                    
                    // Valida se a forma de pagamento existe antes de atualizar e carrega a entidade completa
                    if (pedido.getFormaPagamento() != null && pedido.getFormaPagamento().getFormId() != null) {
                        FormaPagamento formaPagamento = formaPagamentoRepository.findById(pedido.getFormaPagamento().getFormId())
                                .orElseThrow(() -> new ResourceNotFoundException("Forma de pagamento não encontrada com id " + pedido.getFormaPagamento().getFormId()));
                        pedido.setFormaPagamento(formaPagamento);
                    } else {
                        // Se não foi fornecida, pode ser null (pedido sem forma de pagamento)
                        pedido.setFormaPagamento(null);
                    }
                    
                    // Atualiza outros campos
                    if (pedido.getPedData() != null) {
                        pedidoExistente.setPedData(pedido.getPedData());
                    }
                    if (pedido.getPedValorTotal() != null) {
                        pedidoExistente.setPedValorTotal(pedido.getPedValorTotal());
                    }
                    if (pedido.getPedStatus() != null) {
                        pedidoExistente.setPedStatus(pedido.getPedStatus());
                    }
                    if (pedido.getPedObservacoes() != null) {
                        pedidoExistente.setPedObservacoes(pedido.getPedObservacoes());
                    }
                    
                    pedidoExistente.setCliente(pedido.getCliente());
                    pedidoExistente.setFormaPagamento(pedido.getFormaPagamento());
                    
                    return pedidoAutoPecasRepository.save(pedidoExistente);
                }).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id " + id));
    }

    @Transactional
    public void deletar(Long id) {
        PedidoAutoPecas pedido = pedidoAutoPecasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id " + id));
        pedidoAutoPecasRepository.delete(pedido);
        
        // Renumerar IDs após exclusão
        renumerarIds();
    }
    
    /**
     * Renumera os IDs dos pedidos sequencialmente começando do 1
     * Desabilita temporariamente as constraints de FK para permitir atualização de chaves primárias
     */
    @Transactional
    public void renumerarIds() {
        try {
            // Usa findAll() simples para renumerar, não precisa dos relacionamentos
            List<PedidoAutoPecas> todosPedidos = pedidoAutoPecasRepository.findAll();
            
            // Se não houver pedidos, reseta para começar do 1
            if (todosPedidos.isEmpty()) {
                resetAutoIncrementMySQL(1L);
                return;
            }
            
            // Ordena por ID atual para manter a ordem cronológica
            todosPedidos.sort((p1, p2) -> Long.compare(p1.getPedId(), p2.getPedId()));
            
            // Verifica se já está sequencial (otimização)
            boolean jaEstaSequencial = true;
            for (int i = 0; i < todosPedidos.size(); i++) {
                if (!todosPedidos.get(i).getPedId().equals((long)(i + 1))) {
                    jaEstaSequencial = false;
                    break;
                }
            }
            
            if (jaEstaSequencial) {
                // Já está sequencial, apenas reseta o auto-increment
                Long proximoId = (long) (todosPedidos.size() + 1);
                resetAutoIncrementMySQL(proximoId);
                return;
            }
            
            // Desabilita temporariamente as verificações de chave estrangeira
            pedidoAutoPecasRepository.disableForeignKeyChecks();
            
            try {
                // Primeiro passo: Mover todos os IDs para valores temporários muito altos
                // Isso evita conflitos durante a renumerar
                long offsetInicial = 999999L;
                for (int i = 0; i < todosPedidos.size(); i++) {
                    PedidoAutoPecas pedido = todosPedidos.get(i);
                    Long idTemporario = offsetInicial - i;
                    Long idAtual = pedido.getPedId();
                    
                    if (!idAtual.equals(idTemporario)) {
                        pedidoAutoPecasRepository.updatePedidoId(idAtual, idTemporario);
                    }
                }
                
                // Segundo passo: Renumerar sequencialmente começando do 1
                for (int i = 0; i < todosPedidos.size(); i++) {
                    Long novoId = (long) (i + 1);
                    Long idTemporario = offsetInicial - i;
                    
                    if (!idTemporario.equals(novoId)) {
                        pedidoAutoPecasRepository.updatePedidoId(idTemporario, novoId);
                    }
                }
                
                // Reseta o contador de auto-incremento para o próximo ID disponível
                Long proximoId = (long) (todosPedidos.size() + 1);
                resetAutoIncrementMySQL(proximoId);
                
            } finally {
                // Sempre reabilita as verificações de chave estrangeira
                pedidoAutoPecasRepository.enableForeignKeyChecks();
            }
            
        } catch (Exception e) {
            // Se houver erro, tenta reabilitar as constraints e loga o erro
            try {
                pedidoAutoPecasRepository.enableForeignKeyChecks();
            } catch (Exception e2) {
                System.err.println("Erro ao reabilitar constraints: " + e2.getMessage());
            }
            System.err.println("Erro ao renumerar IDs: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Reseta o contador de auto-incremento do MySQL
     */
    private void resetAutoIncrementMySQL(Long proximoId) {
        try {
            pedidoAutoPecasRepository.resetAutoIncrementMySQL(proximoId);
        } catch (Exception e) {
            System.err.println("Não foi possível resetar o auto-increment. Próximo ID será: " + proximoId);
            e.printStackTrace();
        }
    }
}


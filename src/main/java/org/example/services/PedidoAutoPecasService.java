package org.example.services;

import org.example.entities.PedidoAutoPecas;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.repositories.PedidoAutoPecasRepository;
import org.example.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoAutoPecasService {

    @Autowired
    private PedidoAutoPecasRepository pedidoAutoPecasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<PedidoAutoPecas> listarTodos() {
        return pedidoAutoPecasRepository.findAll();
    }

    public PedidoAutoPecas salvar(PedidoAutoPecas pedido) {
        // Valida se o cliente existe antes de salvar
        if (pedido.getCliente() != null && pedido.getCliente().getCliId() != null) {
            clienteRepository.findById(pedido.getCliente().getCliId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + pedido.getCliente().getCliId()));
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
                    // Valida se o cliente existe antes de atualizar
                    if (pedido.getCliente() != null && pedido.getCliente().getCliId() != null) {
                        clienteRepository.findById(pedido.getCliente().getCliId())
                                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + pedido.getCliente().getCliId()));
                    }
                    return pedidoAutoPecasRepository.save(pedido);
                }).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id " + id));
    }

    public void deletar(Long id) {
        PedidoAutoPecas pedido = pedidoAutoPecasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id " + id));
        pedidoAutoPecasRepository.delete(pedido);
    }
}


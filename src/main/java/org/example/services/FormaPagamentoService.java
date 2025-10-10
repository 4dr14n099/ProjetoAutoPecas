package org.example.services;

import org.example.entities.FormaPagamento;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.repositories.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> listarTodos() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public Optional<FormaPagamento> buscarPorId(Long id) {
        return formaPagamentoRepository.findById(id);
    }

    public FormaPagamento atualizar(Long id, FormaPagamento formaPagamento) {
        return formaPagamentoRepository.findById(id)
                .map(fpExistente -> {
                    formaPagamento.setFormId(id);
                    return formaPagamentoRepository.save(formaPagamento);
                }).orElseThrow(() -> new ResourceNotFoundException("Forma de pagamento não encontrada com id " + id));
    }

    public void deletar(Long id) {
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forma de pagamento não encontrada com id " + id));
        formaPagamentoRepository.delete(formaPagamento);
    }
}

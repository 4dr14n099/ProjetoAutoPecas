package org.example.services;

import org.example.entities.Produto;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizar(Long id, Produto produto) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produto.setProId(id);
                    return produtoRepository.save(produto);
                }).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id " + id));
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id " + id));
        produtoRepository.delete(produto);
    }
}

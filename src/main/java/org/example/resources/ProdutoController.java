package org.example.resources;

import org.example.entities.Produto;
import org.example.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Produto produto) {
        try {
            // Garantir que proId seja null para criação
            produto.setProId(null);
            
            // Validar nome (obrigatório)
            if (produto.getProNome() == null || produto.getProNome().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"message\": \"Nome do produto é obrigatório\"}");
            }
            
            // Limpar strings vazias (converter para null)
            if (produto.getProCodigoBarras() != null && produto.getProCodigoBarras().trim().isEmpty()) {
                produto.setProCodigoBarras(null);
            }
            if (produto.getProDescricao() != null && produto.getProDescricao().trim().isEmpty()) {
                produto.setProDescricao(null);
            }
            if (produto.getProReferencia() != null && produto.getProReferencia().trim().isEmpty()) {
                produto.setProReferencia(null);
            }
            if (produto.getProUnidadeMedida() != null && produto.getProUnidadeMedida().trim().isEmpty()) {
                produto.setProUnidadeMedida(null);
            }
            if (produto.getProMarca() != null && produto.getProMarca().trim().isEmpty()) {
                produto.setProMarca(null);
            }
            if (produto.getProCategoria() != null && produto.getProCategoria().trim().isEmpty()) {
                produto.setProCategoria(null);
            }
            if (produto.getProLocalizacao() != null && produto.getProLocalizacao().trim().isEmpty()) {
                produto.setProLocalizacao(null);
            }
            if (produto.getProObservacoes() != null && produto.getProObservacoes().trim().isEmpty()) {
                produto.setProObservacoes(null);
            }
            
            // Se proDataCadastro for null, definir como data atual
            if (produto.getProDataCadastro() == null) {
                produto.setProDataCadastro(java.time.LocalDate.now());
            }
            
            Produto salvo = produtoRepository.save(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            e.printStackTrace();
            String errorMessage = "Erro de integridade: ";
            if (e.getMessage() != null && e.getMessage().contains("PRO_CODIGO_BARRAS")) {
                errorMessage += "Código de barras já existe";
            } else {
                errorMessage += e.getMessage();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"message\": \"" + errorMessage + "\"}");
        } catch (Exception e) {
            e.printStackTrace(); // Mostra erro no console
            String errorMessage = "Erro ao criar produto: " + (e.getMessage() != null ? e.getMessage() : "Erro desconhecido");
            if (e.getCause() != null && e.getCause().getMessage() != null) {
                errorMessage += " - Causa: " + e.getCause().getMessage();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"message\": \"" + errorMessage + "\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            return produtoRepository.findById(id)
                    .map(produtoExistente -> {
                        // Garantir que o ID seja o mesmo da URL
                        produto.setProId(id);
                        
                        // Validar nome (obrigatório)
                        if (produto.getProNome() == null || produto.getProNome().trim().isEmpty()) {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("{\"message\": \"Nome do produto é obrigatório\"}");
                        }
                        
                        // Limpar strings vazias (converter para null)
                        if (produto.getProCodigoBarras() != null && produto.getProCodigoBarras().trim().isEmpty()) {
                            produto.setProCodigoBarras(null);
                        }
                        if (produto.getProDescricao() != null && produto.getProDescricao().trim().isEmpty()) {
                            produto.setProDescricao(null);
                        }
                        if (produto.getProReferencia() != null && produto.getProReferencia().trim().isEmpty()) {
                            produto.setProReferencia(null);
                        }
                        if (produto.getProUnidadeMedida() != null && produto.getProUnidadeMedida().trim().isEmpty()) {
                            produto.setProUnidadeMedida(null);
                        }
                        if (produto.getProMarca() != null && produto.getProMarca().trim().isEmpty()) {
                            produto.setProMarca(null);
                        }
                        if (produto.getProCategoria() != null && produto.getProCategoria().trim().isEmpty()) {
                            produto.setProCategoria(null);
                        }
                        if (produto.getProLocalizacao() != null && produto.getProLocalizacao().trim().isEmpty()) {
                            produto.setProLocalizacao(null);
                        }
                        if (produto.getProObservacoes() != null && produto.getProObservacoes().trim().isEmpty()) {
                            produto.setProObservacoes(null);
                        }
                        
                        // Preservar data de cadastro original se não foi enviada
                        if (produto.getProDataCadastro() == null) {
                            produto.setProDataCadastro(produtoExistente.getProDataCadastro());
                        }
                        
                        Produto atualizado = produtoRepository.save(produto);
                        return ResponseEntity.ok(atualizado);
                    })
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"Produto não encontrado\"}"));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            e.printStackTrace();
            String errorMessage = "Erro de integridade: ";
            if (e.getMessage() != null && e.getMessage().contains("PRO_CODIGO_BARRAS")) {
                errorMessage += "Código de barras já existe";
            } else {
                errorMessage += e.getMessage();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"message\": \"" + errorMessage + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Erro ao atualizar produto: " + (e.getMessage() != null ? e.getMessage() : "Erro desconhecido");
            if (e.getCause() != null && e.getCause().getMessage() != null) {
                errorMessage += " - Causa: " + e.getCause().getMessage();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"message\": \"" + errorMessage + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produtoRepository.delete(produto);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}

package org.example.resources;

import org.example.entities.Endereco;
import org.example.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // ✅ Criar novo endereço
    @PostMapping
    public ResponseEntity<Endereco> criarEndereco(@RequestBody Endereco endereco) {
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return ResponseEntity.ok(enderecoSalvo);
    }

    // ✅ Listar todos os endereços
    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return ResponseEntity.ok(enderecos);
    }

    // ✅ Buscar endereço por ID
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarEnderecoPorId(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Atualizar endereço
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(@PathVariable Long id, @RequestBody Endereco enderecoAtualizado) {
        Optional<Endereco> enderecoExistente = enderecoRepository.findById(id);

        if (enderecoExistente.isPresent()) {
            Endereco endereco = enderecoExistente.get();
            endereco.setEndRua(enderecoAtualizado.getEndRua());
            endereco.setEndNumero(enderecoAtualizado.getEndNumero());
            endereco.setEndCidade(enderecoAtualizado.getEndCidade());
            endereco.setEndCep(enderecoAtualizado.getEndCep());
            endereco.setEndEstado(enderecoAtualizado.getEndEstado());
            // Se for permitido alterar o cliente:
            // endereco.setEndCliente(enderecoAtualizado.getEndCliente());

            return ResponseEntity.ok(enderecoRepository.save(endereco));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Deletar endereço
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

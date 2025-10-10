package org.example.resources;

import org.example.entities.Contato;
import org.example.repositories.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    // ✅ Criar novo contato
    @PostMapping
    public ResponseEntity<Contato> criarContato(@RequestBody Contato contato) {
        Contato contatoSalvo = contatoRepository.save(contato);
        return ResponseEntity.ok(contatoSalvo);
    }

    // ✅ Listar todos os contatos
    @GetMapping
    public ResponseEntity<List<Contato>> listarContatos() {
        List<Contato> contatos = contatoRepository.findAll();
        return ResponseEntity.ok(contatos);
    }

    // ✅ Buscar contato por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarContatoPorId(@PathVariable Long id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        return contato.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Atualizar contato
    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizarContato(@PathVariable Long id, @RequestBody Contato contatoAtualizado) {
        Optional<Contato> contatoExistente = contatoRepository.findById(id);

        if (contatoExistente.isPresent()) {
            Contato contato = contatoExistente.get();
            contato.setConCelular(contatoAtualizado.getConCelular());
            contato.setConTelefoneComercial(contatoAtualizado.getConTelefoneComercial());
            contato.setConEmail(contatoAtualizado.getConEmail());
            // Se for permitido alterar o cliente associado:
            // contato.setConCliente(contatoAtualizado.getConCliente());

            return ResponseEntity.ok(contatoRepository.save(contato));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Deletar contato
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarContato(@PathVariable Long id) {
        if (contatoRepository.existsById(id)) {
            contatoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

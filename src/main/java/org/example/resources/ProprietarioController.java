package org.example.resources;

import org.example.entities.Proprietario;
import org.example.repositories.ProprietarioRepository;
import org.example.services.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("proprietarios")
public class ProprietarioController {
    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @GetMapping
    public List<Proprietario> listarTodos() {
        return proprietarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> buscarPorId(@PathVariable Long id) {
        return proprietarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Proprietario criar(@RequestBody Proprietario proprietario) {
        return proprietarioRepository.save(proprietario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long id, @RequestBody Proprietario proprietario) {
        return proprietarioRepository.findById(id)
                .map(proprietarioExistente -> {
                    proprietario.setPropId(id);
                    Proprietario atualizado = proprietarioRepository.save(proprietario);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (proprietarioRepository.existsById(id)) {
            proprietarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
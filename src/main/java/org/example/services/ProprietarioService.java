package org.example.services;
import org.example.entities.Proprietario;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.repositories.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProprietarioService {
   @Autowired
   private ProprietarioRepository proprietarioRepository;

   public List<Proprietario> listarTodos() {
      return proprietarioRepository.findAll();
   }

   public Proprietario salvar(Proprietario proprietario) {
      return proprietarioRepository.save(proprietario);
   }

   public Optional<Proprietario> buscarPorId(Long id) {
      return proprietarioRepository.findById(id);
   }

   public Proprietario atualizar(Long id, Proprietario proprietario) {
      return proprietarioRepository.findById(id)
              .map(proprietarioExistente -> {
                 proprietario.setPropId(id);
                 return proprietarioRepository.save(proprietario);
              })
              .orElseThrow(() -> new ResourceNotFoundException("Proprietario não encontrado com id " + id));
   }

   public void deletar(Long id) {
      Proprietario proprietario = proprietarioRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Proprietario não encontrado com id " + id));
      proprietarioRepository.delete(proprietario);
   }
}

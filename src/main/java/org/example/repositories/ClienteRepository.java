package org.example.repositories;

import org.example.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Busca por nome
    Cliente findByCliNome(String estNome);

    // Verifica se já existe Cpf cadastrado
    boolean existsByCliCpf(String cliCpf);
}

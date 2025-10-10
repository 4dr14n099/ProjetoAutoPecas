package org.example.mapper;

import org.example.dto.ClienteDTO;
import org.example.entities.Cliente;
import org.example.entities.Contato;
import org.example.entities.Endereco;

import java.util.Optional;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) return null;

        ClienteDTO dto = new ClienteDTO();
        dto.setCliId(cliente.getCliId());
        dto.setCliNome(cliente.getCliNome());
        dto.setCliCpf(cliente.getCliCpf());
        dto.setCliRg(cliente.getCliRg());
        dto.setCliDataNascimento(cliente.getCliDataNascimento());
        dto.setCliSexo(cliente.getCliSexo());
        dto.setCliDataCadastro(cliente.getCliDataCadastro());
        dto.setCliObservacoes(cliente.getCliObservacoes());
        dto.setCliAtivo(cliente.getCliAtivo());

        // Endereço (pegando o primeiro da lista, se existir)
        if (!cliente.getEnderecos().isEmpty()) {
            Endereco endereco = cliente.getEnderecos().get(0);
            dto.setEndRua(endereco.getEndRua());
            dto.setEndNumero(endereco.getEndNumero());
            dto.setEndCidade(endereco.getEndCidade());
            dto.setEndCep(endereco.getEndCep());
            dto.setEndEstado(endereco.getEndEstado());
        }

        // Contato (pegando o primeiro da lista, se existir)
        if (!cliente.getContatos().isEmpty()) {
            Contato contato = cliente.getContatos().get(0);
            dto.setConCelular(contato.getConCelular());
            dto.setConTelefoneComercial(contato.getConTelefoneComercial());
            dto.setConEmail(contato.getConEmail());
        }

        return dto;
    }

    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;

        Cliente cliente = new Cliente();
        cliente.setCliId(dto.getCliId());
        cliente.setCliNome(dto.getCliNome());
        cliente.setCliCpf(dto.getCliCpf());
        cliente.setCliRg(dto.getCliRg());
        cliente.setCliDataNascimento(dto.getCliDataNascimento());
        cliente.setCliSexo(dto.getCliSexo());
        cliente.setCliDataCadastro(dto.getCliDataCadastro());
        cliente.setCliObservacoes(dto.getCliObservacoes());
        cliente.setCliAtivo(dto.getCliAtivo());

        // Endereço
        if (dto.getEndRua() != null) {
            Endereco endereco = new Endereco();
            endereco.setEndRua(dto.getEndRua());
            endereco.setEndNumero(dto.getEndNumero());
            endereco.setEndCidade(dto.getEndCidade());
            endereco.setEndCep(dto.getEndCep());
            endereco.setEndEstado(dto.getEndEstado());
            endereco.setEndCliente(cliente); // vincula o endereço ao cliente

            cliente.getEnderecos().add(endereco);
        }

        // Contato
        if (dto.getConCelular() != null || dto.getConTelefoneComercial() != null || dto.getConEmail() != null) {
            Contato contato = new Contato();
            contato.setConCelular(dto.getConCelular());
            contato.setConTelefoneComercial(dto.getConTelefoneComercial());
            contato.setConEmail(dto.getConEmail());
            contato.setConCliente(cliente); // vincula o contato ao cliente

            cliente.getContatos().add(contato);
        }

        return cliente;
    }
}

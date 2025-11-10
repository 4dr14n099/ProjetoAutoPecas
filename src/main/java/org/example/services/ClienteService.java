package org.example.services;

import org.example.dto.ClienteDTO;
import org.example.entities.Cliente;
import org.example.entities.Contato;
import org.example.entities.Endereco;
import org.example.services.exeptions.ResourceNotFoundException;
import org.example.repositories.ClienteRepository;
import org.example.repositories.EnderecoRepository;
import org.example.services.exeptions.ValueBigForAtributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Cliente insert(Cliente obj) {
        try {
            obj.setCliId(null);
            obj = repository.save(obj);
            enderecoRepository.saveAll(obj.getEnderecos());
            return obj;
        } catch (DataIntegrityViolationException e) {
            throw new ValueBigForAtributeException(e.getMessage());
        }

    }

    public Cliente update(Long id, ClienteDTO objDto) {
        try {
            Cliente entity = findById(id);
            // Atualiza os dados do cliente
            entity.setCliNome(objDto.getCliNome());
            entity.setCliCpf(objDto.getCliCpf());
            entity.setCliRg(objDto.getCliRg());
            entity.setCliDataNascimento(objDto.getCliDataNascimento());
            entity.setCliSexo(objDto.getCliSexo());
            entity.setCliDataCadastro(objDto.getCliDataCadastro());
            entity.setCliObservacoes(objDto.getCliObservacoes());
            
            // Normaliza e atualiza cliAtivo (sempre atualiza, mesmo se for null, normaliza para "false")
            String cliAtivoNormalizado = normalizeCliAtivo(objDto.getCliAtivo());
            entity.setCliAtivo(cliAtivoNormalizado);

            // Atualiza o endereço do cliente (se existir)
            if (!entity.getEnderecos().isEmpty() && objDto.getEndRua() != null) {
                Endereco endereco = entity.getEnderecos().get(0);
                endereco.setEndRua(objDto.getEndRua());
                endereco.setEndNumero(objDto.getEndNumero());
                endereco.setEndCidade(objDto.getEndCidade());
                endereco.setEndCep(objDto.getEndCep());
                endereco.setEndEstado(objDto.getEndEstado());
            }

            // Atualiza o contato (se existir)
            if (!entity.getContatos().isEmpty()) {
                Contato contato = entity.getContatos().get(0);
                contato.setConCelular(objDto.getConCelular());
                contato.setConTelefoneComercial(objDto.getConTelefoneComercial());
                contato.setConEmail(objDto.getConEmail());
            }

            // Salva as alterações
            repository.save(entity);

            return entity;
        } catch (DataIntegrityViolationException e) {
            throw new ValueBigForAtributeException(e.getMessage());
        }
    }

    public void deleteCliente(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public Cliente fromDTO(ClienteDTO objDto) {
        // Normaliza cliAtivo antes de criar o cliente
        String cliAtivoNormalizado = normalizeCliAtivo(objDto.getCliAtivo());
        
        Cliente cliente = new Cliente(null, objDto.getCliNome(), objDto.getCliCpf(), objDto.getCliRg(),
                objDto.getCliDataNascimento(), objDto.getCliSexo(), objDto.getCliDataCadastro(),
                objDto.getCliObservacoes(), cliAtivoNormalizado);

        Endereco ender = new Endereco(null, cliente, objDto.getEndRua(), objDto.getEndNumero(),
                objDto.getEndCidade(), objDto.getEndCep(),
                objDto.getEndEstado());

        Contato contato = new Contato(null, cliente, objDto.getConCelular(), objDto.getConTelefoneComercial(),
                objDto.getConEmail());

        cliente.getEnderecos().add(ender);
        cliente.getContatos().add(contato);

        return cliente;
    }

    public ClienteDTO toNewDTO(Cliente obj) {
        ClienteDTO dto = new ClienteDTO();

        // Mapeie os atributos comuns entre Cliente e ClienteNewDTO
        dto.setCliId(obj.getCliId());
        dto.setCliNome(obj.getCliNome());
        dto.setCliCpf(obj.getCliCpf());
        dto.setCliRg(obj.getCliRg());
        dto.setCliDataNascimento(obj.getCliDataNascimento());
        dto.setCliSexo(obj.getCliSexo());
        dto.setCliDataCadastro(obj.getCliDataCadastro());
        dto.setCliObservacoes(obj.getCliObservacoes());
        
        // Mapeia cliAtivo (normalizado)
        if (obj.getCliAtivo() != null) {
            dto.setCliAtivo(normalizeCliAtivo(obj.getCliAtivo()));
        } else {
            dto.setCliAtivo("false"); // Valor padrão se for null
        }

        // Atributos específicos de Endereco (se existir)
        if (!obj.getEnderecos().isEmpty()) {
            Endereco endereco = obj.getEnderecos().get(0);
            dto.setEndRua(endereco.getEndRua());
            dto.setEndNumero(endereco.getEndNumero());
            dto.setEndCidade(endereco.getEndCidade());
            dto.setEndCep(endereco.getEndCep());
            dto.setEndEstado(endereco.getEndEstado());
        }

        // Atributos específicos de Contato (se existir)
        if (!obj.getContatos().isEmpty()) {
            Contato contato = obj.getContatos().get(0);
            dto.setConCelular(contato.getConCelular());
            dto.setConTelefoneComercial(contato.getConTelefoneComercial());
            dto.setConEmail(contato.getConEmail());
        }

        return dto;
    }
    
    /**
     * Normaliza o valor de cliAtivo para "true" ou "false" (minúsculas)
     * Aceita: "true", "True", "TRUE", "false", "False", "FALSE", boolean true/false (convertido para string)
     */
    private String normalizeCliAtivo(String cliAtivo) {
        if (cliAtivo == null || cliAtivo.trim().isEmpty()) {
            return "false";
        }
        String normalized = cliAtivo.trim().toLowerCase();
        if ("true".equals(normalized) || "1".equals(normalized) || "sim".equals(normalized) || "yes".equals(normalized)) {
            return "true";
        }
        return "false";
    }
}

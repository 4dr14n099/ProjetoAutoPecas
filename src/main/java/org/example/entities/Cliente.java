package org.example.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @Column(name = "CLI_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cliId;

    @OneToMany(mappedBy = "endCliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "conCliente", cascade = CascadeType.ALL)
    private List<Contato> contatos = new ArrayList<>();

    @Column(name = "CLI_NOME", length = 100, nullable = false)
    private String cliNome;

    @Column(name = "CLI_CPF", length = 14, unique = true)
    private String cliCpf;

    @Column(name = "CLI_RG", length = 20)
    private String cliRg;

    @Column(name = "CLI_DATA_NASCIMENTO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date cliDataNascimento;

    @Column(name = "CLI_SEXO", length = 20)
    private String cliSexo;

    @Column(name = "CLI_DATA_CADASTRO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date cliDataCadastro;

    @Column(name = "CLI_OBSERVACOES")
    private String cliObservacoes;

    @Column(name = "CLI_ATIVO")
    private String cliAtivo;

    public Cliente(Long cliId, String cliNome, String cliCpf, String cliRg, Date cliDataNascimento, String cliSexo, Date cliDataCadastro, String cliObservacoes, String cliAtivo) {
        this.cliId = cliId;
        this.cliNome = cliNome;
        this.cliCpf = cliCpf;
        this.cliRg = cliRg;
        this.cliDataNascimento = cliDataNascimento;
        this.cliSexo = cliSexo;
        this.cliDataCadastro = cliDataCadastro;
        this.cliObservacoes = cliObservacoes;
        this.cliAtivo = cliAtivo;
    }
}
package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClienteDTO{

    private Long cliId;
    private String cliNome;
    private String cliCpf;
    private String cliRg;
    private Date cliDataNascimento;
    private String cliSexo;
    private Date cliDataCadastro;
    private String cliObservacoes;
    private String cliAtivo;

    private String endRua;
    private String endNumero;
    private String endCidade;
    private String endCep;
    private String endEstado;

    private String conCelular;
    private String conTelefoneComercial;
    private String conEmail;

    public ClienteDTO() {
    }

}
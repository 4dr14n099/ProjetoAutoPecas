package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AutoPecasDTO {

    private Long apcId;
    private String apcNome;
    private String apcCnpj;
    private String apcEndereco;
    private String apcTelefone;
    private String apcEmail;
    private boolean apcAtivo;

    public AutoPecasDTO() {

    }

}

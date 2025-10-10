package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "END_ID")
    private Long endId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "END_CLI_ID")
    private Cliente endCliente;

    @Column(name = "END_RUA")
    private String endRua;

    @Column(name = "END_NUMERO")
    private String endNumero;

    @Column(name = "END_CIDADE")
    private String endCidade;

    @Column(name = "END_CEP", length = 9)
    private String endCep;

    @Column(name = "END_ESTADO", length = 2)
    private String endEstado;

}

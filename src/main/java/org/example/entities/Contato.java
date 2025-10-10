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
public class Contato {

    @Id
    @Column(name = "CON_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CON_CLI_ID")
    private Cliente conCliente;

    @Column(name = "CON_CELULAR", length = 15)
    private String conCelular;

    @Column(name = "CON_TELEFONE_COMERCIAL", length = 15)
    private String conTelefoneComercial;

    @Column(length = 55, name = "CON_EMAIL")
    private String conEmail;



}



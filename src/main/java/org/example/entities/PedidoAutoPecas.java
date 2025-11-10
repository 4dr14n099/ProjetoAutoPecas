package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "pedido_auto_pecas")
@Getter
@Setter
@NoArgsConstructor
public class PedidoAutoPecas {

    @Id
    @Column(name = "PED_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedId;

    @ManyToOne
    @JoinColumn(name = "CLI_ID")
    @JsonIgnoreProperties({"enderecos", "contatos"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FORM_ID", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private FormaPagamento formaPagamento;

    @Column(name = "PED_DATA", nullable = false)
    private Date pedData;

    @Column(name = "PED_VALOR_TOTAL", nullable = false)
    private Double pedValorTotal;

    @Column(name = "PED_STATUS", length = 20, nullable = false)
    private String pedStatus;

    @Column(name = "PED_OBSERVACOES")
    private String pedObservacoes;

    public PedidoAutoPecas(Long pedId, Cliente cliente, FormaPagamento formaPagamento, Date pedData,
                           Double pedValorTotal, String pedStatus, String pedObservacoes) {
        this.pedId = pedId;
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
        this.pedData = pedData;
        this.pedValorTotal = pedValorTotal;
        this.pedStatus = pedStatus;
        this.pedObservacoes = pedObservacoes;
    }

}
package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.entities.Cliente;
import org.example.entities.FormaPagamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PedidoAutoPecas {

    @Id
    @Column(name = "PED_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CLI_ID")
    private Cliente cliente;

//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PedidoAutoPecas> itens = new ArrayList<>();

//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @Column(name = "PED_DATA", nullable = false)
    private Date pedData;

    @Column(name = "PED_VALOR_TOTAL", nullable = false)
    private Double pedValorTotal;

    @Column(name = "PED_STATUS", length = 20, nullable = false)
    private String pedStatus;

    @Column(name = "PED_OBSERVACOES")
    private String pedObservacoes;

    public PedidoAutoPecas(Long pedId, Cliente cliente, Date pedData,
                           Double pedValorTotal, String pedStatus, String pedObservacoes) {
        this.pedId = pedId;
        this.cliente = cliente;
        this.pedData = pedData;
        this.pedValorTotal = pedValorTotal;
        this.pedStatus = pedStatus;
        this.pedObservacoes = pedObservacoes;
    }

}
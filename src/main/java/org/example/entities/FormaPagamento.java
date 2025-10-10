package org.example.entities;

import jakarta.persistence.*;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.repositories.PedidoAutoPecasRepository;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORM_ID")
    private Long formId;

    @Column(name = "FORM_DESCRICAO", length = 100, nullable = false)
    private String formDescricao;

    @Column(name = "FORM_TIPO", length = 50, nullable = false)
    private String formTipo;  // Ex: à vista, parcelado, crédito, débito, pix, boleto, bens pessoais

    @Column(name = "FORM_NUMERO_PARCELAS")
    private Integer formNumeroParcelas;

    @Column(name = "FORM_DIAS_ENTRE_PARCELAS")
    private Integer formDiasEntreParcelas;

    @Column(name = "FORM_PERMITE_TROCO")
    private boolean formPermiteTroco;

    @Column(name = "FORM_TAXA_PERCENTUAL")
    private Double formTaxaPercentual;

    @Column(name = "FORM_ATIVO")
    private boolean formAtivo;

    @ManyToOne
    @JoinColumn(name = "RES_ID")  // A chave estrangeira que faz a ligação com ProjetoAutoPecasReserva
    private PedidoAutoPecasRepository pagReserva;  // Nome que será referenciado no mappedBy

    public FormaPagamento(Long formId, String formDescricao, String formTipo,
                          Integer formNumeroParcelas, Integer formDiasEntreParcelas,
                          boolean formPermiteTroco, Double formTaxaPercentual, boolean formAtivo) {
        this.formId = formId;
        this.formDescricao = formDescricao;
        this.formTipo = formTipo;
        this.formNumeroParcelas = formNumeroParcelas;
        this.formDiasEntreParcelas = formDiasEntreParcelas;
        this.formPermiteTroco = formPermiteTroco;
        this.formTaxaPercentual = formTaxaPercentual;
        this.formAtivo = formAtivo;
    }
}

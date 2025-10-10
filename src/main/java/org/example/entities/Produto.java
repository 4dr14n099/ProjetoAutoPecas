package org.example.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRO_ID")
    private Long proId;



    @Column(name = "PRO_NOME", length = 100, nullable = false)
    private String proNome;

    @Column(name = "PRO_DESCRICAO", length = 255)
    private String proDescricao;

    @Column(name = "PRO_CODIGO_BARRAS", length = 50, unique = true)
    private String proCodigoBarras;

    @Column(name = "PRO_REFERENCIA", length = 50)
    private String proReferencia;

    @Column(name = "PRO_UNIDADE_MEDIDA", length = 5)
    private String proUnidadeMedida; // ex: UN, KG, L

    @Column(name = "PRO_MARCA", length = 50)
    private String proMarca;

    @Column(name = "PRO_CATEGORIA", length = 50)
    private String proCategoria;

    @Column(name = "PRO_PRECO_CUSTO")
    private BigDecimal proPrecoCusto;

    @Column(name = "PRO_PRECO_VENDA")
    private BigDecimal proPrecoVenda;

    @Column(name = "PRO_ESTOQUE_ATUAL")
    private Integer proEstoqueAtual;

    @Column(name = "PRO_ESTOQUE_MINIMO")
    private Integer proEstoqueMinimo;

    @Column(name = "PRO_ESTOQUE_MAXIMO")
    private Integer proEstoqueMaximo;

    @Column(name = "PRO_LOCALIZACAO", length = 50)
    private String proLocalizacao; // ex: prateleira A1

    @Column(name = "PRO_DATA_VALIDADE")
    private LocalDate proDataValidade;



    @Column(name = "PRO_ATIVO")
    private boolean proAtivo;

    @Column(name = "PRO_DATA_CADASTRO")
    private LocalDate proDataCadastro;

    @Column(name = "PRO_OBSERVACOES", columnDefinition = "TEXT")
    private String proObservacoes;

    public Produto(Long proId, String proNome, String proDescricao, String proCodigoBarras, String proReferencia,
                   String proUnidadeMedida, String proMarca, String proCategoria, BigDecimal proPrecoCusto,
                   BigDecimal proPrecoVenda, Integer proEstoqueAtual, Integer proEstoqueMinimo,
                   Integer proEstoqueMaximo, String proLocalizacao, LocalDate proDataValidade,
                   boolean proAtivo, LocalDate proDataCadastro, String proObservacoes) {
        this.proId = proId;
        this.proNome = proNome;
        this.proDescricao = proDescricao;
        this.proCodigoBarras = proCodigoBarras;
        this.proReferencia = proReferencia;
        this.proUnidadeMedida = proUnidadeMedida;
        this.proMarca = proMarca;
        this.proCategoria = proCategoria;
        this.proPrecoCusto = proPrecoCusto;
        this.proPrecoVenda = proPrecoVenda;
        this.proEstoqueAtual = proEstoqueAtual;
        this.proEstoqueMinimo = proEstoqueMinimo;
        this.proEstoqueMaximo = proEstoqueMaximo;
        this.proLocalizacao = proLocalizacao;
        this.proDataValidade = proDataValidade;

        this.proAtivo = proAtivo;
        this.proDataCadastro = proDataCadastro;
        this.proObservacoes = proObservacoes;
    }
}

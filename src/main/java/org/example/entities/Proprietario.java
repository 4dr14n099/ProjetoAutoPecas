package org.example.entities;
import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROPRIETARIO")
@Getter
@Setter
@NoArgsConstructor
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_ID")
    private Long propId;

    @Column(name = "PROP_NOME", length = 100, nullable = false)
    private String propNome;

    @Column(name = "PROP_CPF", length = 14, unique = true, nullable = false)
    private String propCpf;

    @Column(name = "PROP_RG", length = 20)
    private String propRg;

    @Column(name = "PROP_TELEFONE", length = 20)
    private String propTelefone;

    @Column(name = "PROP_EMAIL", length = 100)
    private String propEmail;

    @Column(name = "PROP_ATIVO")
    private boolean propAtivo;

    public Proprietario(Long propId, String propNome, String propCpf, String propRg, String propTelefone,
                        String propEmail, boolean propAtivo) {
        this.propId = propId;
        this.propNome = propNome;
        this.propCpf = propCpf;
        this.propRg = propRg;
        this.propTelefone = propTelefone;
        this.propEmail = propEmail;
        this.propAtivo = propAtivo;
    }
}

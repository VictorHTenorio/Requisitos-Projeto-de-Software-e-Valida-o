package jpa.administracao.cliente;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class CartaoJpaEntity {
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, length = 16)
    private String numero;
    
    @Column(nullable = false, length = 5)
    private String validade;
    
    @Column(nullable = false, length = 3)
    private String cvv;

    protected CartaoJpaEntity() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
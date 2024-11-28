package jpa.administracao.cliente;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class EnderecoJpaEntity {
    @Column(nullable = false, length = 9)
    private String cep;
    
    @Column(nullable = false)
    private String cidade;
    
    @Column(nullable = false)
    private String bairro;
    
    @Column(nullable = false)
    private String rua;
    
    @Column(nullable = false)
    private int numero;

    public EnderecoJpaEntity() {}

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
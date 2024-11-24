package jpa.loja.produto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class CorJpaEntity {
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, length = 7)
    private String hex;
    
    protected CorJpaEntity() {}
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getHex() { return hex; }
    public void setHex(String hex) { this.hex = hex; }
}
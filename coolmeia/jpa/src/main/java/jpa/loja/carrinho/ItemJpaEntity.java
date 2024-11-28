package jpa.loja.carrinho;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class ItemJpaEntity {
    @Column(nullable = false)
    private int quantidade;
    
    @Column(name = "produto_id", nullable = false)
    private int produtoId;
    
    @Column(name = "valor_unitario", nullable = false)
    private float valorUnitario;
    
    @Column(name = "cupom_codigo")
    private String cupomCodigo;
    
    protected ItemJpaEntity() {}

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public float getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(float valorUnitario) { this.valorUnitario = valorUnitario; }

    public String getCupomCodigo() { return cupomCodigo; }
    public void setCupomCodigo(String cupomCodigo) { this.cupomCodigo = cupomCodigo; }
}
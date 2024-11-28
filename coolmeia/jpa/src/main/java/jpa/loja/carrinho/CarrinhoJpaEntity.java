package jpa.loja.carrinho;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinhos")
public class CarrinhoJpaEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "valor_total", nullable = false)
    private float valorTotal;
    
    @ElementCollection
    @CollectionTable(
        name = "carrinho_itens",
        joinColumns = @JoinColumn(name = "carrinho_id")
    )
    private List<ItemJpaEntity> itens = new ArrayList<>();
    
    protected CarrinhoJpaEntity() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public float getValorTotal() { return valorTotal; }
    public void setValorTotal(float valorTotal) { this.valorTotal = valorTotal; }

    public List<ItemJpaEntity> getItens() { return itens; }
    public void setItens(List<ItemJpaEntity> itens) { this.itens = itens; }
}
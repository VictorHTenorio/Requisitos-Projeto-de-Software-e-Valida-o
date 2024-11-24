package jpa.loja.produto;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lista_novidades")
public class ListaNovidadesJpaEntity {
    
    @Id
    @Column(name = "id")
    private Integer id = 1; // Como é singleton, sempre será 1
    
    @ElementCollection
    @CollectionTable(
        name = "lista_novidades_produtos",
        joinColumns = @JoinColumn(name = "lista_id")
    )
    @Column(name = "produto_id")
    private List<Integer> produtosIds = new ArrayList<>();
    
    protected ListaNovidadesJpaEntity() {}
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public List<Integer> getProdutosIds() { return produtosIds; }
    public void setProdutosIds(List<Integer> produtosIds) { this.produtosIds = produtosIds; }
}
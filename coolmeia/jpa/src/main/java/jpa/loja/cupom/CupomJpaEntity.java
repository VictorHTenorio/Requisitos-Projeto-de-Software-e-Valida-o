package jpa.loja.cupom;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cupons")
public class CupomJpaEntity {
    @EmbeddedId
    private CupomCodigoJpaEntity id;
    
    @Embedded
    private PeriodoJpaEntity periodoValidade;
    
    @Column(name = "porcentagem_desconto", nullable = false)
    private int porcentagemDesconto;
    
    @ElementCollection
    @CollectionTable(
        name = "cupom_categorias",
        joinColumns = @JoinColumn(name = "cupom_codigo")
    )
    @Column(name = "categoria_id")
    private List<Integer> categoriasIds = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(
        name = "cupom_produtos",
        joinColumns = @JoinColumn(name = "cupom_codigo")
    )
    @Column(name = "produto_id")
    private List<Integer> produtosIds = new ArrayList<>();
    
    protected CupomJpaEntity() {}
    
    // Getters e Setters
    public CupomCodigoJpaEntity getId() { return id; }
    public void setId(CupomCodigoJpaEntity id) { this.id = id; }
    
    public PeriodoJpaEntity getPeriodoValidade() { return periodoValidade; }
    public void setPeriodoValidade(PeriodoJpaEntity periodoValidade) { this.periodoValidade = periodoValidade; }
    
    public int getPorcentagemDesconto() { return porcentagemDesconto; }
    public void setPorcentagemDesconto(int porcentagemDesconto) { this.porcentagemDesconto = porcentagemDesconto; }
    
    public List<Integer> getCategoriasIds() { return categoriasIds; }
    public void setCategoriasIds(List<Integer> categoriasIds) { this.categoriasIds = categoriasIds; }
    
    public List<Integer> getProdutosIds() { return produtosIds; }
    public void setProdutosIds(List<Integer> produtosIds) { this.produtosIds = produtosIds; }
}
package jpa.loja.produto;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos")
public class ProdutoJpaEntity {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String descricao;
    
    @Column(nullable = false)
    private int quantidade;
    
    @Column(nullable = false)
    private float valor;
    
    @ElementCollection
    @CollectionTable(
        name = "produto_cores",
        joinColumns = @JoinColumn(name = "produto_id")
    )
    private List<CorJpaEntity> cores = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(
        name = "produto_categorias",
        joinColumns = @JoinColumn(name = "produto_id")
    )
    @Column(name = "categoria_id")
    private List<Integer> categoriasIds = new ArrayList<>();
    
    @Column(name = "data_adicao", nullable = false)
    private LocalDate dataAdicao;

    // Construtor protegido para JPA
    protected ProdutoJpaEntity() {}

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    
    public float getValor() { return valor; }
    public void setValor(float valor) { this.valor = valor; }
    
    public List<CorJpaEntity> getCores() { return cores; }
    public void setCores(List<CorJpaEntity> cores) { this.cores = cores; }
    
    public List<Integer> getCategoriasIds() { return categoriasIds; }
    public void setCategoriasIds(List<Integer> categoriasIds) { this.categoriasIds = categoriasIds; }
    
    public LocalDate getDataAdicao() { return dataAdicao; }
    public void setDataAdicao(LocalDate dataAdicao) { this.dataAdicao = dataAdicao; }
}
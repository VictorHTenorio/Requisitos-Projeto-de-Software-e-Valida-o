package jpa.loja.categoria;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class CategoriaJpaEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String nome;
    
    protected CategoriaJpaEntity() {}
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
package jpa.administracao.cliente;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "clientes")
public class ClienteJpaEntity {
    @EmbeddedId
    private ClienteIdJpaEntity id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String senha;
    
    @Temporal(TemporalType.DATE)
    private Date nascimento;
    
    @Column(name = "carrinho_id")
    private int carrinhoId;
    
    @ElementCollection
    @CollectionTable(
        name = "cliente_desejos",
        joinColumns = @JoinColumn(name = "cliente_cpf")
    )
    @Column(name = "produto_id")
    private List<Integer> listaDesejos = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(
        name = "cliente_enderecos",
        joinColumns = @JoinColumn(name = "cliente_cpf")
    )
    private List<EnderecoJpaEntity> enderecos = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(
        name = "cliente_cartoes",
        joinColumns = @JoinColumn(name = "cliente_cpf")
    )
    private List<CartaoJpaEntity> cartoes = new ArrayList<>();

    // Construtor padrão para JPA
    protected ClienteJpaEntity() {}

    // Getters e Setters
    public ClienteIdJpaEntity getId() {
        return id;
    }

    public void setId(ClienteIdJpaEntity id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public int getCarrinhoId() {
        return carrinhoId;
    }

    public void setCarrinhoId(int carrinhoId) {
        this.carrinhoId = carrinhoId;
    }

    public List<Integer> getListaDesejos() {
        return listaDesejos;
    }

    public void setListaDesejos(List<Integer> listaDesejos) {
        this.listaDesejos = listaDesejos;
    }

    public List<EnderecoJpaEntity> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoJpaEntity> enderecos) {
        this.enderecos = enderecos;
    }

    public List<CartaoJpaEntity> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoJpaEntity> cartoes) {
        this.cartoes = cartoes;
    }
}
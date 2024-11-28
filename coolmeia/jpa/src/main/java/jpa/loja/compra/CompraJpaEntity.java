package jpa.loja.compra;

import jakarta.persistence.*;
import jpa.administracao.cliente.EnderecoJpaEntity;

@Entity
@Table(name = "compras")
public class CompraJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private float frete;
    
    @Column(name = "carrinho_id", nullable = false)
    private Integer carrinhoId;
    
    @Embedded
    private EnderecoJpaEntity enderecoEntrega;
    
    // Em vez de embedar o cartão diretamente, guardamos apenas os dados necessários
    @Column(name = "metodo_pagamento", nullable = false)
    private String metodoPagamento;
    
    // Dados do cartão referenciando a tabela de cartões do cliente
    @Column(name = "cartao_numero", length = 16)
    private String cartaoNumero;
    
    protected CompraJpaEntity() {}

    // Getters e Setters básicos
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public float getFrete() { return frete; }
    public void setFrete(float frete) { this.frete = frete; }

    public Integer getCarrinhoId() { return carrinhoId; }
    public void setCarrinhoId(Integer carrinhoId) { this.carrinhoId = carrinhoId; }

    public EnderecoJpaEntity getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(EnderecoJpaEntity enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }
    
    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    
    public String getCartaoNumero() { return cartaoNumero; }
    public void setCartaoNumero(String cartaoNumero) { this.cartaoNumero = cartaoNumero; }
}
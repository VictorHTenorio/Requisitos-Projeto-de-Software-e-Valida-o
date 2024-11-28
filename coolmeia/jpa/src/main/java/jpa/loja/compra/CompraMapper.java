package jpa.loja.compra;

import administracao.cliente.Cartao;
import comum.administracao.cliente.Endereco;
import jpa.administracao.cliente.EnderecoJpaEntity;
import loja.carrinho.CarrinhoId;
import loja.compra.Compra;
import loja.compra.CompraId;
import loja.pagamento.MetodoPagamento;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class CompraMapper {
    
	public CompraJpaEntity toJpaEntity(Compra compra) {
        notNull(compra, "Compra não pode ser nula");
        
        CompraJpaEntity jpaEntity = new CompraJpaEntity();
        
        if (compra.getId() != null) {
            jpaEntity.setId(compra.getId().getId());
        }
        
        jpaEntity.setFrete(compra.getFrete());
        jpaEntity.setCarrinhoId(compra.getCarrinhoId().getId());
        jpaEntity.setEnderecoEntrega(toJpaEntity(compra.getEnderecoEntrega()));
        
        // Mapear método de pagamento
        MetodoPagamento metodoPagamento = compra.getMetodoPagamento();
        if (metodoPagamento instanceof Cartao) {
            Cartao cartao = (Cartao) metodoPagamento;
            jpaEntity.setMetodoPagamento("CARTAO");
            jpaEntity.setCartaoNumero(cartao.getNumero());
        } else {
            throw new IllegalArgumentException("Método de pagamento não suportado: " + 
                metodoPagamento.getClass().getSimpleName());
        }
        
        return jpaEntity;
    }
    
    public Compra toDomainEntity(CompraJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        // Recriar o método de pagamento baseado no tipo
        MetodoPagamento metodoPagamento;
        if ("CARTAO".equals(jpaEntity.getMetodoPagamento())) {
            metodoPagamento = new Cartao(
                "Cliente", // Nome não é relevante para o pagamento
                jpaEntity.getCartaoNumero(),
                "12/99", // Dados fixos pois são só para processamento
                "123"
            );
        } else {
            throw new IllegalStateException("Método de pagamento desconhecido: " + 
                jpaEntity.getMetodoPagamento());
        }
        
        if (jpaEntity.getId() == null) {
            return new Compra(
                new CarrinhoId(jpaEntity.getCarrinhoId()),
                toDomainEntity(jpaEntity.getEnderecoEntrega()),
                jpaEntity.getFrete(),
                metodoPagamento
            );
        }
        
        return new Compra(
            new CompraId(jpaEntity.getId()),
            new CarrinhoId(jpaEntity.getCarrinhoId()),
            toDomainEntity(jpaEntity.getEnderecoEntrega()),
            jpaEntity.getFrete(),
            metodoPagamento
        );
    }
    
    private EnderecoJpaEntity toJpaEntity(Endereco endereco) {
        if (endereco == null) return null;
        
        EnderecoJpaEntity jpaEntity = new EnderecoJpaEntity();
        jpaEntity.setCep(endereco.getCep());
        jpaEntity.setCidade(endereco.getCidade());
        jpaEntity.setBairro(endereco.getBairro());
        jpaEntity.setRua(endereco.getRua());
        jpaEntity.setNumero(endereco.getNumero());
        return jpaEntity;
    }
    
    private Endereco toDomainEntity(EnderecoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return new Endereco(
            jpaEntity.getCep(),
            jpaEntity.getCidade(),
            jpaEntity.getBairro(),
            jpaEntity.getRua(),
            jpaEntity.getNumero()
        );
    }
    
}
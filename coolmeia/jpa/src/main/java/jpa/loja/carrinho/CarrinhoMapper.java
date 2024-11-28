package jpa.loja.carrinho;

import loja.carrinho.*;
import loja.produto.ProdutoId;
import loja.cupom.CupomCodigo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class CarrinhoMapper {
    
	public CarrinhoJpaEntity toJpaEntity(Carrinho carrinho) {
        notNull(carrinho, "Carrinho não pode ser nulo");
        
        CarrinhoJpaEntity jpaEntity = new CarrinhoJpaEntity();
        
        if (carrinho.getId() != null) {
            jpaEntity.setId(carrinho.getId().getId());
        }
        
        jpaEntity.setValorTotal(carrinho.getValorTotal());
        
        if (carrinho.getItens() != null) {
            jpaEntity.setItens(
                carrinho.getItens().stream()
                    .map(this::toJpaEntity)
                    .collect(Collectors.toList())
            );
        }
        
        return jpaEntity;
    }
    
	 public Carrinho toDomainEntity(CarrinhoJpaEntity jpaEntity) {
	        if (jpaEntity == null) return null;
	        
	        List<Item> itens = jpaEntity.getItens().stream()
	            .map(this::toDomainEntity)
	            .collect(Collectors.toList());
	            
	        if (jpaEntity.getId() == null) {
	            Carrinho carrinho = new Carrinho();
	            itens.forEach(item -> carrinho.adicionarItem(item, item.getValorUnitario() * item.getQuantidade()));
	            return carrinho;
	        }
	        
	        return new Carrinho(
	            new CarrinhoId(jpaEntity.getId()),
	            itens,
	            jpaEntity.getValorTotal()
	        );
	    }
    
    private ItemJpaEntity toJpaEntity(Item item) {
        if (item == null) return null;
        
        ItemJpaEntity jpaEntity = new ItemJpaEntity();
        jpaEntity.setQuantidade(item.getQuantidade());
        jpaEntity.setProdutoId(item.getProduto().getId());
        jpaEntity.setValorUnitario(item.getValorUnitario());
        
        if (item.getCupomCodigo() != null) {
            jpaEntity.setCupomCodigo(item.getCupomCodigo().getId());
        }
        
        return jpaEntity;
    }
    
    private Item toDomainEntity(ItemJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        CupomCodigo cupomCodigo = null;
        if (jpaEntity.getCupomCodigo() != null) {
            cupomCodigo = new CupomCodigo(jpaEntity.getCupomCodigo());
        }
        
        return new Item(
            jpaEntity.getQuantidade(),
            new ProdutoId(jpaEntity.getProdutoId()),
            jpaEntity.getValorUnitario(),
            cupomCodigo
        );
    }
}
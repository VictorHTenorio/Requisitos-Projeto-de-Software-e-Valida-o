package jpa.loja.produto;

import loja.produto.*;
import loja.categoria.CategoriaId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class ProdutoMapper {
    
	public ProdutoJpaEntity toJpaEntity(Produto produto) {
        notNull(produto, "Produto não pode ser nulo");
        
        ProdutoJpaEntity jpaEntity = new ProdutoJpaEntity();
        
        if (produto.getId() != null) {
            jpaEntity.setId(produto.getId().getId());
        }
        
        jpaEntity.setNome(produto.getNome());
        jpaEntity.setDescricao(produto.getDescricao());
        jpaEntity.setQuantidade(produto.getQuantidade());
        jpaEntity.setValor(produto.getValor());
        jpaEntity.setDataAdicao(produto.getDataAdicao());
        
        // Mapeia cores
        if (produto.getCores() != null) {
            jpaEntity.setCores(
                produto.getCores().stream()
                    .map(this::toJpaEntity)
                    .collect(Collectors.toList())
            );
        }
        
        // Mapeia categorias
        if (produto.getCategorias() != null) {
            jpaEntity.setCategoriasIds(
                produto.getCategorias().stream()
                    .map(CategoriaId::getId)
                    .collect(Collectors.toList())
            );
        }
        
        return jpaEntity;
    }
    
	public Produto toDomainEntity(ProdutoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        List<Cor> cores = jpaEntity.getCores().stream()
            .map(this::toDomainEntity)
            .collect(Collectors.toList());
            
        List<CategoriaId> categorias = jpaEntity.getCategoriasIds().stream()
            .map(CategoriaId::new)
            .collect(Collectors.toList());
        
        if (jpaEntity.getId() == null) {
            return new Produto(
                jpaEntity.getNome(),
                jpaEntity.getDescricao(),
                jpaEntity.getQuantidade(),
                jpaEntity.getValor(),
                cores,
                categorias
            );
        }
        
        return new Produto(
            new ProdutoId(jpaEntity.getId()),
            jpaEntity.getNome(),
            jpaEntity.getDescricao(),
            jpaEntity.getQuantidade(),
            jpaEntity.getValor(),
            cores,
            categorias,
            jpaEntity.getDataAdicao()
        );
    }
    
    private CorJpaEntity toJpaEntity(Cor cor) {
        if (cor == null) return null;
        
        CorJpaEntity jpaEntity = new CorJpaEntity();
        jpaEntity.setNome(cor.getNome());
        jpaEntity.setHex(cor.getHex());
        return jpaEntity;
    }
    
    private Cor toDomainEntity(CorJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        return new Cor(jpaEntity.getNome(), jpaEntity.getHex());
    }
}
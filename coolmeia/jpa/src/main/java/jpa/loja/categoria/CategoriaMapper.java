package jpa.loja.categoria;

import loja.categoria.*;
import org.springframework.stereotype.Component;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class CategoriaMapper {
    
	public CategoriaJpaEntity toJpaEntity(Categoria categoria) {
        notNull(categoria, "Categoria não pode ser nula");
        
        CategoriaJpaEntity jpaEntity = new CategoriaJpaEntity();
        
        if (categoria.getId() != null) {
            jpaEntity.setId(categoria.getId().getId());
        }
        jpaEntity.setNome(categoria.getNome());
        
        return jpaEntity;
    }
    
	public Categoria toDomainEntity(CategoriaJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        if (jpaEntity.getId() == null) {
            return new Categoria(jpaEntity.getNome());
        }
        
        return new Categoria(
            new CategoriaId(jpaEntity.getId()),
            jpaEntity.getNome()
        );
    }
	
}
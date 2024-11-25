package jpa.loja.categoria;

import loja.categoria.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional
public class CategoriaJpaRepository implements CategoriaRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final CategoriaMapper mapper;
    
    public CategoriaJpaRepository(CategoriaMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public Categoria salvar(Categoria categoria) {
        notNull(categoria, "A categoria não pode ser nula");
        
        CategoriaJpaEntity jpaEntity = mapper.toJpaEntity(categoria);
        
        if (categoria.getId() == null) {
            entityManager.persist(jpaEntity);
        } else {
            jpaEntity = entityManager.merge(jpaEntity);
        }
        
        return mapper.toDomainEntity(jpaEntity);
    }
    
    @Override
    public Optional<Categoria> obter(CategoriaId id) {
        notNull(id, "O id da categoria não pode ser nulo");
        
        CategoriaJpaEntity jpaEntity = entityManager.find(
            CategoriaJpaEntity.class,
            id.getId()
        );
        
        return Optional.ofNullable(mapper.toDomainEntity(jpaEntity));
    }
    
    @Override
    public boolean excluir(CategoriaId id) {
        notNull(id, "O id da categoria não pode ser nulo");
        
        CategoriaJpaEntity jpaEntity = entityManager.find(
            CategoriaJpaEntity.class,
            id.getId()
        );
        
        if (jpaEntity != null) {
            entityManager.remove(jpaEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<Categoria> obterTodas() {
        // Criar uma consulta JPQL para buscar todas as categorias
        List<CategoriaJpaEntity> jpaEntities = entityManager.createQuery(
            "SELECT c FROM CategoriaJpaEntity c", 
            CategoriaJpaEntity.class
        ).getResultList();
        
        // Converter cada entidade JPA para entidade de domínio
        return jpaEntities.stream()
            .map(mapper::toDomainEntity)
            .collect(Collectors.toList());
    }
}
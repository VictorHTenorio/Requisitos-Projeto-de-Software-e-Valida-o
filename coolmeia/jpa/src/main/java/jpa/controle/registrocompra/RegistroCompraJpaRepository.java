package jpa.controle.registrocompra;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import controle.registroCompra.RegistroCompra;
import controle.registroCompra.RegistroCompraId;
import controle.registroCompra.RegistroCompraRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class RegistroCompraJpaRepository implements RegistroCompraRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final RegistroCompraMapper mapper;
    
    public RegistroCompraJpaRepository(RegistroCompraMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public RegistroCompra salvar(RegistroCompra registroCompra) {
        RegistroCompraJpaEntity jpaEntity = mapper.toJpaEntity(registroCompra);
        
        if (jpaEntity.getId() != null) {
            jpaEntity = entityManager.merge(jpaEntity);
        } else {
            entityManager.persist(jpaEntity);
        }
        
        return mapper.toDomainEntity(jpaEntity);
    }
    
    @Override
    public RegistroCompra obter(RegistroCompraId id) {
        RegistroCompraJpaEntity jpaEntity = entityManager.find(
            RegistroCompraJpaEntity.class,
            id.getId()
        );
        
        return mapper.toDomainEntity(jpaEntity);
    }
}
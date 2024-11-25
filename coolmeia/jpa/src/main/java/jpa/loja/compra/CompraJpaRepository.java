package jpa.loja.compra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import loja.compra.Compra;
import loja.compra.CompraId;
import loja.compra.CompraRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional
public class CompraJpaRepository implements CompraRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final CompraMapper mapper;
    
    public CompraJpaRepository(CompraMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public Compra salvar(Compra compra) {
        notNull(compra, "A compra não pode ser nula");
        
        try {
            CompraJpaEntity jpaEntity = mapper.toJpaEntity(compra);
            
            if (compra.getId() == null) {
                entityManager.persist(jpaEntity);
            } else {
                CompraJpaEntity existente = entityManager.find(
                    CompraJpaEntity.class, 
                    jpaEntity.getId()
                );
                
                if (existente != null) {
                    // Atualiza os dados
                    existente.setFrete(jpaEntity.getFrete());
                    existente.setCarrinhoId(jpaEntity.getCarrinhoId());
                    existente.setEnderecoEntrega(jpaEntity.getEnderecoEntrega());
                    existente.setMetodoPagamento(jpaEntity.getMetodoPagamento());
                    existente.setCartaoNumero(jpaEntity.getCartaoNumero());
                    
                    jpaEntity = entityManager.merge(existente);
                } else {
                    jpaEntity = entityManager.merge(jpaEntity);
                }
            }
            
            entityManager.flush();
            
            return mapper.toDomainEntity(jpaEntity);
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar compra: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar compra", e);
        }
    }
    
    @Override
    public Compra obter(CompraId id) {
        notNull(id, "O id da compra não pode ser nulo");
        
        try {
            CompraJpaEntity jpaEntity = entityManager.find(
                CompraJpaEntity.class,
                id.getId()
            );
            
            return mapper.toDomainEntity(jpaEntity);
            
        } catch (Exception e) {
            System.err.println("Erro ao obter compra: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter compra", e);
        }
    }
}
package jpa.loja.carrinho;

import loja.carrinho.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional
public class CarrinhoJpaRepository implements CarrinhoRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final CarrinhoMapper mapper;
    
    public CarrinhoJpaRepository(CarrinhoMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public Carrinho salvar(Carrinho carrinho) {
        notNull(carrinho, "O carrinho não pode ser nulo");
        
        try {
            CarrinhoJpaEntity jpaEntity = mapper.toJpaEntity(carrinho);
            
            // É uma nova entidade
            if (carrinho.getId() == null) {
                entityManager.persist(jpaEntity);
            } else {
                CarrinhoJpaEntity existente = entityManager.find(
                    CarrinhoJpaEntity.class, 
                    jpaEntity.getId()
                );
                
                if (existente != null) {
                    // Atualiza os dados
                    existente.setValorTotal(jpaEntity.getValorTotal());
                    existente.getItens().clear();
                    existente.getItens().addAll(jpaEntity.getItens());
                    
                    jpaEntity = entityManager.merge(existente);
                } else {
                    jpaEntity = entityManager.merge(jpaEntity);
                }
            }
            
            entityManager.flush();
            
            return mapper.toDomainEntity(jpaEntity);
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar carrinho: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public Carrinho obter(CarrinhoId id) {
        notNull(id, "O id do carrinho não pode ser nulo");
        
        CarrinhoJpaEntity jpaEntity = entityManager.find(
            CarrinhoJpaEntity.class,
            id.getId()
        );
        
        return mapper.toDomainEntity(jpaEntity);
    }
}
package jpa.loja.cupom;

import loja.cupom.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional
public class CupomJpaRepository implements CupomRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final CupomMapper mapper;
    
    public CupomJpaRepository(CupomMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public Cupom salvar(Cupom cupom) {
        notNull(cupom, "O cupom não pode ser nulo");
        
        CupomJpaEntity jpaEntity = mapper.toJpaEntity(cupom);
        
        // Verifica se o cupom já existe
        CupomJpaEntity existente = entityManager.find(
            CupomJpaEntity.class, 
            jpaEntity.getId()
        );
        
        if (existente != null) {
            // Atualiza dados básicos
            existente.setPeriodoValidade(jpaEntity.getPeriodoValidade());
            existente.setPorcentagemDesconto(jpaEntity.getPorcentagemDesconto());
            
            // Atualiza coleções
            existente.getCategoriasIds().clear();
            existente.getCategoriasIds().addAll(jpaEntity.getCategoriasIds());
            
            existente.getProdutosIds().clear();
            existente.getProdutosIds().addAll(jpaEntity.getProdutosIds());
            
            jpaEntity = entityManager.merge(existente);
        } else {
            entityManager.persist(jpaEntity);
        }
        
        return mapper.toDomainEntity(jpaEntity);
    }
    
    @Override
    public Cupom obter(CupomCodigo codigo) {
        notNull(codigo, "O código do cupom não pode ser nulo");
        
        CupomJpaEntity jpaEntity = entityManager.find(
            CupomJpaEntity.class,
            mapper.toJpaEntity(codigo)
        );
        
        return mapper.toDomainEntity(jpaEntity);
    }
    
    @Override
    public boolean excluir(CupomCodigo codigo) {
        notNull(codigo, "O código do cupom não pode ser nulo");
        
        CupomJpaEntity jpaEntity = entityManager.find(
            CupomJpaEntity.class,
            mapper.toJpaEntity(codigo)
        );
        
        if (jpaEntity != null) {
            entityManager.remove(jpaEntity);
            return true;
        }
        return false;
    }
}
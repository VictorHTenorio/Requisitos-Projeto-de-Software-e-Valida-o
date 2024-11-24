package jpa.loja.cupom;

import loja.cupom.*;
import loja.categoria.CategoriaId;
import loja.produto.ProdutoId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class CupomMapper {
    
    public CupomJpaEntity toJpaEntity(Cupom cupom) {
        notNull(cupom, "Cupom não pode ser nulo");
        
        CupomJpaEntity jpaEntity = new CupomJpaEntity();
        
        if (cupom.getId() != null) {
            jpaEntity.setId(toJpaEntity(cupom.getId()));
        }
        
        jpaEntity.setPeriodoValidade(toJpaEntity(cupom.getPeriodoValidade()));
        jpaEntity.setPorcentagemDesconto(cupom.getPorcentagemDesconto());
        
        // Mapeia categorias
        if (cupom.getCategorias() != null) {
            jpaEntity.setCategoriasIds(
                cupom.getCategorias().stream()
                    .map(CategoriaId::getId)
                    .collect(Collectors.toList())
            );
        }
        
        // Mapeia produtos
        if (cupom.getProdutos() != null) {
            jpaEntity.setProdutosIds(
                cupom.getProdutos().stream()
                    .map(ProdutoId::getId)
                    .collect(Collectors.toList())
            );
        }
        
        return jpaEntity;
    }
    
    public Cupom toDomainEntity(CupomJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        List<CategoriaId> categorias = jpaEntity.getCategoriasIds().stream()
            .map(CategoriaId::new)
            .collect(Collectors.toList());
            
        List<ProdutoId> produtos = jpaEntity.getProdutosIds().stream()
            .map(ProdutoId::new)
            .collect(Collectors.toList());
            
        Periodo periodo = toDomainEntity(jpaEntity.getPeriodoValidade());
        
        if (jpaEntity.getId() == null) {
            return new Cupom(
                periodo,
                jpaEntity.getPorcentagemDesconto(),
                categorias,
                produtos
            );
        }
        
        return new Cupom(
            toDomainEntity(jpaEntity.getId()),
            periodo,
            jpaEntity.getPorcentagemDesconto(),
            categorias,
            produtos
        );
    }
    
    CupomCodigoJpaEntity toJpaEntity(CupomCodigo domainId) {
        if (domainId == null) return null;
        
        CupomCodigoJpaEntity jpaId = new CupomCodigoJpaEntity();
        jpaId.setCodigo(domainId.getId());
        return jpaId;
    }
    
    private CupomCodigo toDomainEntity(CupomCodigoJpaEntity jpaId) {
        if (jpaId == null) return null;
        return new CupomCodigo(jpaId.getCodigo());
    }
    
    private PeriodoJpaEntity toJpaEntity(Periodo periodo) {
        if (periodo == null) return null;
        
        PeriodoJpaEntity jpaEntity = new PeriodoJpaEntity();
        jpaEntity.setInicio(periodo.getInicio());
        jpaEntity.setFim(periodo.getFim());
        return jpaEntity;
    }
    
    private Periodo toDomainEntity(PeriodoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        return new Periodo(jpaEntity.getInicio(), jpaEntity.getFim());
    }
}
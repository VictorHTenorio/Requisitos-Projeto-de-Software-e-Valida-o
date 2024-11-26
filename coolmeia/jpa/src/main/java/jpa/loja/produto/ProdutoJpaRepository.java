package jpa.loja.produto;

import loja.produto.*;
import loja.categoria.CategoriaId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional
public class ProdutoJpaRepository implements ProdutoRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final ProdutoMapper mapper;
    
    public ProdutoJpaRepository(ProdutoMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public Produto salvar(Produto produto) {
        notNull(produto, "O produto não pode ser nulo");
        
        try {
            ProdutoJpaEntity jpaEntity = mapper.toJpaEntity(produto);
            
            // É uma nova entidade
            if (produto.getId() == null) {
                entityManager.persist(jpaEntity);
            } else {
                // É uma atualização
                jpaEntity = entityManager.merge(jpaEntity);
            }
            
            // Força o flush para garantir que temos o ID
            entityManager.flush();
            
            return mapper.toDomainEntity(jpaEntity);
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public Produto obter(ProdutoId id) {
        notNull(id, "O id do produto não pode ser nulo");
        
        ProdutoJpaEntity jpaEntity = entityManager.find(
            ProdutoJpaEntity.class,
            id.getId()
        );
        
        return mapper.toDomainEntity(jpaEntity);
    }
    
    @Override
    public List<Produto> obterTodos() {
        try {
            // Criar uma consulta JPQL para buscar todos os produtos
            TypedQuery<ProdutoJpaEntity> query = entityManager.createQuery(
                "SELECT p FROM ProdutoJpaEntity p",
                ProdutoJpaEntity.class
            );
            
            // Executar a query e converter cada entidade JPA para entidade de domínio
            return query.getResultList()
                .stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            System.err.println("Erro ao obter todos os produtos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public boolean excluir(ProdutoId id) {
        notNull(id, "O id do produto não pode ser nulo");
        
        ProdutoJpaEntity jpaEntity = entityManager.find(
            ProdutoJpaEntity.class,
            id.getId()
        );
        
        if (jpaEntity != null) {
            entityManager.remove(jpaEntity);
            return true;
        }
        return false;
    }
    
    @Override
    public List<Produto> obterPorCategoria(CategoriaId categoria) {
        notNull(categoria, "A categoria não pode ser nula");
        
        // Ajustando a query para usar a lista de IDs diretamente
        TypedQuery<ProdutoJpaEntity> query = entityManager.createQuery(
            "SELECT DISTINCT p FROM ProdutoJpaEntity p WHERE :categoriaId MEMBER OF p.categoriasIds",
            ProdutoJpaEntity.class
        );
        query.setParameter("categoriaId", categoria.getId());
        
        return query.getResultList().stream()
            .map(mapper::toDomainEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public ListaNovidades salvar(ListaNovidades listaNovidades) {
        notNull(listaNovidades, "A lista de novidades não pode ser nula");
        
        ListaNovidadesJpaEntity jpaEntity = entityManager.find(ListaNovidadesJpaEntity.class, 1);
        if (jpaEntity == null) {
            jpaEntity = new ListaNovidadesJpaEntity();
        }
        
        jpaEntity.setProdutosIds(
            listaNovidades.getProdutos().stream()
                .map(ProdutoId::getId)
                .collect(Collectors.toList())
        );
        
        if (entityManager.contains(jpaEntity)) {
            jpaEntity = entityManager.merge(jpaEntity);
        } else {
            entityManager.persist(jpaEntity);
        }
        
        // Converte de volta para o domínio
        ListaNovidades resultado = ListaNovidades.getInstance();
        resultado.setProdutos(
            jpaEntity.getProdutosIds().stream()
                .map(ProdutoId::new)
                .collect(Collectors.toList())
        );
        
        return resultado;
    }
    
    @Override
    public ListaNovidades obter() {
        ListaNovidadesJpaEntity jpaEntity = entityManager.find(ListaNovidadesJpaEntity.class, 1);
        if (jpaEntity == null) {
            return ListaNovidades.getInstance(); // Retorna uma lista vazia se não existir
        }
        
        ListaNovidades listaNovidades = ListaNovidades.getInstance();
        listaNovidades.setProdutos(
            jpaEntity.getProdutosIds().stream()
                .map(ProdutoId::new)
                .collect(Collectors.toList())
        );
        
        return listaNovidades;
    }

}
package jpa.administracao.cliente;

import administracao.cliente.Cliente;
import administracao.cliente.ClienteId;
import administracao.cliente.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional
public class ClienteJpaRepository implements ClienteRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final ClienteMapper mapper;
    
    public ClienteJpaRepository(ClienteMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public Cliente salvar(Cliente cliente) {
        notNull(cliente, "O cliente não pode ser nulo");
        
        ClienteJpaEntity jpaEntity = mapper.toJpaEntity(cliente);
        
        // Verifica se o cliente já existe
        ClienteJpaEntity existente = entityManager.find(
            ClienteJpaEntity.class, 
            jpaEntity.getId()
        );
        
        if (existente != null) {
            // Atualiza os dados do cliente existente
            existente.setNome(jpaEntity.getNome());
            existente.setEmail(jpaEntity.getEmail());
            existente.setSenha(jpaEntity.getSenha());
            existente.setNascimento(jpaEntity.getNascimento());
            existente.setCarrinhoId(jpaEntity.getCarrinhoId());
            
            // Atualiza as coleções
            existente.getEnderecos().clear();
            existente.getEnderecos().addAll(jpaEntity.getEnderecos());
            
            existente.getCartoes().clear();
            existente.getCartoes().addAll(jpaEntity.getCartoes());
            
            existente.getListaDesejos().clear();
            existente.getListaDesejos().addAll(jpaEntity.getListaDesejos());
            
            jpaEntity = entityManager.merge(existente);
        } else {
            entityManager.persist(jpaEntity);
        }
        
        return mapper.toDomainEntity(jpaEntity);
    }
    
    @Override
    public Cliente obter(ClienteId id) {
        notNull(id, "O id do cliente não pode ser nulo");
        
        ClienteJpaEntity jpaEntity = entityManager.find(
            ClienteJpaEntity.class, 
            mapper.toJpaEntity(id)
        );
        
        return mapper.toDomainEntity(jpaEntity);
    }
    
    @Override
    public boolean excluir(ClienteId id) {
        notNull(id, "O id do cliente não pode ser nulo");
        
        ClienteJpaEntity jpaEntity = entityManager.find(
            ClienteJpaEntity.class, 
            mapper.toJpaEntity(id)
        );
        
        if (jpaEntity != null) {
            entityManager.remove(jpaEntity);
            return true;
        }
        return false;
    }
}
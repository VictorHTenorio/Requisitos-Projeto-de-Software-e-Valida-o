package jpa.administracao.cliente; // Pacote corrigido

import administracao.cliente.*;
import comum.administracao.cliente.Endereco;
import loja.carrinho.CarrinhoId;
import loja.produto.ProdutoId;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class ClienteMapper {
    
    public ClienteJpaEntity toJpaEntity(Cliente cliente) {
        notNull(cliente, "Cliente não pode ser nulo");
        
        ClienteJpaEntity jpaEntity = new ClienteJpaEntity();
        
        // Mapeia dados básicos
        jpaEntity.setId(toJpaEntity(cliente.getId()));
        jpaEntity.setNome(cliente.getNome());
        jpaEntity.setEmail(cliente.getEmail());
        jpaEntity.setSenha(cliente.getSenha());
        jpaEntity.setNascimento(cliente.getNascimento());
        
        // Mapeia CarrinhoId como inteiro
        if (cliente.getCarrinhoId() != null) {
            jpaEntity.setCarrinhoId((cliente.getCarrinhoId().getId()));
        }
        
        // Mapeia endereços com proteção null
        if (cliente.getEnderecos() != null) {
            jpaEntity.setEnderecos(
                cliente.getEnderecos().stream()
                    .map(this::toJpaEntity)
                    .collect(Collectors.toList())
            );
        }
            
        // Mapeia cartões com proteção null
        if (cliente.getCartoes() != null) {
            jpaEntity.setCartoes(
                cliente.getCartoes().stream()
                    .map(this::toJpaEntity)
                    .collect(Collectors.toList())
            );
        }
            
        // Mapeia lista de desejos com proteção null
        if (cliente.getListaDeDesejos() != null && cliente.getListaDeDesejos().getProdutos() != null) {
            jpaEntity.setListaDesejos(
                cliente.getListaDeDesejos().getProdutos().stream()
                    .map(produtoId -> produtoId.getId())
                    .collect(Collectors.toList())
            );
        }
            
        return jpaEntity;
    }
    
    public Cliente toDomainEntity(ClienteJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        // Cria cliente com dados básicos obrigatórios
        Cliente cliente = new Cliente(
            toDomainEntity(jpaEntity.getId()),
            jpaEntity.getNome(),
            jpaEntity.getEmail(),
            new CarrinhoId(jpaEntity.getCarrinhoId())
        );
        
        // Adiciona dados opcionais com validação
        if (jpaEntity.getSenha() != null) {
            cliente.setSenha(jpaEntity.getSenha());
        }
        
        if (jpaEntity.getNascimento() != null) {
            cliente.setNascimento(jpaEntity.getNascimento());
        }
        
        // Mapeia endereços com proteção null
        if (jpaEntity.getEnderecos() != null) {
            jpaEntity.getEnderecos().stream()
                .map(this::toDomainEntity)
                .forEach(cliente::adicionarEndereco);
        }
            
        // Mapeia cartões com proteção null
        if (jpaEntity.getCartoes() != null) {
            jpaEntity.getCartoes().stream()
                .map(this::toDomainEntity)
                .forEach(cliente::adicionarCartao);
        }
            
        // Mapeia lista de desejos com proteção null
        if (jpaEntity.getListaDesejos() != null) {
            jpaEntity.getListaDesejos().stream()
                .map(ProdutoId::new)  // Agora usa o Integer diretamente
                .forEach(cliente::adicionarProdutoListaDeDesejos);
        }
            
        return cliente;
    }
    
    ClienteIdJpaEntity toJpaEntity(ClienteId domainId) {
        if (domainId == null) return null;
        
        ClienteIdJpaEntity jpaId = new ClienteIdJpaEntity();
        jpaId.setCpf(domainId.getCpf());
        return jpaId;
    }
    
    private ClienteId toDomainEntity(ClienteIdJpaEntity jpaId) {
        if (jpaId == null) return null;
        return new ClienteId(jpaId.getCpf());
    }
    
    private EnderecoJpaEntity toJpaEntity(Endereco endereco) {
        if (endereco == null) return null;
        
        EnderecoJpaEntity jpaEntity = new EnderecoJpaEntity();
        jpaEntity.setCep(endereco.getCep());
        jpaEntity.setCidade(endereco.getCidade());
        jpaEntity.setBairro(endereco.getBairro());
        jpaEntity.setRua(endereco.getRua());
        jpaEntity.setNumero(endereco.getNumero());
        return jpaEntity;
    }
    
    private Endereco toDomainEntity(EnderecoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return new Endereco(
            jpaEntity.getCep(),
            jpaEntity.getCidade(),
            jpaEntity.getBairro(),
            jpaEntity.getRua(),
            jpaEntity.getNumero()
        );
    }

    private CartaoJpaEntity toJpaEntity(Cartao cartao) {
        if (cartao == null) return null;
        
        CartaoJpaEntity jpaEntity = new CartaoJpaEntity();
        jpaEntity.setNome(cartao.getNome());
        jpaEntity.setNumero(cartao.getNumero());
        jpaEntity.setValidade(cartao.getValidade());
        jpaEntity.setCvv(cartao.getCvv());
        
        return jpaEntity;
    }
    
    private Cartao toDomainEntity(CartaoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return new Cartao(
            jpaEntity.getNome(),
            jpaEntity.getNumero(),
            jpaEntity.getValidade(),
            jpaEntity.getCvv()
        );
    }
}
package jpa;

import administracao.cliente.Cartao;
import administracao.cliente.Cliente;
import administracao.cliente.ClienteId;
import comum.administracao.cliente.Endereco;
import jpa.administracao.cliente.ClienteJpaRepository;
import loja.carrinho.CarrinhoId;
import loja.produto.ProdutoId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class ClienteTester implements CommandLineRunner {
    
    private final ClienteJpaRepository clienteRepository;
    
    public ClienteTester(ClienteJpaRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ClienteTester.class, args);
    }
    
    @Override
    public void run(String... args) {
        try {
            System.out.println("Iniciando teste de persistência de Cliente...");
            
            // Criando um cliente de teste
            ClienteId clienteId = new ClienteId("64603915055");
            CarrinhoId carrinhoId = new CarrinhoId(1);
            
            Cliente cliente = new Cliente(
                clienteId,
                "João da Silva",
                "joao3@email.com",
                "senha123",
                new Date(),
                carrinhoId
            );
            
            // Adicionando um endereço
            Endereco endereco = new Endereco(
                "12345678",
                "São Paulo",
                "Centro",
                "Rua Principal",
                123
            );
            cliente.adicionarEndereco(endereco);
            
            // Adicionando um cartão
            Cartao cartao = new Cartao(
                "João da Silva",
                "1234567890123456",
                "12/25",
                "123"
            );
            cliente.adicionarCartao(cartao);
            
            // Adicionando um produto à lista de desejos
            cliente.adicionarProdutoListaDeDesejos(new ProdutoId(1));
            
            System.out.println("Salvando cliente...");
            Cliente clienteSalvo = clienteRepository.salvar(cliente);
            System.out.println("Cliente salvo com sucesso! ID: " + clienteSalvo.getId());
            
            // Buscando o cliente salvo
            System.out.println("Buscando cliente...");
            Cliente clienteRecuperado = clienteRepository.obter(clienteId);
            System.out.println("Cliente recuperado: " + clienteRecuperado);
            System.out.println("Endereços: " + clienteRecuperado.getEnderecos());
            System.out.println("Cartões: " + clienteRecuperado.getCartoes());
            System.out.println("Lista de Desejos: " + clienteRecuperado.getListaDeDesejos().getProdutos());
            
            // Atualizando o cliente
            System.out.println("Atualizando cliente...");
            clienteRecuperado.adicionarEndereco(new Endereco(
                "87654321",
                "Rio de Janeiro",
                "Copacabana",
                "Av Atlântica",
                456
            ));
            
            Cliente clienteAtualizado = clienteRepository.salvar(clienteRecuperado);
            System.out.println("Cliente atualizado com sucesso!");
            System.out.println("Novos endereços: " + clienteAtualizado.getEnderecos());
            
            // Excluindo o cliente
            System.out.println("Excluindo cliente...");
            boolean excluido = clienteRepository.excluir(clienteId);
            System.out.println("Cliente excluído: " + excluido);
            
        } catch (Exception e) {
            System.err.println("Erro durante o teste: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
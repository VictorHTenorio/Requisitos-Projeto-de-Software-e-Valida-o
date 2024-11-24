package jpa;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jpa.loja.carrinho.CarrinhoJpaRepository;
import jpa.loja.produto.ProdutoJpaRepository;
import loja.carrinho.Carrinho;
import loja.carrinho.Item;
import loja.produto.Cor;
import loja.produto.Produto;

@SpringBootApplication
public class CarrinhoTester implements CommandLineRunner {
    
    private final CarrinhoJpaRepository carrinhoRepository;
    private final ProdutoJpaRepository produtoRepository;
    
    public CarrinhoTester(CarrinhoJpaRepository carrinhoRepository, 
                         ProdutoJpaRepository produtoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.produtoRepository = produtoRepository;
    }
    
    @Override
    public void run(String... args) {
        try {
            System.out.println("Iniciando teste de persistência de Carrinho...");
            
            // Primeiro criamos um produto para usar no carrinho
            Produto produto = new Produto(
                "Teste Produto",
                "Produto para teste",
                10,
                100.0f,
                Arrays.asList(new Cor("Preto", "#000000")),
                new ArrayList<>()
            );
            
            Produto produtoSalvo = produtoRepository.salvar(produto);
            
            // Criando um carrinho novo
            Carrinho carrinho = new Carrinho();
            
            // Adicionando um item
            Item item = new Item(
                2,
                produtoSalvo.getId(),
                produtoSalvo.getValor(),
                null // sem cupom
            );
            
            carrinho.adicionarItem(item, item.getValorUnitario() * item.getQuantidade());
            
            // Salvando carrinho
            System.out.println("Salvando carrinho...");
            Carrinho carrinhoSalvo = carrinhoRepository.salvar(carrinho);
            System.out.println("Carrinho salvo com sucesso! ID: " + carrinhoSalvo.getId().getId());
            System.out.println("Valor total: " + carrinhoSalvo.getValorTotal());
            System.out.println("Quantidade de itens: " + carrinhoSalvo.getItens().size());
            
            // Buscando o carrinho
            System.out.println("\nBuscando carrinho...");
            Carrinho carrinhoRecuperado = carrinhoRepository.obter(carrinhoSalvo.getId());
            System.out.println("Carrinho recuperado com " + carrinhoRecuperado.getItens().size() + " itens");
            
            // Limpando dados de teste
            produtoRepository.excluir(produtoSalvo.getId());
            
        } catch (Exception e) {
            System.err.println("Erro durante o teste: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        SpringApplication.run(CarrinhoTester.class, args);
    }
}

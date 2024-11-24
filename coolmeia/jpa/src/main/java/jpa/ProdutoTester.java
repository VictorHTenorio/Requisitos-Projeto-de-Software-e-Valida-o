package jpa;
import jpa.loja.categoria.CategoriaJpaRepository;
import jpa.loja.produto.ProdutoJpaRepository;
import loja.categoria.Categoria;
import loja.categoria.CategoriaId;
import loja.produto.Cor;
import loja.produto.Produto;
import loja.produto.ProdutoService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication(scanBasePackages = {"jpa", "loja"})
public class ProdutoTester implements CommandLineRunner {
    
    private final ProdutoJpaRepository produtoRepository;
    private final CategoriaJpaRepository categoriaRepository;
    private final ProdutoService produtoService;
    
    public ProdutoTester(ProdutoJpaRepository produtoRepository, 
                        CategoriaJpaRepository categoriaRepository,
                        ProdutoService produtoService) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.produtoService = produtoService;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ProdutoTester.class, args);
    }
    
    @Override
    public void run(String... args) {
        try {
            System.out.println("Iniciando teste de persistência de Produto...");
            
            // Criando categorias
            System.out.println("Criando categorias de teste...");
            Categoria catEletronicos = categoriaRepository.salvar(new Categoria("Eletrônicos"));
            Categoria catInformatica = categoriaRepository.salvar(new Categoria("Informática"));
            
            System.out.println("Categorias criadas com IDs: " + 
                catEletronicos.getId().getId() + ", " + catInformatica.getId().getId());
            
            // Criando cores
            List<Cor> cores = Arrays.asList(
                new Cor("Preto", "#000000"),
                new Cor("Branco", "#FFFFFF")
            );
            
            // Usando os IDs corretos das categorias
            List<CategoriaId> categorias = Arrays.asList(
                new CategoriaId(catEletronicos.getId().getId()),
                new CategoriaId(catInformatica.getId().getId())
            );
            
            // Criando produto
            System.out.println("Criando produto com categorias: " + categorias);
            Produto produto = new Produto(
                "Notebook",
                "Notebook i7 16GB RAM",
                10,
                3500.00f,
                cores,
                categorias
            );
            
            // Salvando produto
            System.out.println("Salvando produto...");
            Produto produtoSalvo = produtoRepository.salvar(produto);
            System.out.println("Produto salvo com sucesso! ID: " + produtoSalvo.getId().getId());
            System.out.println("Categorias do produto: " + 
                produtoSalvo.getCategorias().stream()
                    .map(CategoriaId::getId)
                    .collect(Collectors.toList()));
            System.out.println("Nome: " + produtoSalvo.getNome());
            System.out.println("Descrição: " + produtoSalvo.getDescricao());
            System.out.println("Quantidade: " + produtoSalvo.getQuantidade());
            System.out.println("Valor: " + produtoSalvo.getValor());
            System.out.println("Cores disponíveis: " + produtoSalvo.getCores().size());

            
            // Buscando o produto
            System.out.println("\nBuscando produto...");
            Produto produtoRecuperado = produtoRepository.obter(produtoSalvo.getId());
            System.out.println("Produto recuperado: " + produtoRecuperado.getNome());
            
            // Atualizando o produto
            System.out.println("\nAtualizando produto...");
            List<Cor> coresAtualizadas = new ArrayList<>(produtoRecuperado.getCores());
            coresAtualizadas.add(new Cor("Cinza", "#808080"));
            
            Produto produtoAtualizado = new Produto(
                produtoRecuperado.getId(),
                "Notebook Premium",
                "Notebook i7 16GB RAM SSD 512GB",
                produtoRecuperado.getQuantidade(),
                3999.99f,
                coresAtualizadas,
                produtoRecuperado.getCategorias(),
                produtoRecuperado.getDataAdicao()
            );
            
            produtoAtualizado = produtoRepository.salvar(produtoAtualizado);
            System.out.println("Produto atualizado com sucesso!");
            System.out.println("Novo nome: " + produtoAtualizado.getNome());
            System.out.println("Novo valor: " + produtoAtualizado.getValor());
            System.out.println("Cores disponíveis: " + produtoAtualizado.getCores().size());
            
            // Testando busca por categoria
            System.out.println("\nBuscando produtos por categoria...");
            List<Produto> produtosDaCategoria = produtoRepository.obterPorCategoria(catEletronicos.getId());
            System.out.println("Produtos encontrados na categoria: " + produtosDaCategoria.size());
            
            // Diminuindo quantidade
            System.out.println("\nTestando alteração de quantidade...");
            produtoAtualizado.diminuirQuantidade(2);
            produtoAtualizado = produtoRepository.salvar(produtoAtualizado);
            System.out.println("Nova quantidade: " + produtoAtualizado.getQuantidade());
            
            // Excluindo o produto
            System.out.println("\nExcluindo produto...");
            boolean excluido = produtoRepository.excluir(produtoAtualizado.getId());
            System.out.println("Produto excluído: " + excluido);
            
            // Excluindo as categorias de teste
            System.out.println("\nLimpando categorias de teste...");
            categoriaRepository.excluir(catEletronicos.getId());
            categoriaRepository.excluir(catInformatica.getId());
            
            Produto produtodois = new Produto(
                    "Teste Produto",
                    "Produto para teste",
                    10,
                    100.0f,
                    Arrays.asList(new Cor("Preto", "#000000")),
                    new ArrayList<>()
                );
            
            produtodois = produtoService.salvar(produtodois);
            
        } catch (Exception e) {
            System.err.println("Erro durante o teste: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
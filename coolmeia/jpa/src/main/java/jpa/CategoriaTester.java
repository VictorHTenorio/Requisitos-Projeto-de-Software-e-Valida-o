	package jpa;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jpa.loja.categoria.CategoriaJpaRepository;
import loja.categoria.Categoria;
import loja.categoria.CategoriaId;

@SpringBootApplication
public class CategoriaTester implements CommandLineRunner {
    
    private final CategoriaJpaRepository categoriaRepository;
    
    public CategoriaTester(CategoriaJpaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(CategoriaTester.class, args);
    }
    
    @Override
    public void run(String... args) {
        try {
            System.out.println("Iniciando teste de persistência de Categoria...");
            
            // Criando categoria
            Categoria categoria = new Categoria("Eletrônicos");
            
            System.out.println("Salvando categoria...");
            Categoria categoriaSalva = categoriaRepository.salvar(categoria);
            System.out.println("Categoria salva com sucesso! ID: " + categoriaSalva.getId());
            
            // Buscando a categoria
            System.out.println("Buscando categoria...");
            Optional<Categoria> categoriaRecuperada = categoriaRepository.obter(categoriaSalva.getId());
            
            if (categoriaRecuperada.isPresent()) {
                System.out.println("Categoria recuperada: " + categoriaRecuperada.get());
                
                // Atualizando categoria
                Categoria paraAtualizar = categoriaRecuperada.get();
                CategoriaId idExistente = paraAtualizar.getId();
                
                System.out.println("Atualizando categoria...");
                Categoria categoriaAtualizada = new Categoria(idExistente, "Eletrônicos e Informática");
                categoriaAtualizada = categoriaRepository.salvar(categoriaAtualizada);
                System.out.println("Categoria atualizada com sucesso! Novo nome: " + categoriaAtualizada.getNome());
                
                // Excluindo categoria
                System.out.println("Excluindo categoria...");
                boolean excluido = categoriaRepository.excluir(categoriaAtualizada.getId());
                System.out.println("Categoria excluída: " + excluido);
            }
            
        } catch (Exception e) {
            System.err.println("Erro durante o teste: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
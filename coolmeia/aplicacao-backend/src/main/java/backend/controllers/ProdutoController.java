package backend.controllers;

import loja.categoria.CategoriaId;
import loja.produto.Cor;
import loja.produto.ListaNovidades;
import loja.produto.Produto;
import loja.produto.ProdutoId;
import loja.produto.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/coolmeia/produtos")
public class ProdutoController {
    
    private final ProdutoService produtoService;
    
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    
    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Map<String, Object> request) {
        try {
            // Extrair dados básicos
            String nome = (String) request.get("nome");
            String descricao = (String) request.get("descricao");
            int quantidade = ((Number) request.get("quantidade")).intValue();
            float valor = ((Number) request.get("valor")).floatValue();
            
            // Extrair cores
            List<Map<String, String>> coresMap = (List<Map<String, String>>) request.get("cores");
            List<Cor> cores = coresMap.stream()
                .map(corMap -> new Cor(
                    corMap.get("nome"),
                    corMap.get("hex")
                ))
                .collect(Collectors.toList());
                
            // Extrair categorias
            List<Map<String, Object>> categoriasMap = (List<Map<String, Object>>) request.get("categorias");
            List<CategoriaId> categorias = categoriasMap.stream()
                .map(catMap -> new CategoriaId(((Number) catMap.get("id")).intValue()))
                .collect(Collectors.toList());
            
            // Criar produto
            Produto produto = new Produto(
                nome,
                descricao,
                quantidade,
                valor,
                cores,
                categorias
            );
            
            Produto produtoSalvo = produtoService.salvar(produto);
            return ResponseEntity.ok(produtoSalvo);
        } catch (Exception e) {
            System.err.println("Erro ao criar produto: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Produto> obter(@PathVariable int id) {
        try {
            Produto produto = produtoService.obter(new ProdutoId(id));
            if (produto != null) {
                return ResponseEntity.ok(produto);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        try {
            boolean excluido = produtoService.excluir(new ProdutoId(id));
            if (excluido) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Produto>> obterPorCategoria(@PathVariable int categoriaId) {
        try {
            List<Produto> produtos = produtoService.obterProdutosPorCategoria(new CategoriaId(categoriaId));
            return ResponseEntity.ok(produtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/novidades")
    public ResponseEntity<ListaNovidades> obterNovidades() {
        try {
            ListaNovidades novidades = produtoService.obter();
            return ResponseEntity.ok(novidades);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/novidades/verificar")
    public ResponseEntity<ListaNovidades> verificarNovidades() {
        try {
            ListaNovidades novaLista = produtoService.verificarListaNovidades(ListaNovidades.getInstance());
            return ResponseEntity.ok(novaLista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/todos")
    public ResponseEntity<List<Produto>> obterTodos() {
        try {
            List<Produto> produtos = produtoService.obterTodos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
package backend.controllers;

import loja.categoria.Categoria;
import loja.categoria.CategoriaId;
import loja.categoria.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/coolmeia/categorias")
public class CategoriaController {
    
    private final CategoriaService categoriaService;
    
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    
    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody @Valid Map<String, String> request) {
        try {
            String nome = request.get("nome");
            if (nome == null || nome.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Categoria categoria = new Categoria(nome);
            Categoria categoriaSalva = categoriaService.salvar(categoria);
            return ResponseEntity.ok(categoriaSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obter(@PathVariable int id) {
        try {
            Optional<Categoria> categoriaOpt = categoriaService.obter(new CategoriaId(id));
            return categoriaOpt
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        try {
            boolean excluido = categoriaService.excluir(new CategoriaId(id));
            if (excluido) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Categoria>> obterTodas() {
        try {
            List<Categoria> categorias = categoriaService.obterTodas();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
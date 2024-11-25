package backend.controllers;

import loja.cupom.Cupom;
import loja.cupom.CupomCodigo;
import loja.cupom.CupomService;
import loja.cupom.Periodo;
import loja.categoria.CategoriaId;
import loja.produto.ProdutoId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/coolmeia/cupons")
public class CupomController {
    
    private final CupomService cupomService;
    
    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }
    
    @PostMapping
    public ResponseEntity<Cupom> criar(@Valid @RequestBody Map<String, Object> request) {
        try {
            // Extrair dados básicos
            String codigo = (String) request.get("codigo");
            int porcentagemDesconto = ((Number) request.get("porcentagemDesconto")).intValue();
            
            // Extrair datas do período
            Map<String, String> periodo = (Map<String, String>) request.get("periodo");
            LocalDate inicio = LocalDate.parse(periodo.get("inicio"));
            LocalDate fim = LocalDate.parse(periodo.get("fim"));
            
            // Extrair IDs das categorias
            List<CategoriaId> categorias = new ArrayList<>();
            if (request.containsKey("categorias")) {
                List<Map<String, Object>> categoriasMap = (List<Map<String, Object>>) request.get("categorias");
                categorias = categoriasMap.stream()
                    .map(cat -> new CategoriaId(((Number) cat.get("id")).intValue()))
                    .collect(Collectors.toList());
            }
            
            // Extrair IDs dos produtos
            List<ProdutoId> produtos = new ArrayList<>();
            if (request.containsKey("produtos")) {
                List<Map<String, Object>> produtosMap = (List<Map<String, Object>>) request.get("produtos");
                produtos = produtosMap.stream()
                    .map(prod -> new ProdutoId(((Number) prod.get("id")).intValue()))
                    .collect(Collectors.toList());
            }
            
            // Criar período e cupom
            Periodo periodoValidade = new Periodo(inicio, fim);
            
            Cupom cupom = new Cupom(
                new CupomCodigo(codigo),
                periodoValidade,
                porcentagemDesconto,
                categorias,
                produtos
            );
            
            Cupom cupomSalvo = cupomService.salvar(cupom);
            return ResponseEntity.ok(cupomSalvo);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao criar cupom: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{codigo}")
    public ResponseEntity<Cupom> obter(@PathVariable String codigo) {
        try {
            Cupom cupom = cupomService.obter(new CupomCodigo(codigo));
            if (cupom != null) {
                return ResponseEntity.ok(cupom);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable String codigo) {
        try {
            boolean excluido = cupomService.excluir(new CupomCodigo(codigo));
            if (excluido) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
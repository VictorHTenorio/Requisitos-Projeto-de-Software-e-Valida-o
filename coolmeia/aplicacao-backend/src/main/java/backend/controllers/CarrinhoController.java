package backend.controllers;

import loja.carrinho.Carrinho;
import loja.carrinho.CarrinhoId;
import loja.carrinho.CarrinhoService;
import loja.carrinho.Item;
import loja.cupom.CupomCodigo;
import loja.produto.ProdutoId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/coolmeia/carrinhos")
public class CarrinhoController {
    
    private final CarrinhoService carrinhoService;
    
    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }
    
    @PostMapping
    public ResponseEntity<CarrinhoId> criar() {
        try {
            CarrinhoId carrinhoId = carrinhoService.criarNovoCarrinho();
            return ResponseEntity.ok(carrinhoId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> obter(@PathVariable int id) {
        try {
            Carrinho carrinho = carrinhoService.obter(new CarrinhoId(id));
            if (carrinho != null) {
                return ResponseEntity.ok(carrinho);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/itens")
    public ResponseEntity<Carrinho> adicionarItem(
            @PathVariable int id,
            @Valid @RequestBody Map<String, Object> request) {
        try {
            // Extrair dados do item
            int quantidade = ((Number) request.get("quantidade")).intValue();
            int produtoId = ((Number) request.get("produtoId")).intValue();
            float valorUnitario = ((Number) request.get("valorUnitario")).floatValue();
            
            // Criar item
            Item item = new Item(
                quantidade,
                new ProdutoId(produtoId),
                valorUnitario,
                null  // cupom começa null
            );
            
            // Obter carrinho e adicionar item
            Carrinho carrinho = carrinhoService.obter(new CarrinhoId(id));
            carrinho.adicionarItem(item, quantidade * valorUnitario);
            
            // Salvar e retornar carrinho atualizado
            Carrinho carrinhoAtualizado = carrinhoService.salvar(carrinho);
            return ResponseEntity.ok(carrinhoAtualizado);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao adicionar item: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/cupom")
    public ResponseEntity<Boolean> aplicarCupom(
            @PathVariable int id,
            @Valid @RequestBody Map<String, String> request) {
        try {
            String codigoCupom = request.get("codigo");
            
            boolean cupomAplicado = carrinhoService.aplicarCupom(
                new CupomCodigo(codigoCupom),
                new CarrinhoId(id)
            );
            
            if (cupomAplicado) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao aplicar cupom: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
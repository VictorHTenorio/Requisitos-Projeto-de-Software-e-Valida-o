package backend.controllers;

import loja.carrinho.Carrinho;
import loja.carrinho.CarrinhoId;
import loja.carrinho.CarrinhoService;
import loja.carrinho.Item;
import loja.cupom.CupomCodigo;
import loja.produto.Produto;
import loja.produto.ProdutoId;
import loja.produto.ProdutoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/coolmeia/carrinhos")
public class CarrinhoController {
    
    private final CarrinhoService carrinhoService;
    private final ProdutoService produtoService;
    
    public CarrinhoController(CarrinhoService carrinhoService, ProdutoService produtoService) {
        this.carrinhoService = carrinhoService;
        this.produtoService = produtoService;
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
    
    @PutMapping("/{id}/itens/{produtoId}")
    public ResponseEntity<Carrinho> atualizarQuantidadeItem(
            @PathVariable int id,
            @PathVariable int produtoId,
            @RequestBody Map<String, Integer> request) {
        try {
            int novaQuantidade = request.get("quantidade");
            
            Carrinho carrinho = carrinhoService.obter(new CarrinhoId(id));
            Item itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId() == produtoId)
                .findFirst()
                .orElse(null);

            if (itemExistente != null) {
                // Remove o item antigo
                carrinho.removerItem(itemExistente, itemExistente.getQuantidade() * itemExistente.getValorUnitario());
                
                // Se a quantidade for maior que 0, adiciona com nova quantidade
                if (novaQuantidade > 0) {
                    Item novoItem = new Item(novaQuantidade, itemExistente.getProduto(),
                            itemExistente.getValorUnitario(), itemExistente.getCupomCodigo());
                    carrinho.adicionarItem(novoItem, novoItem.getQuantidade() * novoItem.getValorUnitario());
                }
                
                return ResponseEntity.ok(carrinhoService.salvar(carrinho));
            }
            
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/itens/{produtoId}")
    public ResponseEntity<Void> removerItem(
            @PathVariable int id,
            @PathVariable int produtoId) {
        try {
            Carrinho carrinho = carrinhoService.obter(new CarrinhoId(id));
            Item itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId() == produtoId)
                .findFirst()
                .orElse(null);

            if (itemExistente != null) {
                carrinho.removerItem(itemExistente, itemExistente.getQuantidade() * itemExistente.getValorUnitario());
                carrinhoService.salvar(carrinho);
                return ResponseEntity.ok().build();
            }
            
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
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
    
    @DeleteMapping("/{id}/itens/{produtoId}/cupom")
    public ResponseEntity<Carrinho> removerCupomItem(
            @PathVariable int id,
            @PathVariable int produtoId) {
        try {
            Carrinho carrinho = carrinhoService.obter(new CarrinhoId(id));
            
            List<Item> novosItens = new ArrayList<>();
            float novoValorTotal = 0.0f;
            
            for (Item item : carrinho.getItens()) {
                if (item.getProduto().getId() == produtoId && item.getCupomCodigo() != null) {
                    // Cria novo item sem cupom e com valor original
                    Produto produto = produtoService.obter(item.getProduto());
                    Item novoItem = new Item(item.getQuantidade(), item.getProduto(), produto.getValor(), null);
                    novosItens.add(novoItem);
                    novoValorTotal += produto.getValor() * item.getQuantidade();
                } else {
                    novosItens.add(item);
                    novoValorTotal += item.getValorUnitario() * item.getQuantidade();
                }
            }
            
            Carrinho carrinhoAtualizado = new Carrinho(carrinho.getId(), novosItens, novoValorTotal);
            return ResponseEntity.ok(carrinhoService.salvar(carrinhoAtualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
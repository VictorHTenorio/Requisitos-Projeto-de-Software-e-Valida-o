package backend.controllers;

import loja.compra.Compra;
import loja.compra.CompraId;
import loja.compra.CompraService;
import loja.carrinho.CarrinhoId;
import comum.administracao.cliente.Endereco;
import controle.registroCompra.RegistroCompraService;
import loja.pagamento.MetodoPagamento;
import administracao.cliente.Cartao;
import administracao.cliente.ClienteId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/coolmeia/compras")
public class CompraController {
    
    private final CompraService compraService;
    private final RegistroCompraService registroCompraService;
    
    public CompraController(CompraService compraService, RegistroCompraService registroCompraService) {
        this.compraService = compraService;
        this.registroCompraService = registroCompraService; 
    }
    
    @PostMapping
    public ResponseEntity<Compra> criar(@Valid @RequestBody Map<String, Object> request) {
        try {
            // Extrair dados básicos
            int carrinhoId = ((Number) request.get("carrinhoId")).intValue();
            float frete = ((Number) request.get("frete")).floatValue();
            
            // Extrair endereço
            Map<String, Object> enderecoMap = (Map<String, Object>) request.get("enderecoEntrega");
            Endereco endereco = new Endereco(
                (String) enderecoMap.get("cep"),
                (String) enderecoMap.get("cidade"),
                (String) enderecoMap.get("bairro"),
                (String) enderecoMap.get("rua"),
                ((Number) enderecoMap.get("numero")).intValue()
            );
            
            // Extrair dados do cartão
            Map<String, String> pagamentoMap = (Map<String, String>) request.get("pagamento");
            Cartao cartao = new Cartao(
                pagamentoMap.get("nome"),
                pagamentoMap.get("numero"),
                pagamentoMap.get("validade"),
                pagamentoMap.get("cvv")
            );
            
            // Criar compra
            Compra compra = new Compra(
                new CarrinhoId(carrinhoId),
                endereco,
                frete,
                cartao
            );
            
            Compra compraSalva = compraService.salvar(compra);
            return ResponseEntity.ok(compraSalva);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao criar compra: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Compra> obter(@PathVariable int id) {
        try {
            Compra compra = compraService.obter(new CompraId(id));
            if (compra != null) {
                return ResponseEntity.ok(compra);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/realizar")
    public ResponseEntity<Void> realizarCompra(
            @PathVariable int id,
            @RequestBody Map<String, Object> request) {
        try {
            String cpf = (String) request.get("clienteCpf");
            
            // Registra a compra usando o RegistroCompraService
            registroCompraService.RegistrarCompra(
                new CompraId(id),
                new ClienteId(cpf)
            );
            
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            System.err.println("Erro ao realizar compra: " + e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
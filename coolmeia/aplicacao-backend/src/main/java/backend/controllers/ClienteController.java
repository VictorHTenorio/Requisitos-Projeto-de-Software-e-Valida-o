package backend.controllers;

import administracao.cliente.Cliente;
import administracao.cliente.ClienteId;
import administracao.cliente.ClienteService;
import administracao.cliente.ListaDeDesejos;
import loja.carrinho.CarrinhoId;
import loja.produto.ProdutoId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/coolmeia/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Map<String, Object> request) {
        try {
            // Extrair dados básicos
            String cpf = (String) request.get("cpf");
            String nome = (String) request.get("nome");
            String email = (String) request.get("email");
            String senha = (String) request.get("senha");
            
            // Verificar se é registro completo ou simples
            if (senha != null) {
                // Registro completo
                Date nascimento = new Date((Long) request.get("nascimento")); // Assume timestamp
                CarrinhoId carrinhoId = new CarrinhoId(1); // Criar novo carrinho

                Cliente cliente = new Cliente(
                    new ClienteId(cpf),
                    nome,
                    email,
                    senha,
                    nascimento,
                    carrinhoId
                );
                
                Cliente clienteSalvo = clienteService.salvar(cliente);
                return ResponseEntity.ok(clienteSalvo);
            } else {
                // Registro simples
                CarrinhoId carrinhoId = new CarrinhoId(1); // Criar novo carrinho
                
                Cliente cliente = new Cliente(
                    new ClienteId(cpf),
                    nome,
                    email,
                    carrinhoId
                );
                
                Cliente clienteSalvo = clienteService.salvar(cliente);
                return ResponseEntity.ok(clienteSalvo);
            }
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> obter(@PathVariable String cpf) {
        try {
            Cliente cliente = clienteService.obter(new ClienteId(cpf));
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> excluir(@PathVariable String cpf) {
        try {
            boolean excluido = clienteService.excluir(new ClienteId(cpf));
            if (excluido) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{cpf}/limpar-carrinho")
    public ResponseEntity<Void> limparCarrinho(@PathVariable String cpf) {
        try {
            clienteService.limparCarrinho(new ClienteId(cpf));
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{cpf}/verificar-produtos")
    public ResponseEntity<Boolean> verificarProdutos(
            @PathVariable String cpf,
            @RequestBody Map<String, Object> request) {
        try {
            int carrinhoId = ((Number) request.get("carrinhoId")).intValue();
            int poucaQuantidade = ((Number) request.get("poucaQuantidade")).intValue();
            
            boolean notificado = clienteService.verificarNotificarPoucosProdutosCarrinho(
                new ClienteId(cpf),
                new CarrinhoId(carrinhoId),
                poucaQuantidade
            );
            
            return ResponseEntity.ok(notificado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{cpf}/lista-desejos/{produtoId}")
    public ResponseEntity<Void> alternarProdutoListaDesejos(
            @PathVariable String cpf,
            @PathVariable int produtoId) {
        try {
            Cliente cliente = clienteService.obter(new ClienteId(cpf));
            if (cliente == null) {
                return ResponseEntity.notFound().build();
            }

            // Toggle do produto na lista de desejos
            ListaDeDesejos listaDeDesejos = cliente.getListaDeDesejos();
            ProdutoId pid = new ProdutoId(produtoId);
            
            if (listaDeDesejos.verificarProdutoInLista(pid)) {
                listaDeDesejos.removerProduto(pid);
            } else {
                listaDeDesejos.adicionarProduto(pid);
            }
            
            clienteService.salvar(cliente);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{cpf}/lista-desejos")
    public ResponseEntity<List<ProdutoId>> obterListaDesejos(@PathVariable String cpf) {
        try {
            Cliente cliente = clienteService.obter(new ClienteId(cpf));
            if (cliente == null) {
                return ResponseEntity.notFound().build();
            }

            List<ProdutoId> produtos = cliente.getListaDeDesejos().getProdutos();
            return ResponseEntity.ok(produtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
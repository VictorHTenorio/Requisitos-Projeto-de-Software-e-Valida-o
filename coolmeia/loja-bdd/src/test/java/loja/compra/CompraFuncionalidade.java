package loja.compra;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import administracao.cliente.Cartao;
import administracao.cliente.Cliente;
import administracao.cliente.ClienteId;
import comum.administracao.cliente.Endereco;
import loja.carrinho.Carrinho;
import loja.carrinho.CarrinhoId;
import loja.carrinho.CarrinhoService;
import loja.carrinho.Item;

import loja.produto.Produto;
import loja.produto.ProdutoId;
import loja.produto.ProdutoService;
import memoria.Repository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CompraFuncionalidade {

    private Carrinho carrinho;
    private Cliente cliente;
    private Produto produto;
    private Repository repository = new Repository();
    private ProdutoService produtoService = new ProdutoService(repository);
    private CarrinhoService carrinhoService = new CarrinhoService(repository);
    private CompraService compraService = new CompraService(repository, carrinhoService, produtoService);
    private RuntimeException excecao;

    @Given("Um {string} {string} disponível")
    public void um_produto_disponivel(String classe, String disponibilidade) {
        ProdutoId produtoId = new ProdutoId(1);

        produto = new Produto(produtoId, "Produto Teste", "Descrição", 10, 100.0f, List.of(), List.of());
        produtoService.salvar(produto);

        if ("não está".equals(disponibilidade)) {
            // Simula a indisponibilidade do produto
            produto.diminuirQuantidade(10);
            produtoService.salvar(produto);
        }

        CarrinhoId carrinhoId = new CarrinhoId(1);
        carrinho = new Carrinho(carrinhoId, List.of(), 0.0f);
        cliente = new Cliente(new ClienteId("10905515412"), "Cliente Teste", "teste@teste.com", carrinhoId);
    }

    @When("um cliente coloca o produto no carrinho")
    public void um_cliente_coloca_o_produto_no_carrinho() {
        Item item = new Item(2, produto.getId());
        carrinho.adicionarItem(item, 200.0f);
    }

    @Then("o cliente finaliza a compra")
    public void o_cliente_finaliza_a_compra() {
        try {
            Compra compra = new Compra(carrinho.getId(), new Endereco("52050300", "Recife", "Amarelo", "Rua da Luz", 99), 10.0f, new Cartao("Victor", "1234567890123456", "060219", "123"));
            compraService.salvar(compra);
            compraService.realizarCompra(compra.getId());
        } catch (RuntimeException e) {
            excecao = e;
        }
    }

    @Then("a quantidade do produto é decrementada de acordo com a quantidade comprada")
    public void a_quantidade_do_produto_e_decrementada() {
        Produto produtoAtualizado = produtoService.obter(produto.getId());
        assertEquals(10, produtoAtualizado.getQuantidade());
    }

    @Then("A loja informa que a venda não pode ser realizada")
    public void a_loja_informa_que_a_venda_nao_pode_ser_realizada() {
        assertNotNull(excecao);
        assertTrue(excecao instanceof IllegalStateException);
    }
}
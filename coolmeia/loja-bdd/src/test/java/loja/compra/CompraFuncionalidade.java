package loja.compra;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import administracao.cliente.Cartao;
import comum.administracao.cliente.Endereco;
import loja.LojaFuncionalidade;
import loja.carrinho.Carrinho;
import loja.carrinho.CarrinhoId;
import loja.carrinho.Item;
import loja.produto.Produto;
import loja.produto.ProdutoId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CompraFuncionalidade extends LojaFuncionalidade{

    private Carrinho carrinho;
    private Produto produto;
    private RuntimeException excecao;

    @Given("Um {string} {string} disponível")
    public void um_produto_disponivel(String classe, String disponibilidade) {
        ProdutoId produtoId = new ProdutoId(1);

        produto = new Produto(produtoId, "Produto Teste", "Descrição", 10, 100.0f, List.of(), List.of(), java.time.LocalDate.now());
        produtoService.salvar(produto);

        if ("não está".equals(disponibilidade)) {
            // Simula a indisponibilidade do produto
            produto.diminuirQuantidade(10);
            produtoService.salvar(produto);
        }

        CarrinhoId carrinhoId = new CarrinhoId(1);
        carrinho = new Carrinho(carrinhoId, List.of(), 0.0f);
    }

    @When("um cliente coloca o produto no carrinho")
    public void um_cliente_coloca_o_produto_no_carrinho() {
        Item item = new Item(2, produto.getId(), 200.0f, null);
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

    @Then("A loja informa que a venda não pode ser realizado")
    public void a_loja_informa_que_a_venda_nao_pode_ser_realizada() {
        assertNotNull(excecao);
        assertTrue(excecao instanceof IllegalArgumentException);
    }
}
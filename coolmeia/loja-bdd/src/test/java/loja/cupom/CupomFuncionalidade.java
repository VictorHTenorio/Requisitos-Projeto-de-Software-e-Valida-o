package loja.cupom;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

import loja.LojaFuncionalidade;
import loja.carrinho.Carrinho;
import loja.carrinho.CarrinhoId;
import loja.carrinho.Item;
import loja.produto.Produto;
import loja.produto.ProdutoId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class CupomFuncionalidade extends LojaFuncionalidade{

    private Carrinho carrinho;
    private Cupom cupom;
    private Produto produto;
    private boolean cupomAplicado;

    @Given("Um carrinho de compras com produtos")
    public void um_carrinho_de_compras_com_produtos() {
        ProdutoId produtoId = new ProdutoId(1);
        produto = new Produto(produtoId, "Produto Teste", "Descrição", 10, 100.0f, List.of(), List.of(), java.time.LocalDate.now());
        produtoService.salvar(produto);

        CarrinhoId carrinhoId = new CarrinhoId(1);
        carrinho = new Carrinho(carrinhoId, List.of(), 0.0f); // Cria o carrinho
        Item item = new Item(1, produto.getId(), 100.0f, null);
        carrinho.adicionarItem(item, 100.0f); // Adiciona o item ao carrinho

        // Salva o carrinho no repositório
        carrinhoService.salvar(carrinho);
    }

    @Given("Um cupom de desconto válido")
    public void um_cupom_de_desconto_valido() {
        // Cupom válido: 10% de desconto, com validade entre hoje e 30 dias depois
        Periodo periodo = new Periodo(LocalDate.now(), LocalDate.now().plusDays(30));
        cupom = new Cupom(new CupomCodigo("CUPOM10"), periodo, 10, List.of(), List.of());
        cupomService.salvar(cupom); // Salva o cupom no repositório
    }

    @Given("Um cupom de desconto expirado")
    public void um_cupom_de_desconto_expirado() {
        // Cupom expirado: 10% de desconto, com validade terminada ontem
        Periodo periodo = new Periodo(LocalDate.now().minusDays(30), LocalDate.now().minusDays(1));
        cupom = new Cupom(new CupomCodigo("CUPOMEXPIRED"), periodo, 10, List.of(), List.of());
        cupomService.salvar(cupom); // Salva o cupom no repositório
    }

    @When("O usuário aplica o cupom ao carrinho")
    public void o_usuario_aplica_o_cupom_ao_carrinho() {
        // Usa o CarrinhoService para aplicar o cupom ao carrinho
        try {
            cupomAplicado = carrinhoService.aplicarCupom(cupom.getId(), carrinho.getId());
        } catch (IllegalStateException e) {
            cupomAplicado = false;
        }
    }

    @Then("O valor total do carrinho deve ser reduzido pelo valor do desconto")
    public void o_valor_total_do_carrinho_deve_ser_reduzido_pelo_valor_do_desconto() {
        // O valor esperado após aplicar 10% de desconto em 100.0f é 90.0f
        float valorEsperado = 90.0f;
        assertEquals(valorEsperado, carrinho.getValorTotal(), "O valor do carrinho deveria ter o desconto aplicado.");
    }

    @Then("O valor do cupom não deve ser aplicado")
    public void o_valor_do_cupom_nao_deve_ser_aplicado() {
        // O valor deve permanecer o mesmo, já que o cupom era inválido/expirado
        float valorEsperado = 100.0f; // Sem desconto
        assertEquals(valorEsperado, carrinho.getValorTotal(), "O valor do carrinho não deveria ser alterado.");
    }

    @Then("Uma mensagem de erro deve ser exibida")
    public void uma_mensagem_de_erro_deve_ser_exibida() {
        // Aqui podemos simular uma mensagem de erro, por exemplo, lançando uma exceção ou logando
        assertFalse(cupomAplicado, "O cupom não deveria ter sido aplicado.");
    }
}

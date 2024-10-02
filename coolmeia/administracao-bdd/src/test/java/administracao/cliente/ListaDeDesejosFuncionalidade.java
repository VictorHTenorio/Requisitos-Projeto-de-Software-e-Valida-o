package administracao.cliente;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date;
import java.util.List;
import administracao.AdministracaoFuncionalidade;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import loja.carrinho.Carrinho;
import loja.carrinho.Item;
import loja.produto.Produto;

public class ListaDeDesejosFuncionalidade extends AdministracaoFuncionalidade{
	Produto produto;
	Cliente cliente;
	Carrinho carrinho;
	
	@Given("Um cliente com um produto na lista de desejos")
	public void cliente_com_produto_na_lista_de_desejos(){
		
		produto = new Produto("Produto Desejado", "descrição", 12, 100, List.of(), List.of());
		produto = produtoService.salvar(produto);
		
        carrinho = new Carrinho();
        Item item = new Item(2, produto.getId(), 100.0f, null);
        carrinho.adicionarItem(item, 100.0f);
        carrinho = carrinhoService.salvar(carrinho);
        ClienteId clienteId = new ClienteId("10905515412");
        
        cliente = new Cliente(clienteId, "Victor", "victor@gmail.com", "123", new Date(), carrinho.getId());
        clienteService.salvar(cliente);
        cliente = clienteService.obter(clienteId);
	}
	
	@When("A quantidade do produto fica {string} o determinado como pouco")
	public void diminui_quantidade_produto(String quantidade) {
		if("menor que".equals(quantidade)) {
			produto.diminuirQuantidade(2);
			
		}else {
			produto.diminuirQuantidade(1);
		}
		
		produto = produtoService.salvar(produto);
	}
	
	@Then("o cliente {string} uma notificação pelo email")
	public void cliente_nitificado(String recebe) {
		if("recebe".equals(recebe)) {
			assertTrue(clienteService.verificarNotificarPoucosProdutosCarrinho(cliente.getId(), carrinho.getId(), 10));
		}else {
			assertFalse(clienteService.verificarNotificarPoucosProdutosCarrinho(cliente.getId(), carrinho.getId(), 10));
		}
	}
}

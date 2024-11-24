package loja.produto;

import loja.LojaFuncionalidade;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ListaNovidadeFuncionalidade extends LojaFuncionalidade{
	
	private ListaNovidades listaNovidades;
	private Produto produto;
	
	@Given("Um produto novo foi criado")
	public void produto_novo_criado() {
		listaNovidades = ListaNovidades.getInstance();
		produtoService.salvar(listaNovidades);
		
		produto = new Produto("Novo Produto", "Descrição", 10, 100.0f, List.of(), List.of());
		
		produto = produtoService.salvar(produto);
	}
	
	@Then("O produto é adicionado na lista de novos produtos")
	public void produto_e_adicionado_na_lista_de_novos_produtos() {
		listaNovidades = produtoService.obter();
		
		assertTrue(listaNovidades.getProdutos().contains(produto.getId()));
	}
	
	@Given("Um produto antigo esta na novos produtos")
	public void produto_antigo_na_lista_de_novos_produtos() {
		produto = new Produto(new ProdutoId(2), "Produto Antigo", "Descrição", 10, 100.0f, List.of(), List.of(), LocalDate.now().minusDays(40));
		
		listaNovidades = ListaNovidades.getInstance();
		
		listaNovidades.adicionarProduto(produto.getId());
		
		produtoService.salvar(produto);
		produtoService.salvar(listaNovidades);
	}
	
	@When("A verificação é realizada")
	public void verificacao_lista_de_novidades() {
		produtoService.verificarListaNovidades(listaNovidades);
	}
	
	@Then("O produto é removido da lista de novos produtos")
	public void produto_removido_da_lista() {
		assertFalse(listaNovidades.getProdutos().contains(produto.getId()));
	}
}

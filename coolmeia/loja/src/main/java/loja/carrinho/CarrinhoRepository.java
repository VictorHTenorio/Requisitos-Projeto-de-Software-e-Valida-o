package loja.carrinho;

public interface CarrinhoRepository {
	Carrinho salvar(Carrinho carrinho);
	
	Carrinho buscar(CarrinhoId id);
}

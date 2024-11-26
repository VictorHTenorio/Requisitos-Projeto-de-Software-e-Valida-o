package loja.produto;

import java.util.List;
import loja.categoria.CategoriaId;

public interface ProdutoRepository {
	Produto salvar(Produto produto);
	
	Produto obter(ProdutoId id);
	
	List<Produto> obterTodos();
	
	boolean excluir(ProdutoId id);
	
	List<Produto> obterPorCategoria(CategoriaId categoria);
	
	ListaNovidades salvar(ListaNovidades listaNovidades);
	
	ListaNovidades obter();
}

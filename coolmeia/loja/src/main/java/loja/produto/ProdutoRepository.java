package loja.produto;

import java.util.List;
import loja.categoria.Categoria;

public interface ProdutoRepository {
	Produto salvar(Produto produto);
	
	Produto obter(ProdutoId id);
	
	boolean excluir(ProdutoId id);
	
	List<Produto> obterPorCategoria(Categoria categoria);
}

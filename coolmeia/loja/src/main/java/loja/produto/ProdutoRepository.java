package loja.produto;

import java.util.List;
import loja.categoria.Categoria;

public interface ProdutoRepository {
	void salvar(Produto produto);
	
	Produto obter(ProdutoId id);
	
	void excluir(ProdutoId id);
	
	List<Produto> obterPorCategoria(Categoria categoria);
}

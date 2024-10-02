package loja.produto;

import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.lang3.Validate.notNull;

public class ListaNovidades {
	private List<ProdutoId> produtos;
	
	public ListaNovidades() {
		this.produtos = new ArrayList<>();
	}
	
	public ListaNovidades(List<ProdutoId> produtos) {
		setProdutos(produtos);
	}

	public List<ProdutoId> getProdutos() {
		return new ArrayList<>(produtos);
	}

	public void setProdutos(List<ProdutoId> produtos) {
		notNull(produtos, "Os produtos não podem ser vazios");
		this.produtos = new ArrayList<>(produtos);
	}
	
    public void adicionarProduto(ProdutoId produtoId) {
        notNull(produtoId, "O produto não pode ser nulo");
        if (!produtos.contains(produtoId)) {
            produtos.add(produtoId);
        }
    }

    public boolean removerProduto(ProdutoId produtoId) {
        notNull(produtoId, "O produto não pode ser nulo");
        return produtos.remove(produtoId);
    }
}

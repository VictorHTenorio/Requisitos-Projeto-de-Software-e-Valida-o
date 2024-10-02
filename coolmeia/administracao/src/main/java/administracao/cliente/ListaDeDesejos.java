package administracao.cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jmolecules.ddd.types.ValueObject;
import loja.produto.ProdutoId;

import static org.apache.commons.lang3.Validate.notNull;

public class ListaDeDesejos implements ValueObject {
    private final List<ProdutoId> produtos;

    public ListaDeDesejos() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(ProdutoId produto) {
        notNull(produto, "O produto não pode ser nulo");
        produtos.add(produto);
    }

    public void removerProduto(ProdutoId produto) {
        notNull(produto, "O produto não pode ser nulo");
        produtos.remove(produto);
    }

    public List<ProdutoId> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }
    
    public boolean verificarProdutoInLista(ProdutoId produtoId) {
    	notNull(produtoId, "O id do produto não pode ser nulo");
    	if(produtos.contains(produtoId)) {
    		return true;
    	}
    	return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ListaDeDesejos)) return false;
        ListaDeDesejos that = (ListaDeDesejos) obj;
        return produtos.equals(that.produtos);
    }

    @Override
    public int hashCode() {
        return produtos.hashCode();
    }

    @Override
    public String toString() {
        return "ListaDeDesejos{" +
                "produtos=" + produtos +
                '}';
    }
}

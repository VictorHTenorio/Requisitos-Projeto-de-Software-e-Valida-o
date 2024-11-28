package loja.produto;

import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.lang3.Validate.notNull;

public class ListaNovidades {
    private static ListaNovidades instance;
    private List<ProdutoId> produtos;
    
    private ListaNovidades() {
        this.produtos = new ArrayList<>();
    }
    
    public static synchronized ListaNovidades getInstance() {
        if (instance == null) {
            instance = new ListaNovidades();
        }
        return instance;
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

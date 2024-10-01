package loja.carrinho;

import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import loja.produto.ProdutoId;

public class Item implements ValueObject{
	private int quantidade;
	private ProdutoId produto;
	private float valorUnitario;
	
	public Item(int quantidade, ProdutoId produto) {
		isTrue(quantidade>0,"A quantidade precisa ser maior que 0");
		notNull(produto,"O produto não pode ser nulo");
		this.quantidade = quantidade;
		this.produto = produto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}

	public ProdutoId getProduto() {
		return produto;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantidade == item.quantidade && produto.equals(item.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantidade, produto);
    }

    @Override
    public String toString() {
        return "Item{" +
                "quantidade=" + quantidade +
                ", produto=" + produto +
                '}';
    }
}

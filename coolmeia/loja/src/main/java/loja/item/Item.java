package loja.item;

import org.jmolecules.ddd.types.AggregateRoot;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.isTrue;
import loja.produto.ProdutoId;

public class Item implements Cloneable, AggregateRoot<Item, ItemId>{
	private final ItemId id;
	private int quantidade;
	private ProdutoId produto;
	
	public Item(int quantidade, ProdutoId produto) {
		id = null;
		setQuantidade(quantidade);
		setProduto(produto);
	}
	
	public Item(ItemId id, int quantidade, ProdutoId produto) {
		notNull(id,"O id não pode ser nulo");
		this.id = id;
		setQuantidade(quantidade);
		setProduto(produto);
	}

	@Override
	public ItemId getId() {
		return id;
	}
	
	public int getQuantidade() {
		return quantidade;
	}

	public ProdutoId getProduto() {
		return produto;
	}

	private void setQuantidade(int quantidade) {
		isTrue(quantidade>0,"A quantidade precisa ser maior que 0");
		this.quantidade = quantidade;
	}

	private void setProduto(ProdutoId produto) {
		notNull(produto,"O produto não pode ser nulo");
		this.produto = produto;
	}

	@Override
	public Item clone() {
		try {
			return (Item) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}

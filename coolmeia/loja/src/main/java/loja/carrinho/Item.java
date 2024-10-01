package loja.carrinho;

import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;

import loja.cupom.CupomCodigo;
import loja.produto.ProdutoId;

public class Item implements ValueObject{
	private int quantidade;
	private ProdutoId produto;
	private float valorUnitario;
	private CupomCodigo cupom;
	
	public Item(int quantidade, ProdutoId produto, float valorUnitario, CupomCodigo cupom) {
		isTrue(quantidade>0,"A quantidade precisa ser maior que 0");
		notNull(produto,"O produto não pode ser nulo");
		isTrue(valorUnitario>0,"O valor unitario não pode ser menor que 0");
		this.quantidade = quantidade;
		this.produto = produto;
		this.valorUnitario = valorUnitario;
		this.cupom = cupom;
	}
	
	public int getQuantidade() {
		return quantidade;
	}

	public ProdutoId getProduto() {
		return produto;
	}
	
	public float getValorUnitario() {
		return valorUnitario;
	}
	
	public CupomCodigo getCupomCodigo() {
		return cupom;
	}
	
	public void setValorUnitario(float valorUnitario) {
		isTrue(valorUnitario>0,"O valor unitário não pode ser menor que 0");
		this.valorUnitario = valorUnitario;
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

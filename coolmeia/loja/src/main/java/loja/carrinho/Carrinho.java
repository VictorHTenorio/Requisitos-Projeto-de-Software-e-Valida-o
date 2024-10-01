package loja.carrinho;

import java.util.ArrayList;
import java.util.List;
import org.jmolecules.ddd.types.AggregateRoot;
import static org.apache.commons.lang3.Validate.notNull;

public class Carrinho implements Cloneable, AggregateRoot<Carrinho, CarrinhoId>{
	private final CarrinhoId id;
	private float valorTotal;
	private List<Item>itens;
	
	public Carrinho() {
        this.id = null;
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0f;
    }
	
	public Carrinho(CarrinhoId id, List<Item> itens, float valorTotal) {
        notNull(id, "O ID do carrinho não pode ser nulo");
        this.id = id;
        this.itens = new ArrayList<>(itens);
        this.valorTotal = valorTotal;
    }
	
	@Override
    public CarrinhoId getId() {
        return id;
    }
	
	public float getValorTotal() {
        return valorTotal;
    }

    public List<Item> getItens() {
        return new ArrayList<>(itens);
    }

    public void adicionarItem(Item item, float valor) {
        notNull(item, "O item não pode ser nulo");
        itens.add(item);
        atualizarValorTotal(valor);
    }

    public void removerItem(Item item, float valor) {
        notNull(item, "O item não pode ser nulo");
        if (itens.remove(item)) {
            atualizarValorTotal(-valor);
        }
    }

    private void atualizarValorTotal(float valor) {
        this.valorTotal += valor;
    }
    
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }


    @Override
    public Carrinho clone() {
        try {
            Carrinho clone = (Carrinho) super.clone();
            clone.itens.addAll(this.itens);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", valorTotal=" + valorTotal +
                ", itens=" + itens +
                '}';
    }
}

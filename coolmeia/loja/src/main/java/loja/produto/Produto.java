package loja.produto;

import org.jmolecules.ddd.types.AggregateRoot;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.isTrue;

public class Produto implements Cloneable, AggregateRoot<Produto, ProdutoId>{
	private final ProdutoId id;
	private String nome;
	private String descricao;
	private int quantidade;
	private float valor;
	
	public Produto(String nome, String descricao, int quantidade, float valor) {
		this.id = null;
		setNome(nome);
		setDescricao(descricao);
		setQuantidade(quantidade);
		setValor(valor);
	}
	
	public Produto(ProdutoId id, String nome, String descricao, int quantidade, float valor) {
		notNull(id, "O id não pode ser nulo");
		this.id = id;
		setNome(nome);
		setDescricao(descricao);
		setQuantidade(quantidade);
		setValor(valor);
	}

	private void setNome(String nome) {
		notNull(nome, "O nome não pode ser nulo");
		notBlank(nome, "O nome não pode ser vazio");
		
		this.nome = nome;
	}

	private void setDescricao(String descricao) {
		notNull(descricao, "A descrição não pode ser nula");
		notBlank(descricao, "A descrição não pode ser vazia");
		
		this.descricao = descricao;
	}

	private void setQuantidade(int quantidade) {
		isTrue(quantidade > 0, "A quantidade deve ser maior que 0");
		
		this.quantidade = quantidade;
	}

	private void setValor(float valor) {
		isTrue(valor > 0, "O valor deve ser maior que 0");
		
		this.valor = valor;
	}

	@Override
	public ProdutoId getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public float getValor() {
		return valor;
	}
	
	@Override
	public Produto clone() {
		try {
			return (Produto) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	@Override
	public String toString() {
		return nome;
	}
	
}

package loja.categoria;

import static org.apache.commons.lang3.Validate.notNull;
import org.jmolecules.ddd.types.AggregateRoot;
import static org.apache.commons.lang3.Validate.notBlank;

public class Categoria implements Cloneable, AggregateRoot<Categoria,CategoriaId>{
	private final CategoriaId id;
	private String nome;
	
	public Categoria(String nome) {
		id = null;
		setNome(nome);
	}
	
	public Categoria(CategoriaId id, String nome) {
		notNull(id,"O id não pode ser nulo");
		this.id = id;
		setNome(nome);
	}
	
	public CategoriaId getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	private void setNome(String nome) {
		notNull(nome,"O nome não pode ser nulo");
		notBlank(nome,"O nome não pode ser vazio");
		this.nome = nome;
	}
	
	@Override
	public Categoria clone() {
		try {
			return (Categoria) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	@Override
	public String toString() {
		return nome;
	}
}

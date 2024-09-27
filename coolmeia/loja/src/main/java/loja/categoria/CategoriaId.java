package loja.categoria;

import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.isTrue;

import java.util.Objects;

public class CategoriaId implements ValueObject, Identifier{
	private final int id;
	
	public CategoriaId(int id) {
		isTrue(id>0, "O id não pode ser menor que 0");
		
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CategoriaId) {
			var autorId = (CategoriaId) obj;
			return id == autorId.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}
}

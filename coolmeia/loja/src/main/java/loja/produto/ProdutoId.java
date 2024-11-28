package loja.produto;

import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.isTrue;

import java.util.Objects;

public class ProdutoId implements ValueObject, Identifier{
	private final int id;
	
	public ProdutoId(int id) {
		isTrue(id>0, "O id deve ser positivo");
		
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof ProdutoId) {
			var autorId = (ProdutoId) obj;
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

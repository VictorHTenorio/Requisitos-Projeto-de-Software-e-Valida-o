package loja.carrinho;

import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.isTrue;
import java.util.Objects;

public class CarrinhoId implements ValueObject, Identifier{
	private final int id;
	
	public CarrinhoId(int id) {
		isTrue(id>0,"O id não pode ser menor que 0");
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CarrinhoId) {
			var carrinhoId = (CarrinhoId) obj;
			return id == carrinhoId.id;
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

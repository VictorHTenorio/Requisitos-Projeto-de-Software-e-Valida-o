package loja.item;

import java.util.Objects;

import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.isTrue;

public class ItemId implements ValueObject, Identifier{
	private final int id;
	
	public ItemId(int id){
		isTrue(id>0, "O id deve ser maior que 0");
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof ItemId) {
			var autorId = (ItemId) obj;
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

package controle.registroCompra;

import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.ddd.types.ValueObject;

import static org.apache.commons.lang3.Validate.isTrue;
import java.util.Objects;

public class RegistroCompraId implements ValueObject, Identifier {
    private final int id;

    public RegistroCompraId(int id) {
        isTrue(id > 0, "O ID deve ser maior que zero.");
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof RegistroCompraId) {
			var registroCompraId = (RegistroCompraId) obj;
			return id == registroCompraId.id;
		}
		return false;
	}
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

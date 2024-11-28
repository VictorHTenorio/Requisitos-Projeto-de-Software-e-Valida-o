package loja.cupom;

import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import java.util.Objects;
import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.ddd.types.ValueObject;

public class CupomCodigo implements ValueObject, Identifier{
	private final String codigo;
	
	public CupomCodigo(String codigo) {
		notNull(codigo, "O codigo não pode ser nulo");
		notBlank(codigo,"O codigo não pode ser vazio");
		this.codigo = codigo;
	}
	
	public String getId() {
		return codigo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CupomCodigo) {
			var cupomId = (CupomCodigo) obj;
			return codigo.equals(cupomId.codigo);
		} 
		return false;
	}	

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}
}

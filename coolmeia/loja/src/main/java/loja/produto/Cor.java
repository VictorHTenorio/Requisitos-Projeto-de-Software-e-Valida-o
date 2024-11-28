package loja.produto;

import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.regex.Pattern;


public class Cor implements ValueObject{
	private final String nome;
	private final String hex;
	private static final Pattern HEX_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
	
	public Cor(String nome, String hex) {
		notNull(nome, "O nome não pode ser nulo");
		notNull(hex, "A cor não pode ser nula");
		if (!HEX_PATTERN.matcher(hex).matches()) {
            throw new IllegalArgumentException("Formato de cor inválido");
        }
		
		this.nome = nome;
		this.hex = hex;
	}
	
	public String getNome() {
        return nome;
    }

    public String getHex() {
        return hex;
    }
}

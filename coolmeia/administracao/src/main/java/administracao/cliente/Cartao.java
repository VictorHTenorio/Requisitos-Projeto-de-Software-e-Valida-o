package administracao.cliente;

import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.notNull;
import java.util.Objects;
import static org.apache.commons.lang3.Validate.isTrue;

public class Cartao implements ValueObject {
    private final String numero;
    private final String validade;
    private final String cvv;

    public Cartao(String numero, String validade, String cvv) {
        notNull(numero, "O número do cartão não pode ser nulo");
        notNull(validade, "A validade não pode ser nula");
        notNull(cvv, "O CVV não pode ser nulo");
        
        isTrue(validarNumero(numero), "O número do cartão de crédito é inválido");
        isTrue(validarValidade(validade), "A validade do cartão é inválida");
        isTrue(validarCvv(cvv), "O CVV do cartão é inválido");

        this.numero = numero;
        this.validade = validade;
        this.cvv = cvv;
    }

    private boolean validarNumero(String numero) {
        return numero.matches("\\d{16}");
    }

    private boolean validarValidade(String validade) {
        return validade.matches("\\d{2}/\\d{2}");
    }

    private boolean validarCvv(String cvv) {
        return cvv.matches("\\d{3}");
    }

    public String getNumero() {
        return numero;
    }

    public String getValidade() {
        return validade;
    }

    public String getCvv() {
        return cvv;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cartao) {
        	Cartao outro = (Cartao) obj;
            return numero.equals(outro.numero) &&
                   validade.equals(outro.validade) &&
                   cvv.equals(outro.cvv);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, validade, cvv);
    }

    @Override
    public String toString() {
        return String.format("Cartão de Crédito: %s, Válido até: %s", numero, validade);
    }
}

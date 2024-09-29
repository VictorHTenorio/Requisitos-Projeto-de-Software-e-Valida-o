package administracao.cliente;

import static org.apache.commons.lang3.Validate.isTrue;
import java.util.Objects;
import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.ddd.types.ValueObject;

public class ClienteId implements ValueObject, Identifier {
    private final String cpf;

    public ClienteId(String cpf) {
        isTrue(validarCpf(cpf), "O CPF fornecido é inválido");
        this.cpf = cpf.replaceAll("\\D", "");
    }

    public String getCpf() {
        return cpf;
    }

    private boolean validarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int primeiroDigitoVerificador = 11 - (soma % 11);
            if (primeiroDigitoVerificador >= 10) {
                primeiroDigitoVerificador = 0;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int segundoDigitoVerificador = 11 - (soma % 11);
            if (segundoDigitoVerificador >= 10) {
                segundoDigitoVerificador = 0;
            }

            return primeiroDigitoVerificador == Character.getNumericValue(cpf.charAt(9))
                && segundoDigitoVerificador == Character.getNumericValue(cpf.charAt(10));

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ClienteId) {
            var clienteId = (ClienteId) obj;
            return cpf.equals(clienteId.cpf);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s-%s", 
        	cpf.substring(0, 3),
        	cpf.substring(3, 6),
        	cpf.substring(6, 9),
        	cpf.substring(9, 11));
    }
}

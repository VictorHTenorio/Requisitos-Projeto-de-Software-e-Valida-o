package administracao.cliente;

import org.jmolecules.ddd.types.ValueObject;

import loja.pagamento.MetodoPagamento;

import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;
import java.util.Objects;
import static org.apache.commons.lang3.Validate.isTrue;

public class Cartao implements ValueObject, MetodoPagamento{
    private final String nome;
    private final String numero;
    private final String validade;
    private final String cvv;

    public Cartao(String nome, String numero, String validade, String cvv) {
        notNull(nome, "O nome do titular não pode ser nulo");
        notBlank(nome, "O nome do titular não pode ser vazio");
        notNull(numero, "O número do cartão não pode ser nulo");
        notNull(validade, "A validade não pode ser nula");
        notNull(cvv, "O CVV não pode ser nulo");

        isTrue(validarNumero(numero), "O número do cartão de crédito é inválido");
        isTrue(validarValidade(validade), "A validade do cartão é inválida");
        isTrue(validarCvv(cvv), "O CVV do cartão é inválido");

        this.nome = nome;
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

    public String getNome() {
        return nome;
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
            return nome.equals(outro.nome) &&
                   numero.equals(outro.numero) &&
                   validade.equals(outro.validade) &&
                   cvv.equals(outro.cvv);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, numero, validade, cvv);
    }

    @Override
    public String toString() {
        return String.format("Cartão de Crédito de %s: %s, Válido até: %s", nome, numero, validade);
    }

	@Override
	public boolean processarPagamento() {
		System.out.println("Pagamento via cartão processado com sucesso.");
		return true;
	}

	@Override
	public String getMetodo() {
		return toString();
	}
}

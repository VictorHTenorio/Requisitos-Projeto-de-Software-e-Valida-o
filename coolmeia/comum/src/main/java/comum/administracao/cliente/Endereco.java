package comum.administracao.cliente;

import org.jmolecules.ddd.types.ValueObject;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;

public class Endereco implements ValueObject {
    private final String cep;
    private final String cidade;
    private final String bairro;
    private final String rua;
    private final int numero;

    public Endereco(String cep, String cidade, String bairro, String rua, int numero) {
        notNull(cep, "O CEP não pode ser nulo");
        notBlank(cep, "O CEP não pode estar em branco");
        isTrue(validarCep(cep), "O CEP fornecido é inválido");

        notNull(cidade, "A cidade não pode ser nula");
        notBlank(cidade, "A cidade não pode estar em branco");

        notNull(bairro, "O bairro não pode ser nulo");
        notBlank(bairro, "O bairro não pode estar em branco");

        notNull(rua, "A rua não pode ser nula");
        notBlank(rua, "A rua não pode estar em branco");

        isTrue(numero > 0, "O número deve ser positivo");

        this.cep = cep;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    private boolean validarCep(String cep) {
        return cep.matches("\\d{5}-?\\d{3}");
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Endereco) {
            Endereco outro = (Endereco) obj;
            return cep.equals(outro.cep) &&
                   cidade.equals(outro.cidade) &&
                   bairro.equals(outro.bairro) &&
                   rua.equals(outro.rua) &&
                   numero == outro.numero;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, cidade, bairro, rua, numero);
    }

    @Override
    public String toString() {
        return String.format("Endereço: %s, %s, %s, %s, nº %d", rua, bairro, cidade, cep, numero);
    }
}

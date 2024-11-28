package loja.cupom;

import java.time.LocalDate;
import org.jmolecules.ddd.types.ValueObject;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Periodo implements ValueObject {
    private final LocalDate inicio;
    private final LocalDate fim;

    public Periodo(LocalDate inicio, LocalDate fim) {
        notNull(inicio, "O início não pode ser nulo");
        notNull(fim, "O fim não pode ser nulo");
        isTrue(inicio.compareTo(fim) <= 0, "O início não pode ser maior que o fim");

        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public boolean isValido(LocalDate data) {
        notNull(data, "A data não pode ser nula");
        return (data.compareTo(inicio) >= 0 && data.compareTo(fim) <= 0);
    }

    public boolean isExpirado() {
        return LocalDate.now().isAfter(fim);
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }
}

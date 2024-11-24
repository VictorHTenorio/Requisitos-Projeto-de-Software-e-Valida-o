package jpa.administracao.cliente;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import java.io.Serializable;

@Embeddable
public class ClienteIdJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    protected ClienteIdJpaEntity() {}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
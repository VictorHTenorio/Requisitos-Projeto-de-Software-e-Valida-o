package controle.registroCompra;

import org.jmolecules.ddd.types.AggregateRoot;

import administracao.cliente.ClienteId;

import static org.apache.commons.lang3.Validate.notNull;

import loja.compra.CompraId;

public class RegistroCompra implements Cloneable, AggregateRoot<RegistroCompra, RegistroCompraId> {
    private final RegistroCompraId id;
    private CompraId compra;
    private ClienteId cliente;

    public RegistroCompra(CompraId compra, ClienteId cliente) {
        this.id = null;
        setCompra(compra);
        setCliente(cliente);
    }

    public RegistroCompra(RegistroCompraId id, CompraId compra, ClienteId cliente) {
        this.id = notNull(id, "O ID do registro de compra não pode ser nulo.");
        setCompra(compra);
        setCliente(cliente);
    }

    @Override
    public RegistroCompraId getId() {
        return id;
    }

    public CompraId getCompra() {
        return compra;
    }

    public ClienteId getCliente() {
        return cliente;
    }

    private void setCompra(CompraId compra) {
        notNull(compra, "O ID da compra não pode ser nulo.");
        this.compra = compra;
    }

    private void setCliente(ClienteId cliente) {
        notNull(cliente, "O ID do cliente não pode ser nulo.");
        this.cliente = cliente;
    }

    @Override
    public RegistroCompra clone() {
        try {
            return (RegistroCompra) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public String toString() {
        return "RegistroCompra{" +
                "id=" + id +
                ", compra=" + compra +
                ", cliente=" + cliente +
                '}';
    }
}

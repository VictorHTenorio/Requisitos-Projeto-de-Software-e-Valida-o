package loja.compra;

import org.jmolecules.ddd.types.AggregateRoot;

import comum.administracao.cliente.Endereco;
import loja.carrinho.CarrinhoId;

import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.isTrue;

public class Compra implements Cloneable, AggregateRoot<Compra, CompraId> {
    private final CompraId id;
    private float frete;
    private CarrinhoId carrinhoId;
    private Endereco enderecoEntrega;

    public Compra(CarrinhoId carrinhoId, Endereco enderecoEntrega, float frete) {
        this.id = null;
        setCarrinhoId(carrinhoId);
        setEnderecoEntrega(enderecoEntrega);
        setFrete(frete);
    }

    public Compra(CompraId id, CarrinhoId carrinhoId, Endereco enderecoEntrega, float frete) {
    	notNull(id, "O id não pode ser nulo");
    	this.id = id;
        setCarrinhoId(carrinhoId);
        setEnderecoEntrega(enderecoEntrega);
        setFrete(frete);
    }

    @Override
    public CompraId getId() {
        return id;
    }

    public float getFrete() {
        return frete;
    }

    public CarrinhoId getCarrinhoId() {
        return carrinhoId;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    private void setFrete(float frete) {
        isTrue(frete >= 0, "O frete não pode ser negativo");
        this.frete = frete;
    }

    private void setCarrinhoId(CarrinhoId carrinhoId) {
        notNull(carrinhoId, "O ID do carrinho não pode ser nulo");
        this.carrinhoId = carrinhoId;
    }

    private void setEnderecoEntrega(Endereco enderecoEntrega) {
        notNull(enderecoEntrega, "O endereço de entrega não pode ser nulo");
        this.enderecoEntrega = enderecoEntrega;
    }

    @Override
    public Compra clone() {
        try {
            return (Compra) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", frete=" + frete +
                ", carrinhoId=" + carrinhoId +
                ", enderecoEntrega=" + enderecoEntrega +
                '}';
    }
}

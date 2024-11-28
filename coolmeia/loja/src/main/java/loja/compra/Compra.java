package loja.compra;

import org.jmolecules.ddd.types.AggregateRoot;

import comum.administracao.cliente.Endereco;
import loja.carrinho.CarrinhoId;
import loja.pagamento.MetodoPagamento;

import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.isTrue;

public class Compra implements Cloneable, AggregateRoot<Compra, CompraId> {
    private final CompraId id;
    private float frete;
    private CarrinhoId carrinhoId;
    private Endereco enderecoEntrega;
    private MetodoPagamento pagamento;

    public Compra(CarrinhoId carrinhoId, Endereco enderecoEntrega, float frete, MetodoPagamento pagamento) {
        this.id = null;
        setCarrinhoId(carrinhoId);
        setEnderecoEntrega(enderecoEntrega);
        setFrete(frete);
        setPagamento(pagamento);
    }

    public Compra(CompraId id, CarrinhoId carrinhoId, Endereco enderecoEntrega, float frete, MetodoPagamento pagamento) {
    	notNull(id, "O id não pode ser nulo");
    	this.id = id;
        setCarrinhoId(carrinhoId);
        setEnderecoEntrega(enderecoEntrega);
        setFrete(frete);
        setPagamento(pagamento);
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
    
    public MetodoPagamento getMetodoPagamento() {
    	return pagamento;
    }
    
    public String getPagamento() {
    	return pagamento.getMetodo();
    }
    
    public boolean processarPagmento() {
    	return pagamento.processarPagamento();
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
    
    private void setPagamento(MetodoPagamento pagamento) {
        notNull(pagamento, "O método de pagamento não pode ser nulo");
        this.pagamento = pagamento;
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

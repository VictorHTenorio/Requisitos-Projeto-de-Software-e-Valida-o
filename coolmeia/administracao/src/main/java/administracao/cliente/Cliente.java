package administracao.cliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jmolecules.ddd.types.AggregateRoot;

import comum.administracao.cliente.Endereco;
import comum.administracao.notificacao.GerenciadorNotificacoes;
import comum.administracao.notificacao.Observer;
import loja.carrinho.CarrinhoId;
import loja.produto.ProdutoId;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

public class Cliente implements Cloneable, AggregateRoot<Cliente, ClienteId>, Observer {
    private final ClienteId id;
    private String nome;
    private String email;
    private String senha;
    private Date nascimento;
    private CarrinhoId carrinhoId;
    private ListaDeDesejos listaDeDesejos;
    private List<Endereco> enderecos;
    private List<Cartao> cartoes;

    public Cliente(ClienteId id, String nome, String email, CarrinhoId carrinho) { //sem cadastro
    	notNull(id, "O id não pode ser nulo");
    	this.id = id;
        setNome(nome);
        setEmail(email);
        setCarrinhoId(carrinho);
        this.listaDeDesejos = new ListaDeDesejos();
        this.enderecos = new ArrayList<>();
        this.cartoes = new ArrayList<>();
    }

    public Cliente(ClienteId id, String nome, String email, String senha, Date nascimento, CarrinhoId carrinho) {
    	notNull(id, "O id não pode ser nulo");
    	this.id = id;
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setNascimento(nascimento);
        setCarrinhoId(carrinho);
        this.listaDeDesejos = new ListaDeDesejos();
        this.enderecos = new ArrayList<>();
        this.cartoes = new ArrayList<>();
    }

    public ClienteId getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public CarrinhoId getCarrinhoId() {
        return carrinhoId;
    }

    public ListaDeDesejos getListaDeDesejos() {
        return listaDeDesejos;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    private void setNome(String nome) {
        notBlank(nome, "O nome não pode ser em branco");
        this.nome = nome;
    }

    private void setEmail(String email) {
        notBlank(email, "O email não pode ser em branco");
        this.email = email;
    }

    public void setSenha(String senha) {
        notBlank(senha, "A senha não pode ser em branco");
        this.senha = senha;
    }

    public void setNascimento(Date nascimento) {
        notNull(nascimento, "A data de nascimento não pode ser nula");
        this.nascimento = nascimento;
    }

    public void setCarrinhoId(CarrinhoId carrinhoId) {
        notNull(carrinhoId, "O carrinho não pode ser nulo");
        this.carrinhoId = carrinhoId;
    }

    public void adicionarEndereco(Endereco endereco) {
        notNull(endereco, "O endereço não pode ser nulo");
        this.enderecos.add(endereco);
    }

    public void removerEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
    }

    public void adicionarCartao(Cartao cartao) {
        notNull(cartao, "O cartão não pode ser nulo");
        this.cartoes.add(cartao);
    }

    public void removerCartao(Cartao cartao) {
        this.cartoes.remove(cartao);
    }
    
    @Override
    public void receberNotificacao(String mensagem) {
        System.out.println("Cliente " + this.nome + " recebeu notificação: " + mensagem);
    }

    public void adicionarProdutoListaDeDesejos(ProdutoId produto) {
        notNull(produto, "O produto não pode ser nulo");
        this.listaDeDesejos.adicionarProduto(produto);
        GerenciadorNotificacoes.getInstance().adicionarObservador(this, produto.toString());
    }

    public void removerProdutoListaDeDejos(ProdutoId produto) {
        notNull(produto, "O produto não pode ser nulo");
        this.listaDeDesejos.removerProduto(produto);
        GerenciadorNotificacoes.getInstance().removerObservador(this, produto.toString());
    }

    @Override
    public Cliente clone() {
        try {
            return (Cliente) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s, Email: %s, Nascimento: %s", nome, email, nascimento);
    }
}

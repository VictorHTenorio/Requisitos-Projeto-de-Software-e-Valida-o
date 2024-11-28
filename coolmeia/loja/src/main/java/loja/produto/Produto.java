package loja.produto;

import org.jmolecules.ddd.types.AggregateRoot;

import comum.administracao.notificacao.GerenciadorNotificacoes;
import loja.categoria.CategoriaId;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.Validate.isTrue;

public class Produto implements Cloneable, AggregateRoot<Produto, ProdutoId>{
	private final ProdutoId id;
	private String nome;
	private String descricao;
	private int quantidade;
	private float valor;
	private List<Cor> cores;
	private List<CategoriaId> categorias;
	private LocalDate dataAdicao;
	
	public Produto(String nome, String descricao, int quantidade, float valor, List<Cor> cores, List<CategoriaId> categorias) {
		this.id = null;
		setNome(nome);
		setDescricao(descricao);
		setQuantidade(quantidade);
		setValor(valor);
		setCores(cores);
        setCategorias(categorias);
        this.dataAdicao = java.time.LocalDate.now();
	}
	
	public Produto(ProdutoId id, String nome, String descricao, int quantidade, float valor, List<Cor> cores, List<CategoriaId> categorias, LocalDate dataAdicao) {
		notNull(id, "O id não pode ser nulo");
		this.id = id;
		setNome(nome);
		setDescricao(descricao);
		setQuantidade(quantidade);
		setValor(valor);
		setCores(cores);
        setCategorias(categorias);
        setDataAdicao(dataAdicao);
	}

	private void setNome(String nome) {
		notNull(nome, "O nome não pode ser nulo");
		notBlank(nome, "O nome não pode ser vazio");
		
		this.nome = nome;
	}

	private void setDescricao(String descricao) {
		notNull(descricao, "A descrição não pode ser nula");
		notBlank(descricao, "A descrição não pode ser vazia");
		
		this.descricao = descricao;
	}

	private void setQuantidade(int quantidade) {
		isTrue(quantidade > 0, "A quantidade deve ser maior que 0");
		
		this.quantidade = quantidade;
	}

	private void setValor(float valor) {
		isTrue(valor > 0, "O valor deve ser maior que 0");
		
		this.valor = valor;
	}
	
	private void setCores(List<Cor> cores) {
        notNull(cores, "A lista de cores não pode ser nula");
        this.cores = new ArrayList<>(cores);
    }

    private void setCategorias(List<CategoriaId> categorias) {
        notNull(categorias, "A lista de categorias não pode ser nula");
        this.categorias = new ArrayList<>(categorias);
    }
    
    private void setDataAdicao(LocalDate dataAdicao) {
    	notNull(dataAdicao, "A data de adição não pode ser nula");
    	this.dataAdicao = dataAdicao;
    }

	@Override
	public ProdutoId getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public float getValor() {
		return valor;
	}
	
	public LocalDate getDataAdicao() {
		return dataAdicao;
	}
	
	public List<Cor> getCores() {
        return new ArrayList<>(cores);
    }

    public List<CategoriaId> getCategorias() {
        return new ArrayList<>(categorias);
    }

    public void adicionarCor(Cor cor) {
        notNull(cor, "A cor não pode ser nula");
        cores.add(cor);
    }

    public void adicionarCategoria(CategoriaId categoriaId) {
        notNull(categoriaId, "A categoria não pode ser nula");
        categorias.add(categoriaId);
    }
	
    public void diminuirQuantidade(int quantidadeCompra) {
        isTrue(quantidade - quantidadeCompra >= 0, "A quantidade comprada não pode ser maior que o estoque");
        this.quantidade -= quantidadeCompra;

        if (this.quantidade < 10) {
            String mensagem = String.format(
                "Últimas %d peças!",
                this.nome, 
                this.quantidade
            );
            GerenciadorNotificacoes.getInstance()
                .notificarObservadores(this.id.toString(), mensagem);
        }
    }
    
    public void aumentarQuantidade(int quantidadeAdicionada) {
        isTrue(quantidadeAdicionada > 0, "A quantidade não pode ser negativa");
        this.quantidade += quantidadeAdicionada;
    }
	
	public boolean verificarPoucaQuantidade(int poucaQuantidade) {
		isTrue(poucaQuantidade > 0, "A quantidade não pode ser menor que 0");
		if(quantidade <= poucaQuantidade) {
			return true;
		}
		return false;
	}
	
	@Override
	public Produto clone() {
		try {
			return (Produto) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	@Override
	public String toString() {
		return nome;
	}
	
}

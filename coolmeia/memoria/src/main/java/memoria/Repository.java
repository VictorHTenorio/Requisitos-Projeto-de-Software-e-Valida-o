package memoria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;
import administracao.cliente.Cliente;
import administracao.cliente.ClienteId;
import administracao.cliente.ClienteRepository;
import controle.registroCompra.RegistroCompra;
import controle.registroCompra.RegistroCompraId;
import controle.registroCompra.RegistroCompraRepository;
import loja.carrinho.Carrinho;
import loja.carrinho.CarrinhoId;
import loja.carrinho.CarrinhoRepository;
import loja.categoria.Categoria;
import loja.categoria.CategoriaId;
import loja.categoria.CategoriaRepository;
import loja.compra.Compra;
import loja.compra.CompraId;
import loja.compra.CompraRepository;
import loja.cupom.Cupom;
import loja.cupom.CupomCodigo;
import loja.cupom.CupomRepository;
import loja.produto.ListaNovidades;
import loja.produto.Produto;
import loja.produto.ProdutoId;
import loja.produto.ProdutoRepository;

public class Repository implements ClienteRepository, RegistroCompraRepository, CategoriaRepository, CarrinhoRepository, CompraRepository, CupomRepository, ProdutoRepository{
    private int registroCompraIdCounter = 1;
    private int categoriaIdCounter = 1;
    private int carrinhoIdCounter = 1;
    private int compraIdCounter = 1;
    private int produtoIdCounter = 1;
    private ListaNovidades listaNovidades;
    
	/*-----------------------------Cliente------------------------------------------*/
	private Map<ClienteId, Cliente> clientes = new HashMap<>();
	@Override
	public Cliente salvar(Cliente cliente) {
		notNull(cliente, "O cliente não pode ser nulo");
		return clientes.put(cliente.getId(), cliente);
	}

	@Override
	public Cliente obter(ClienteId id) {
		notNull(id, "O id do cliente não pode ser nulo");
		return clientes.get(id);
	}

	@Override
	public boolean excluir(ClienteId id) {
		notNull(id, "O id do cliente não pode ser nulo");
		return clientes.remove(id) != null;
	}
	
	/*-----------------------------RegistroCompra------------------------------------------*/
	private Map<RegistroCompraId, RegistroCompra> registrosCompra = new HashMap<>();
	@Override
	public RegistroCompra salvar(RegistroCompra registroCompra) {
        notNull(registroCompra, "O registro de compra não pode ser nulo");
        if (registroCompra.getId() == null) {
            RegistroCompraId novoId = new RegistroCompraId(registroCompraIdCounter++);
            registroCompra = new RegistroCompra(novoId, registroCompra.getDataHoraRealizacao(), registroCompra.getCompra(), registroCompra.getCliente());
        }
        
        registrosCompra.put(registroCompra.getId(), registroCompra);
        return registroCompra;
    }

	@Override
	public RegistroCompra obter(RegistroCompraId id) {
        notNull(id, "O ID do registro de compra não pode ser nulo");
        return registrosCompra.get(id);
    }
	
	/*-----------------------------Categoria------------------------------------------*/
	private Map<CategoriaId, Categoria> categorias = new HashMap<>();
	@Override
	public Categoria salvar(Categoria categoria) {
        notNull(categoria, "A categoria não pode ser nula");
        if (categoria.getId() == null) {
            CategoriaId novoId = new CategoriaId(categoriaIdCounter++);
            categoria = new Categoria(novoId, categoria.getNome());
        }
        categorias.put(categoria.getId(), categoria);
        return categoria;
    }

	@Override
	public Optional<Categoria> obter(CategoriaId id) {
        notNull(id, "O ID da categoria não pode ser nulo");
        return Optional.ofNullable(categorias.get(id));
    }

	@Override
	public boolean excluir(CategoriaId id) {
        notNull(id, "O ID da categoria não pode ser nulo");
        return categorias.remove(id) != null;
    }
	
	/*-----------------------------Carrinho------------------------------------------*/
	private Map<CarrinhoId, Carrinho> carrinhos = new HashMap<>();
	@Override
	public Carrinho salvar(Carrinho carrinho) {
        notNull(carrinho, "O carrinho não pode ser nulo");
        if (carrinho.getId() == null) {
            CarrinhoId novoId = new CarrinhoId(carrinhoIdCounter++);
            carrinho = new Carrinho(novoId, carrinho.getItens(), carrinho.getValorTotal());
        }
        carrinhos.put(carrinho.getId(), carrinho);
        return carrinho;
    }

	@Override
	public Carrinho obter(CarrinhoId id) {
        notNull(id, "O ID do carrinho não pode ser nulo");
        return carrinhos.get(id);
    }
	
	/*-----------------------------Compra------------------------------------------*/
	private Map<CompraId, Compra> compras = new HashMap<>();
	@Override
	public Compra salvar(Compra compra) {
        notNull(compra, "A compra não pode ser nula");
        if (compra.getId() == null) {
            CompraId novoId = new CompraId(compraIdCounter++);
            compra = new Compra(novoId, compra.getCarrinhoId(), compra.getEnderecoEntrega(),compra.getFrete(),compra.getMetodoPagamento());
        }
        compras.put(compra.getId(), compra);
        return compra;
    }

	@Override
	public Compra obter(CompraId id) {
        notNull(id, "O ID da compra não pode ser nulo");
        return compras.get(id);
    }
	
	/*-----------------------------Cupom------------------------------------------*/
	
	private Map<CupomCodigo, Cupom> cupons = new HashMap<>();
	@Override
	public Cupom salvar(Cupom cupom) {
		notNull(cupom,"O cupom não pode ser nulo");
		return cupons.put(cupom.getId(), cupom);
	}

	@Override
	public Cupom obter(CupomCodigo codigo) {
		notNull(codigo,"O codigo do cupom não pode ser nulo");
		return cupons.get(codigo);
	}

	@Override
	public boolean excluir(CupomCodigo codigo) {
		notNull(codigo,"O codigo do cupom não pode ser nulo");
		return cupons.remove(codigo) != null;
	}
	
	/*-----------------------------Produto------------------------------------------*/
	private Map<ProdutoId, Produto> produtos = new HashMap<>();
	@Override
	public Produto salvar(Produto produto) {
        notNull(produto, "O produto não pode ser nulo");
        if (produto.getId() == null) {
            ProdutoId novoId = new ProdutoId(produtoIdCounter++);
            produto = new Produto(novoId, produto.getNome(), produto.getDescricao(),produto.getQuantidade(),produto.getValor(), produto.getCores(), produto.getCategorias(), produto.getDataAdicao());
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

	@Override
	public Produto obter(ProdutoId id) {
        notNull(id, "O ID do produto não pode ser nulo");
        return produtos.get(id);
    }

	@Override
	public boolean excluir(ProdutoId id) {
        notNull(id, "O ID do produto não pode ser nulo");
        return produtos.remove(id) != null;
    }

	@Override
	public List<Produto> obterPorCategoria(CategoriaId categoriaId) {
        notNull(categoriaId, "O id da categoria não pode ser nulo");
        return produtos.values().stream()
                .filter(produto -> produto.getCategorias().contains(categoriaId))
                .collect(Collectors.toList());
    }

	@Override
	public ListaNovidades salvar(ListaNovidades listaNovidades) {
		notNull(listaNovidades, "A lista de novidades não pode ser nula");
        this.listaNovidades = listaNovidades;
        return this.listaNovidades;
	}

	@Override
	public ListaNovidades obter() {
		return this.listaNovidades;
	}

	@Override
	public List<Categoria> obterTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findSenhaByCpf(String cpf) {
		return null;
	}	
	
	public List<Produto> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}

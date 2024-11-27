package loja.carrinho;


import loja.categoria.CategoriaId;
import loja.cupom.Cupom;
import loja.cupom.CupomCodigo;
import loja.cupom.CupomService;
import loja.produto.Produto;
import loja.produto.ProdutoService;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {
	private final CarrinhoRepository carrinhoRepository;
	private final CupomService cupomService;
	private final ProdutoService produtoService;
	
	public CarrinhoService(CarrinhoRepository carrinhoRepository, CupomService cupomService, ProdutoService produtoService) {
		notNull(carrinhoRepository, "O repositório não pode ser nulo");
		notNull(cupomService, "O serviço do cupom não pode ser nulo");
		notNull(produtoService,"O serviço do produto não pode ser nulo");
		
		this.carrinhoRepository = carrinhoRepository;
		this.cupomService = cupomService;
		this.produtoService = produtoService;
	}
	
	public Carrinho salvar(Carrinho carrinho) {
		notNull(carrinho, "O carrinho não pode ser nulo");
		
		return carrinhoRepository.salvar(carrinho);
	}
	
	public Carrinho obter(CarrinhoId id) {
		notNull(id, "O carrinho id não pode ser nulo");
		
		return carrinhoRepository.obter(id);
	}
	
	public CarrinhoId criarNovoCarrinho() {
		Carrinho novoCarrinho = new Carrinho();
		novoCarrinho = carrinhoRepository.salvar(novoCarrinho);
		
		return novoCarrinho.getId();
	}
	
	public boolean aplicarCupom(CupomCodigo cupomCodigo, CarrinhoId carrinhoId) {
	    notNull(cupomCodigo, "O código do cupom não pode ser nulo");
	    notNull(carrinhoId, "O id do carrinho não pode ser nulo");

	    Cupom cupom = cupomService.obter(cupomCodigo);
	    if (!cupom.isValido()) return false;

	    Carrinho carrinho = carrinhoRepository.obter(carrinhoId);
	    List<Item> novosItens = new ArrayList<>();
	    float novoValorTotal = 0.0f;
	    boolean algumItemAlterado = false;

	    for (Item item : carrinho.getItens()) {
	      if (item.getCupomCodigo() != null) {
	        novosItens.add(item);
	        novoValorTotal += item.getValorUnitario() * item.getQuantidade();
	        continue;
	      }

	      Produto produto = produtoService.obter(item.getProduto());
	      boolean cupomValidoParaItem = false;
	      if(!cupom.getProdutos().isEmpty()) {
	    	  cupomValidoParaItem = cupom.isAplicavelAProduto(item.getProduto());
	      }
	      if(cupomValidoParaItem == false && !cupom.getCategorias().isEmpty()) {
	    	  cupomValidoParaItem = produto.getCategorias().stream().anyMatch(cupom::isAplicavelACategoria);
	      }

	      if (cupomValidoParaItem) {
	        float valorComDesconto = item.getValorUnitario() * (1 - cupom.getPorcentagemDesconto() / 100.0f);
	        novosItens.add(new Item(item.getQuantidade(), item.getProduto(), valorComDesconto, cupomCodigo));
	        novoValorTotal += valorComDesconto * item.getQuantidade();
	        algumItemAlterado = true;
	      } else {
	        novosItens.add(item);
	        novoValorTotal += item.getValorUnitario() * item.getQuantidade();
	      }
	    }

	    if (algumItemAlterado) {
	      carrinhoRepository.salvar(new Carrinho(carrinho.getId(), novosItens, novoValorTotal));
	      return true;
	    }
	    
	    return false;
	  }
}

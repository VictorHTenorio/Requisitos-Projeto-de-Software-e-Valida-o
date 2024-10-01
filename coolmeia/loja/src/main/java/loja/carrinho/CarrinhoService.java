package loja.carrinho;

import org.jmolecules.ddd.annotation.Service;

import loja.categoria.CategoriaId;
import loja.cupom.Cupom;
import loja.cupom.CupomCodigo;
import loja.cupom.CupomService;
import loja.produto.Produto;
import loja.produto.ProdutoService;

import static org.apache.commons.lang3.Validate.notNull;

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
	    Carrinho carrinho = carrinhoRepository.obter(carrinhoId);
	    
	    if (!cupom.isValido()) {
	        throw new IllegalStateException("O cupom é inválido ou está expirado");
	    }
	    
	    boolean cupomAplicado = false;
	    
	    for (Item item : carrinho.getItens()) {
	        Produto produto = produtoService.obter(item.getProduto());
	        
	        if (cupom.isAplicavelAProduto(item.getProduto())) {
	            aplicarDesconto(item, cupom);
	            cupomAplicado = true;
	        } else {
	            for (CategoriaId categoriaId : produto.getCategorias()) {
	                if (cupom.isAplicavelACategoria(categoriaId)) {
	                    aplicarDesconto(item, cupom);
	                    cupomAplicado = true;
	                    break;
	                }
	            }
	        }
	    }
	    
	    if (cupomAplicado) {
	        carrinhoRepository.salvar(carrinho);
	    }
	    
	    return cupomAplicado;
	}
	
	private void aplicarDesconto(Item item, Cupom cupom) {
	    float valorOriginal = item.getValorUnitario();
	    float desconto = valorOriginal * (cupom.getPorcentagemDesconto() / 100.0f);
	    float valorComDesconto = valorOriginal - desconto;
	    item.setValorUnitario(valorComDesconto);
	}
}

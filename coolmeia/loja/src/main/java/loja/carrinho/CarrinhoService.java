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

	    // Verifica se o cupom é válido
	    if (!cupom.isValido()) {
	        // Retorna falso, indicando que o cupom não foi aplicado e não altera o valor
	        return false;
	    }

	    boolean cupomAplicado = false;
	    float valorTotalComDesconto = carrinho.getValorTotal(); // Mantém o valor original se o cupom não for aplicável

	    // Tenta aplicar o desconto item por item
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

	    // Atualiza o valor total do carrinho SOMENTE se o cupom foi aplicado
	    if (cupomAplicado) {
	        valorTotalComDesconto = carrinho.getItens().stream()
	            .map(item -> item.getValorUnitario() * item.getQuantidade())
	            .reduce(0.0f, Float::sum); // Recalcula o valor total com o desconto
	        carrinho.setValorTotal(valorTotalComDesconto);
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

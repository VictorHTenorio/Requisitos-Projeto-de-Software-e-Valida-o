package loja.compra;

import static org.apache.commons.lang3.Validate.notNull;
import org.springframework.stereotype.Service;
import loja.carrinho.Carrinho;
import loja.carrinho.CarrinhoService;
import loja.carrinho.Item;
import loja.produto.Produto;
import loja.produto.ProdutoService;

@Service
public class CompraService {
	private final CompraRepository compraRepository;
	private final CarrinhoService carrinhoService;
	private final ProdutoService produtoService;
	
	public CompraService(CompraRepository compraRepository, CarrinhoService carrinhoService, ProdutoService produtoService) {
		notNull(compraRepository, "O repositório da compra não pode ser nulo");
		notNull(carrinhoService, "O service do carrinho não pode ser nulo");
		notNull(carrinhoService, "O service do produto não pode ser nulo");
		
		this.compraRepository = compraRepository;
		this.carrinhoService = carrinhoService;
		this.produtoService = produtoService;
	}
	
	public Compra salvar(Compra compra) {
		notNull(compra, "A compra não pode ser nula");
		
		return compraRepository.salvar(compra);
	}
	
	public Compra obter(CompraId id) {
		notNull(id, "O compra id não pode ser nulo");
		
		return compraRepository.obter(id);
	}
	
	public void realizarCompra(CompraId compraId) {
	    notNull(compraId, "O ID da compra não pode ser nulo");

	    Compra compra = obter(compraId);
	    Carrinho carrinho = carrinhoService.obter(compra.getCarrinhoId());

	    boolean pagamentoSucesso = compra.processarPagmento();
	    if (!pagamentoSucesso) {
	        throw new IllegalStateException("Falha no processamento do pagamento");
	    }

	    for (Item item : carrinho.getItens()) {
	        Produto produto = produtoService.obter(item.getProduto());

	        produto.diminuirQuantidade(item.getQuantidade());
	        produtoService.salvar(produto);
	    }

	    compraRepository.salvar(compra);
	}
}
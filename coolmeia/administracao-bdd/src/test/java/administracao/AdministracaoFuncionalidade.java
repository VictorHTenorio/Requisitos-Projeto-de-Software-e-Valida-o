package administracao;

import administracao.cliente.ClienteService;
import loja.carrinho.CarrinhoService;
import loja.compra.CompraService;
import loja.cupom.CupomService;
import loja.produto.ProdutoService;
import memoria.Repository;

public class AdministracaoFuncionalidade {
	protected Repository repository = new Repository();
	protected ProdutoService produtoService = new ProdutoService(repository);
	protected CupomService cupomService = new CupomService(repository);
	protected CarrinhoService carrinhoService = new CarrinhoService(repository, cupomService, produtoService);
	protected CompraService compraService = new CompraService(repository, carrinhoService, produtoService);
	protected ClienteService clienteService = new ClienteService(repository, carrinhoService, produtoService);
}

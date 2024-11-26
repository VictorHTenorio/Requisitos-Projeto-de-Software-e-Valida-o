package administracao.cliente;


import loja.carrinho.Carrinho;
import loja.carrinho.CarrinhoId;
import loja.carrinho.CarrinhoService;
import loja.carrinho.Item;
import loja.produto.Produto;
import loja.produto.ProdutoService;
import static org.apache.commons.lang3.Validate.notNull;

import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	private final ClienteRepository clienteRepository;
	private final CarrinhoService carrinhoService;
	private final ProdutoService produtoService;
	
	public ClienteService(ClienteRepository clienteRepository, CarrinhoService carrinhoService, ProdutoService produtoService) {
		notNull(clienteRepository, "O repositório naõ pode ser nulo");
		notNull(carrinhoService, "O service do carrinho não pode ser nulo");
		notNull(produtoService, "O service do produto não pode ser nulo");
		
		this.clienteRepository = clienteRepository;
		this.carrinhoService = carrinhoService;
		this.produtoService = produtoService;
	}
	
	public Cliente salvar(Cliente cliente) {
		notNull(cliente, "O cliente não pode ser nulo");
		
		return clienteRepository.salvar(cliente);
	}
	
	public Cliente obter(ClienteId id) {
		notNull(id, "O cliente id não pode ser nulo");
		
		return clienteRepository.obter(id);
	}
	
	public boolean excluir(ClienteId id) {
		notNull(id, "O cliente id não pode ser nulo");
		
		return clienteRepository.excluir(id);
	}
	
	public void limparCarrinho(ClienteId clienteId) {
        notNull(clienteId, "O cliente id não pode ser nulo");

        Cliente cliente = clienteRepository.obter(clienteId);
        notNull(cliente, "Cliente não encontrado");

        CarrinhoId novoCarrinhoId = carrinhoService.criarNovoCarrinho();
        cliente.setCarrinhoId(novoCarrinhoId);

        clienteRepository.salvar(cliente);
    }
	
	public boolean autenticar(String cpf, String senha) {
        notNull(cpf, "O CPF não pode ser nulo");
        notNull(senha, "A senha não pode ser nula");

        // Chama o método no repositório para buscar a senha
        String senhaArmazenada = clienteRepository.findSenhaByCpf(cpf);
        return senhaArmazenada != null && senhaArmazenada.equals(senha);
    }
	
	public void notificarCliente() {
		System.out.print("Email enviado!");
	}
	
	public boolean verificarNotificarPoucosProdutosCarrinho(ClienteId clienteId, CarrinhoId carrinhoId, int poucaQuantidade) {
		notNull(clienteId ,"O id do cliente não pode ser nulo");
		notNull(carrinhoId ,"O id do carrinho não pode ser nulo");
		
		Cliente cliente = obter(clienteId);
		ListaDeDesejos listaDeDesejos = cliente.getListaDeDesejos();
		Carrinho carrinho = carrinhoService.obter(carrinhoId);
		
		for(Item item: carrinho.getItens()) {
			if(!listaDeDesejos.verificarProdutoInLista(item.getProduto())) {
				continue;
			}
			Produto produto = produtoService.obter(item.getProduto());
			if(produto.verificarPoucaQuantidade(poucaQuantidade)) {
				notificarCliente();
				return true;
			}
		}
		return false;
	}
	
}

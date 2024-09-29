package administracao.cliente;

import org.jmolecules.ddd.annotation.Service;

import loja.carrinho.CarrinhoId;
import loja.carrinho.CarrinhoService;

import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ClienteService {
	private final ClienteRepository clienteRepository;
	private final CarrinhoService carrinhoService;
	
	public ClienteService(ClienteRepository clienteRepository, CarrinhoService carrinhoService) {
		notNull(clienteRepository, "O repositório naõ pode ser nulo");
		notNull(carrinhoService, "O service do carrinho não pode ser nulo");
		
		this.clienteRepository = clienteRepository;
		this.carrinhoService = carrinhoService;
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
}

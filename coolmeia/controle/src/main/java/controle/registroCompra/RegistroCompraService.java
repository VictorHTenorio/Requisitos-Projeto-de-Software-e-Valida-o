package controle.registroCompra;


import administracao.cliente.ClienteId;
import administracao.cliente.ClienteService;
import loja.compra.CompraId;
import loja.compra.CompraService;
import static org.apache.commons.lang3.Validate.notNull;
import org.springframework.stereotype.Service;

@Service
public class RegistroCompraService {
	private final RegistroCompraRepository registroCompraRepository;
	private final CompraService compraService;
	private final ClienteService clienteService;
	
	public RegistroCompraService(RegistroCompraRepository registroCompraRepository, CompraService compraService, ClienteService clienteService) {
		notNull(registroCompraRepository, "O repositório não pode ser nulo");
		notNull(compraService, "O serviço da compra não pode ser nulo");
		notNull(clienteService, "O serviço do cliente não pode ser nulo");
		
		this.registroCompraRepository = registroCompraRepository;
		this.compraService = compraService;
		this.clienteService = clienteService;
	}
	
	public RegistroCompra salvar(RegistroCompra registroCompra) {
		notNull(registroCompra, "O registro da compra não pode ser nulo");
		
		return registroCompraRepository.salvar(registroCompra);
	}
	
	public RegistroCompra obter(RegistroCompraId id) {
		notNull(id, "O registro da compra id não pode ser nulo");
		
		return registroCompraRepository.obter(id);
	}
	
	public void RegistrarCompra(CompraId compraId, ClienteId clienteId) {
		notNull(compraId ,"O id da compra não pode ser nulo");
		notNull(clienteId ,"O id do cliente não pode ser nulo");
		RegistroCompra novoRegistro = new RegistroCompra(compraId, clienteId);
		
		compraService.realizarCompra(compraId);
		clienteService.limparCarrinho(clienteId);
		
		salvar(novoRegistro);
	}
}

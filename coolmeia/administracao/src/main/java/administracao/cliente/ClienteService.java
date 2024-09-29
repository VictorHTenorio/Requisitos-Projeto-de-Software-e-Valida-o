package administracao.cliente;

import org.jmolecules.ddd.annotation.Service;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ClienteService {
	private final ClienteRepository clienteRepository;
	
	public ClienteService(ClienteRepository clienteRepository) {
		notNull(clienteRepository, "O repositório naõ pode ser nulo");
		
		this.clienteRepository = clienteRepository;
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
}

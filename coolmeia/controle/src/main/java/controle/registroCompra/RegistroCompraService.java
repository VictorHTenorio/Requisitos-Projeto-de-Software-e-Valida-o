package controle.registroCompra;

import org.jmolecules.ddd.annotation.Service;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class RegistroCompraService {
	private final RegistroCompraRepository registroCompraRepository;
	
	public RegistroCompraService(RegistroCompraRepository registroCompraRepository) {
		notNull(registroCompraRepository, "O repositório não pode ser nulo");
		
		this.registroCompraRepository = registroCompraRepository;
	}
	
	public RegistroCompra salvar(RegistroCompra registroCompra) {
		notNull(registroCompra, "O registro da compra não pode ser nulo");
		
		return registroCompraRepository.salvar(registroCompra);
	}
	
	public RegistroCompra obter(RegistroCompraId id) {
		notNull(id, "O registro da compra id não pode ser nulo");
		
		return registroCompraRepository.obter(id);
	}
}

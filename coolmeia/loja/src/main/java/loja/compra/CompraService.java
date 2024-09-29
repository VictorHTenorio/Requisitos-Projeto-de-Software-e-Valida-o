package loja.compra;

import static org.apache.commons.lang3.Validate.notNull;
import org.jmolecules.ddd.annotation.Service;

@Service
public class CompraService {
	private final CompraRepository compraRepository;
	
	public CompraService(CompraRepository compraRepository) {
		notNull(compraRepository, "O repositório não pode ser nulo");
		
		this.compraRepository = compraRepository;
	}
	
	public Compra salvar(Compra compra) {
		notNull(compra, "A compra não pode ser nula");
		
		return compraRepository.salvar(compra);
	}
	
	public Compra obter(CompraId id) {
		notNull(id, "O compra id não pode ser nulo");
		
		return compraRepository.obter(id);
	}
}
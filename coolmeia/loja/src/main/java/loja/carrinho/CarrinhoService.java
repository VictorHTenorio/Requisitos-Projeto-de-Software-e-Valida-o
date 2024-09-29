package loja.carrinho;

import org.jmolecules.ddd.annotation.Service;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class CarrinhoService {
	private final CarrinhoRepository carrinhoRepository;
	
	public CarrinhoService(CarrinhoRepository carrinhoRepository) {
		notNull(carrinhoRepository, "O repositório não pode ser nulo");
		
		this.carrinhoRepository = carrinhoRepository;
	}
	
	public Carrinho salvar(Carrinho carrinho) {
		notNull(carrinho, "O carrinho não pode ser nulo");
		
		return carrinhoRepository.salvar(carrinho);
	}
	
	public Carrinho obter(CarrinhoId id) {
		notNull(id, "O carrinho id não pode ser nulo");
		
		return carrinhoRepository.obter(id);
	}
}

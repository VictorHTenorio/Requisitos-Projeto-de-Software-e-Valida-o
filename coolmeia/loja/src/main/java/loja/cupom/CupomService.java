package loja.cupom;

import static org.apache.commons.lang3.Validate.notNull;
import org.springframework.stereotype.Service;

@Service
public class CupomService {
	private final CupomRepository cupomRepository;
	
	public CupomService(CupomRepository cupomRepository) {
		notNull(cupomRepository, "O repositório não pode ser nulo");
		
		this.cupomRepository = cupomRepository;
	}
	
	public Cupom salvar(Cupom cupom) {
		notNull(cupom, "O cupom não pode ser nulo");
		
		return cupomRepository.salvar(cupom);
	}
	
	public Cupom obter(CupomCodigo codigo) {
		notNull(codigo, "O cupom codigo não pode ser nulo");
		
		return cupomRepository.obter(codigo);
	}
	
	public boolean excluir(CupomCodigo codigo) {
		notNull(codigo, "O cupom codigo não pode ser nulo");
		
		return cupomRepository.excluir(codigo);
	}
}
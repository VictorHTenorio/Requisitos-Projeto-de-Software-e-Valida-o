package loja.categoria;

import org.jmolecules.ddd.annotation.Service;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class CategoriaService {
	private final CategoriaRepository categoriaRepository;
	
	public CategoriaService(CategoriaRepository categoriaRepository) {
		notNull(categoriaRepository, "O repositório não pode ser nulo");
		
		this.categoriaRepository = categoriaRepository;
	}
	
	public Categoria salvar(Categoria categoria) {
		notNull(categoria, "A categoria não pode ser nula");
		
		return categoriaRepository.salvar(categoria);
	}
	
	public Categoria obter(CategoriaId id) {
		notNull(id, "A categoria id não pode ser nula");
		
		return categoriaRepository.obter(id);
	}
	
	public boolean excluir(CategoriaId id) {
		notNull(id, "A categoria id não pode ser nula");
		
		return categoriaRepository.excluir(id);
	}
}

package loja.categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {
	Categoria salvar(Categoria produto);
	
	Optional<Categoria> obter(CategoriaId id);
	
	List<Categoria> obterTodas();
	
	boolean excluir(CategoriaId id);
}

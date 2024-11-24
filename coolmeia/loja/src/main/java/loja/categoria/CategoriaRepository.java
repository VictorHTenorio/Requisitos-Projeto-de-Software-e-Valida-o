package loja.categoria;

import java.util.Optional;

public interface CategoriaRepository {
	Categoria salvar(Categoria produto);
	
	Optional<Categoria> obter(CategoriaId id);
	
	boolean excluir(CategoriaId id);
}

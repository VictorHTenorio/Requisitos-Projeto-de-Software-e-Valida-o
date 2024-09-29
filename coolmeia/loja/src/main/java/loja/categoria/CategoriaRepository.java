package loja.categoria;

public interface CategoriaRepository {
	Categoria salvar(Categoria produto);
	
	Categoria obter(CategoriaId id);
	
	boolean excluir(CategoriaId id);
}

package loja.categoria;

public interface CategoriaRepository {
void salvar(Categoria produto);
	
	Categoria obter(CategoriaId id);
	
	void excluir(CategoriaId id);
}

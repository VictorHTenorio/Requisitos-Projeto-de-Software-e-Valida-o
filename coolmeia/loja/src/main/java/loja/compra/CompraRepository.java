package loja.compra;

public interface CompraRepository {
	Compra salvar(Compra compra);
	
	Compra obter(CompraId id);
}

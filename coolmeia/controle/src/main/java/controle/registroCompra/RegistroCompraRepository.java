package controle.registroCompra;

public interface RegistroCompraRepository {
	RegistroCompra salvar(RegistroCompra registroCompra);
	
	RegistroCompra obter(RegistroCompraId id);
}

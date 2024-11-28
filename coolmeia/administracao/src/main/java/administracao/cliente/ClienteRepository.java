package administracao.cliente;

public interface ClienteRepository {
	Cliente salvar(Cliente cliente);
	
	Cliente obter(ClienteId id);
	
	boolean excluir(ClienteId id);
	
	String findSenhaByCpf(String cpf);
}

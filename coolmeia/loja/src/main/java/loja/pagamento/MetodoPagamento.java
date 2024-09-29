package loja.pagamento;

public interface MetodoPagamento {
	public boolean processarPagamento();
	
	public String getMetodo();
}

package loja.cupom;

public interface CupomRepository {
	Cupom salvar(Cupom cupom);
	
	Cupom obter(CupomCodigo codigo);
	
	boolean excluir(CupomCodigo codigo);
}

package loja.produto;

import static org.apache.commons.lang3.Validate.notNull;
import org.jmolecules.ddd.annotation.Service;

@Service
public class ProdutoService {
	private final ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository) {
		notNull(produtoRepository, "O repositório não pode ser nulo");
		
		this.produtoRepository = produtoRepository;
	}
	
	public Produto salvar(Produto produto) {
		notNull(produto, "O produto não pode ser nulO");
		
		return produtoRepository.salvar(produto);
	}
	
	public Produto obter(ProdutoId id) {
		notNull(id, "O produto id não pode ser nulo");
		
		return produtoRepository.obter(id);
	}
	
	public boolean excluir(ProdutoId id) {
		notNull(id, "A categoria id não pode ser nula");
		
		return produtoRepository.excluir(id);
	}
}
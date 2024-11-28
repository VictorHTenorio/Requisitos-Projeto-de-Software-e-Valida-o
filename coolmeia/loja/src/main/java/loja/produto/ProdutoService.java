package loja.produto;

import static org.apache.commons.lang3.Validate.notNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import loja.categoria.CategoriaId;

@Service
public class ProdutoService {
	private final ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository) {
		notNull(produtoRepository, "O repositório não pode ser nulo");
		
		this.produtoRepository = produtoRepository;
	}
	
	public Produto salvar(Produto produto) {
        notNull(produto, "O produto não pode ser nulo");
        
        Produto produtoSalvo = produtoRepository.salvar(produto);
        
        if(produtoSalvo.getDataAdicao().isAfter(LocalDate.now().minusDays(30))) {
            ListaNovidades.getInstance().adicionarProduto(produtoSalvo.getId());
            salvar(ListaNovidades.getInstance());
        }
        
        return produtoSalvo;
    }
	
	public Produto obter(ProdutoId id) {
		notNull(id, "O produto id não pode ser nulo");
		
		return produtoRepository.obter(id);
	}
	
	public boolean excluir(ProdutoId id) {
		notNull(id, "A categoria id não pode ser nula");
		
		return produtoRepository.excluir(id);
	}
	
	 public List<Produto> obterProdutosPorCategoria(CategoriaId categoria) {
		 
	        notNull(categoria, "A categoria não pode ser nula");
	        return produtoRepository.obterPorCategoria(categoria);
	 }
	 
	 public ListaNovidades salvar(ListaNovidades listaNovidades) {
		 notNull(listaNovidades, "A lista de novidades não pode ser nula");
		 return produtoRepository.salvar(listaNovidades);
	 }
	 
	 public ListaNovidades obter() {
		 return produtoRepository.obter();
	 }
	 
	 public ListaNovidades verificarListaNovidades(ListaNovidades listaNovidades) {
		 List<ProdutoId> novaLista = new ArrayList<ProdutoId>();
		 
		 for(ProdutoId produtoId : listaNovidades.getProdutos()) {
			 Produto produto = obter(produtoId);
			 
			 if(produto.getDataAdicao().isAfter(LocalDate.now().minusDays(30))) {
				 novaLista.add(produtoId);
			 }
		 }
		 
		 listaNovidades.setProdutos(novaLista);
		 return salvar(listaNovidades);
	 }
	 
	 public List<Produto> obterTodos() {
		    return produtoRepository.obterTodos();
		}
}
package loja.cupom;

import java.util.ArrayList;
import java.util.List;
import org.jmolecules.ddd.types.AggregateRoot;
import loja.categoria.CategoriaId;
import loja.produto.ProdutoId;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Cupom implements Cloneable, AggregateRoot<Cupom, CupomCodigo> {
    private final CupomCodigo id;
    private Periodo periodoValidade;
    private int porcentagemDesconto;
    private List<CategoriaId> categorias; //Categorias aplicáveis
    private List<ProdutoId> produtos; //Produtos aplicáveis

    public Cupom(Periodo periodoValidade, int porcentagemDesconto, List<CategoriaId> categorias, List<ProdutoId> produtos) {
        this.id = null;
        setPeriodoValidade(periodoValidade);
        setPorcentagemDesconto(porcentagemDesconto);
        setCategorias(categorias);
        setProdutos(produtos);
    }

    public Cupom(CupomCodigo id, Periodo periodoValidade, int porcentagemDesconto, List<CategoriaId> categorias, List<ProdutoId> produtos) {
        notNull(id, "O código do cupom não pode ser nulo");
        this.id = id;
        setPeriodoValidade(periodoValidade);
        setPorcentagemDesconto(porcentagemDesconto);
        setCategorias(categorias);
        setProdutos(produtos);
    }

    @Override
    public CupomCodigo getId() {
        return id;
    }

    public Periodo getPeriodoValidade() {
        return periodoValidade;
    }

    public int getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public List<CategoriaId> getCategorias() {
        return new ArrayList<>(categorias);
    }

    public List<ProdutoId> getProdutos() {
        return new ArrayList<>(produtos);
    }

    private void setPeriodoValidade(Periodo periodoValidade) {
        notNull(periodoValidade, "O período de validade não pode ser nulo");
        this.periodoValidade = periodoValidade;
    }

    private void setPorcentagemDesconto(int porcentagemDesconto) {
        isTrue(porcentagemDesconto > 0 && porcentagemDesconto <= 100, "O desconto deve estar entre 1% e 100%");
        this.porcentagemDesconto = porcentagemDesconto;
    }

    private void setCategorias(List<CategoriaId> categorias) {
        this.categorias = categorias != null ? new ArrayList<>(categorias) : new ArrayList<>();
    }

    private void setProdutos(List<ProdutoId> produtos) {
        this.produtos = produtos != null ? new ArrayList<>(produtos) : new ArrayList<>();
    }

    public boolean isValido() {
        return periodoValidade.isValido(java.time.LocalDate.now());
    }
    
    // Se não tiver específico aplica a todas
    public boolean isAplicavelACategoria(CategoriaId categoriaId) {
        notNull(categoriaId, "A categoria não pode ser nula");
        return categorias.isEmpty() || categorias.contains(categoriaId);
    }
    
    // Se não tiver específico aplica a todos
    public boolean isAplicavelAProduto(ProdutoId produtoId) {
        notNull(produtoId, "O produto não pode ser nulo");
        return produtos.isEmpty() || produtos.contains(produtoId);
    }

    @Override
    public Cupom clone() {
        try {
            Cupom clone = (Cupom) super.clone();
            clone.categorias = new ArrayList<>(this.categorias);
            clone.produtos = new ArrayList<>(this.produtos);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public String toString() {
        return "Cupom{" +
                "id=" + id +
                ", periodoValidade=" + periodoValidade +
                ", porcentagemDesconto=" + porcentagemDesconto +
                ", categorias=" + categorias +
                ", produtos=" + produtos +
                '}';
    }
}

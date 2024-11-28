package jpa.loja.cupom;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CupomCodigoJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "codigo")
    private String codigo;
    
    protected CupomCodigoJpaEntity() {}
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
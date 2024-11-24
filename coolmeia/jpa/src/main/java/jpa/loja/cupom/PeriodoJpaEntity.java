package jpa.loja.cupom;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class PeriodoJpaEntity {
    @Column(name = "data_inicio", nullable = false)
    private LocalDate inicio;
    
    @Column(name = "data_fim", nullable = false)
    private LocalDate fim;
    
    protected PeriodoJpaEntity() {}
    
    public LocalDate getInicio() { return inicio; }
    public void setInicio(LocalDate inicio) { this.inicio = inicio; }
    
    public LocalDate getFim() { return fim; }
    public void setFim(LocalDate fim) { this.fim = fim; }
}
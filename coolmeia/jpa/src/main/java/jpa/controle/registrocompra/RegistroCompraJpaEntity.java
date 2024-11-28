package jpa.controle.registrocompra;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_compra")
public class RegistroCompraJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "data_hora_realizacao", nullable = false)
    private LocalDateTime dataHoraRealizacao;
    
    @Column(name = "compra_id", nullable = false)
    private Integer compraId;
    
    @Column(name = "cliente_cpf", nullable = false)
    private String clienteCpf;
    
    protected RegistroCompraJpaEntity() {}
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public LocalDateTime getDataHoraRealizacao() { return dataHoraRealizacao; }
    public void setDataHoraRealizacao(LocalDateTime dataHoraRealizacao) { this.dataHoraRealizacao = dataHoraRealizacao; }
    
    public Integer getCompraId() { return compraId; }
    public void setCompraId(Integer compraId) { this.compraId = compraId; }
    
    public String getClienteCpf() { return clienteCpf; }
    public void setClienteCpf(String clienteCpf) { this.clienteCpf = clienteCpf; }
}
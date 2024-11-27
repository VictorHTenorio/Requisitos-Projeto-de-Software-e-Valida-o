package jpa.controle.registrocompra;

import org.springframework.stereotype.Component;
import controle.registroCompra.RegistroCompra;
import controle.registroCompra.RegistroCompraId;
import administracao.cliente.ClienteId;
import loja.compra.CompraId;

@Component
public class RegistroCompraMapper {
    
    public RegistroCompraJpaEntity toJpaEntity(RegistroCompra registroCompra) {
        if (registroCompra == null) return null;
        
        RegistroCompraJpaEntity jpaEntity = new RegistroCompraJpaEntity();
        
        if (registroCompra.getId() != null) {
            jpaEntity.setId(registroCompra.getId().getId());
        }
        
        jpaEntity.setDataHoraRealizacao(registroCompra.getDataHoraRealizacao());
        jpaEntity.setCompraId(registroCompra.getCompra().getId());
        jpaEntity.setClienteCpf(registroCompra.getCliente().getCpf());
        
        return jpaEntity;
    }
    
    public RegistroCompra toDomainEntity(RegistroCompraJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return new RegistroCompra(
            new RegistroCompraId(jpaEntity.getId()),
            jpaEntity.getDataHoraRealizacao(),
            new CompraId(jpaEntity.getCompraId()),
            new ClienteId(jpaEntity.getClienteCpf())
        );
    }
}
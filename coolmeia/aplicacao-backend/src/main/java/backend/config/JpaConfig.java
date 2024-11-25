package backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
    "jpa",
    "jpa.loja.produto",
    "jpa.loja.categoria",
    "jpa.loja.carrinho",
    "jpa.loja.compra",
    "jpa.loja.cupom",
    "jpa.administracao.cliente"
})
public class JpaConfig {
    // Configuração é feita através das anotações
}
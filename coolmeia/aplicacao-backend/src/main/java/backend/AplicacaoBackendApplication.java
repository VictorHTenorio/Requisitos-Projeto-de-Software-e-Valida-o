package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
	    "jpa",               // pacote base das entidades JPA
	    "jpa.loja.produto",  // pacote específico do produto
	    "jpa.loja.categoria",
	    "jpa.controle.registrocompra",
	    "jpa.loja.carrinho",
	    "jpa.loja.compra",
	    "jpa.loja.cupom",
	    "jpa.administracao.cliente"
})
@ComponentScan(basePackages = {
    "backend",    // para encontrar os controllers
    "administracao",
    "controle",
    "loja",         // para encontrar os services
    "jpa"           // para encontrar os repositories
})
public class AplicacaoBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AplicacaoBackendApplication.class, args);
    }
}

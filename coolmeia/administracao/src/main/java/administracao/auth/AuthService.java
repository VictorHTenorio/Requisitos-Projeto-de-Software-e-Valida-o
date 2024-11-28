package administracao.auth;

import administracao.cliente.ClienteRepository;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.Validate.notBlank;

@Service
public class AuthService {
    private final ClienteRepository clienteRepository;

    public AuthService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean autenticar(String cpf, String senha) {
        notBlank(cpf, "O CPF não pode ser em branco");
        notBlank(senha, "A senha não pode ser em branco");

        // Usa o método customizado para buscar a senha diretamente
        String senhaArmazenada = clienteRepository.findSenhaByCpf(cpf);
        return senhaArmazenada != null && senhaArmazenada.equals(senha);
    }
}

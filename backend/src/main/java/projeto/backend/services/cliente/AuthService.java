package projeto.backend.services.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projeto.backend.config.JwtUtil;
import projeto.backend.model.cliente.Usuario;
import projeto.backend.repository.cliente.UsuarioRepository;


@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String login, String senha){
         Usuario usuario = usuarioRepository.findByLogin(login)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(!passwordEncoder.matches(senha, usuario.getSenha())){
            throw new RuntimeException("Senha incorreta");
        }

        return JwtUtil.gerarToken(usuario);
    }
}

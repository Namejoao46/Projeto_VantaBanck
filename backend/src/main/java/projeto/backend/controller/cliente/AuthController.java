package projeto.backend.controller.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.backend.config.JwtUtil;
import projeto.backend.dto.cliente.LoginDTO;
import projeto.backend.dto.cliente.LoginResponseDTO;
import projeto.backend.model.cliente.Usuario;
import projeto.backend.repository.cliente.UsuarioRepository;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @SuppressWarnings("ConstantConditions")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO dto){
        Usuario usuario = usuarioRepository.findByLogin(dto.getLogin()).orElse(null);

        if (usuario == null){
            return ResponseEntity.status(401).build();
        }

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(401).build();
        }

        String token = JwtUtil.generateToken(usuario.getLogin(), usuario.getTipo());
        LoginResponseDTO response = new LoginResponseDTO(usuario.getLogin(), usuario.getTipo(), token);
        
        return ResponseEntity.ok(response);
    }


    /*---------------< TESTE >--------------- */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario novoUsuario) {
        // Verifica se login já existe
        if (usuarioRepository.findByLogin(novoUsuario.getLogin()).isPresent()) {
            return ResponseEntity.status(400).body("Login já está em uso");
        }

        // Criptografa a senha
        novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));

        // Salva no banco
        usuarioRepository.save(novoUsuario);

        return ResponseEntity.status(201).body("Usuário cadastrado com sucesso");
    }

}

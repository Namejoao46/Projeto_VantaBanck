package projeto.backend.controller.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO dto){
        Usuario usuario = usuarioRepository.findByLogin(dto.getLogin());

        if (usuario == null || !usuario.getSenha().equals(dto.getSenha())) {
            return ResponseEntity.status(401).build();
        }

        String token = JwtUtil.generateToken(usuario.getLogin(), usuario.getTipo());
        LoginResponseDTO response = new LoginResponseDTO(usuario.getLogin(), usuario.getTipo(), token);
        
        return ResponseEntity.ok(response);
    }

}

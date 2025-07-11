package projeto.backend.repository.cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.backend.model.cliente.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
}

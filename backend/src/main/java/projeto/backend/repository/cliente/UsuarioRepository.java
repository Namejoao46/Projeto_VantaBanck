package projeto.backend.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.backend.model.cliente.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);
}

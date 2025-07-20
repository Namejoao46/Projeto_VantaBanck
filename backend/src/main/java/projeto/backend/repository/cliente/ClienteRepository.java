package projeto.backend.repository.cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.backend.model.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    Optional<Cliente> findByUsuarioLogin(String login);
    boolean existsByCpf(String cpf);
}

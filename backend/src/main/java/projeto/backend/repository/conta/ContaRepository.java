package projeto.backend.repository.conta;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.backend.model.conta.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{
    Conta findByCliente_Usuario_Login(String login);
}

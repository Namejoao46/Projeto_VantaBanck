package projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.backend.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}

package projeto.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.backend.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

}

package projeto.backend.repository.conta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.backend.model.conta.Cofrinho;

public interface CofrinhoRepository extends JpaRepository<Cofrinho, Long> {
    List<Cofrinho> findByDonoId(Long donoId);
}

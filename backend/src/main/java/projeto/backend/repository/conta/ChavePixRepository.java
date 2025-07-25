package projeto.backend.repository.conta;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.backend.model.conta.ChavePix;

public interface ChavePixRepository extends JpaRepository<ChavePix, Long> {
    Optional<ChavePix> findByClienteId(Long clienteId); // Limita a 1 chave por cliente
    Optional<ChavePix> findByValor(String valor);       // Busca destinatário pelo valor
}

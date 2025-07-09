package projeto.backend.repository.conta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.backend.model.conta.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

    List<Transacao> findByContaIdOrderByDataHoraDesc(Long contaId);
}

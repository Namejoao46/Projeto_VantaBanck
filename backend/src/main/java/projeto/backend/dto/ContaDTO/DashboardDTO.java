package projeto.backend.dto.ContaDTO;

import java.util.List;

public record DashboardDTO(Double saldo, Double credito, List<TransacaoDTO> ultimasTransacoes) {

}

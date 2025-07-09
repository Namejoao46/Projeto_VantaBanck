package projeto.backend.dto.ContaDTO;

import java.time.LocalDateTime;

// DTO (Data Transfer Object) usado para receber o valor da transação no corpo da requisição
public record TransacaoDTO(String tipo, Double valor, LocalDateTime dataHora) {

}

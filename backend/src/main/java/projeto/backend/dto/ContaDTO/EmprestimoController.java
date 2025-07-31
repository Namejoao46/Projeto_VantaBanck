package projeto.backend.dto.ContaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import projeto.backend.services.conta.EmprestimoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("/simular")
    public ResponseEntity<SimulacaoResultadoDTO> simularEmprestimo(
        @RequestBody SimulacaoRequestDTO dto,
        Authentication auth) {

        String login = auth.getName();
        SimulacaoResultadoDTO resultado = emprestimoService.simular(login, dto.getValorSolicitado(), dto.getMeses());
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/aceitar")
    public ResponseEntity<Void> aceitarEmprestimo(
        @RequestBody SimulacaoRequestDTO dto,
        Authentication auth) {

        String login = auth.getName();
        emprestimoService.aceitarEmprestimo(login, dto.getValorSolicitado(), dto.getMeses());
        return ResponseEntity.ok().build();
    }
}

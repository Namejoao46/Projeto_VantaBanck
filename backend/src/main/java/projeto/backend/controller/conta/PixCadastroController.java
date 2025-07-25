package projeto.backend.controller.conta;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.backend.dto.ContaDTO.ChavePixDTO;
import projeto.backend.services.conta.ContaService;

@RestController
@RequestMapping("/pix/chaves")
public class PixCadastroController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrar(@RequestBody ChavePixDTO dto, Principal principal){
        String login = principal.getName();
        contaService.cadastrarChavePix(
        dto, login);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/minha")
    public ResponseEntity<ChavePixDTO> minhaChave(Principal principal) {
        String login = principal.getName();
        return contaService.consultarMinhaChavePix(login)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.noContent().build());
    }
}

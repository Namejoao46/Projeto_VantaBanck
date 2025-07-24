package projeto.backend.controller.conta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projeto.backend.dto.ContaDTO.PixDTO;
import projeto.backend.services.conta.ContaService;

import java.security.Principal;

@RestController
@RequestMapping("/api/pix")
public class PixController {
    @Autowired
    private ContaService contaService;

    @PostMapping("/enviar")
    public ResponseEntity<Void> realizarPix(@RequestBody PixDTO dto, Principal principal) {
        String login = principal.getName();
        contaService.realizarPix(dto, login);
        return ResponseEntity.ok().build();
    }
}
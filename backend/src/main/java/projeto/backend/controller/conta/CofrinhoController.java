package projeto.backend.controller.conta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.backend.dto.ContaDTO.CofrinhoDTO;
import projeto.backend.model.cliente.Cliente;
import projeto.backend.model.conta.Cofrinho;
import projeto.backend.repository.cliente.ClienteRepository;
import projeto.backend.services.conta.ContaService;

@RestController
@RequestMapping("/api/cofrinhos")
public class CofrinhoController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<List<Cofrinho>> criarEListar(@RequestBody CofrinhoDTO dto, Authentication auth) {
        Cliente cliente = clienteRepository.findByUsuarioLogin(auth.getName())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        contaService.criarCofrinho(dto, cliente);

        List<Cofrinho> cofrinhos = contaService.listarCofrinhosDoUsuario(auth.getName());
        return ResponseEntity.ok(cofrinhos);
    }

    @GetMapping
    public ResponseEntity<List<Cofrinho>> listar(Authentication auth) {
        List<Cofrinho> cofrinhos = contaService.listarCofrinhosDoUsuario(auth.getName());
        return ResponseEntity.ok(cofrinhos);
    }

}
package projeto.backend.controller.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.backend.dto.cliente.ClienteCadastroDTO;
import projeto.backend.services.cliente.ClienteService;
import projeto.backend.services.conta.ContaService;

import org.springframework.web.bind.annotation.GetMapping;

import projeto.backend.dto.ContaDTO.TransacaoDTO;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Endpoint para cadastrar um novo cliente
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody ClienteCadastroDTO dto){
        clienteService.cadastrarCliente(dto);
        return ResponseEntity.ok("Cliente cadastrado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<String> dadosCliente(Authentication authentication){
        String login = authentication.getName(); // login extraído do token JWT
        return ResponseEntity.ok("✅ Acesso autorizado para o cliente: " + login);
    }

    @Autowired
    private ContaService contaService;

    // Endpoint para depósito
    @PostMapping("/depositar")
    public ResponseEntity<String> depositar(@RequestBody TransacaoDTO dto, Authentication auth) {

        // Usa o login do usuário autenticado (extraído do token JWT)
        contaService.depositar(auth.getName(), dto.valor());
        
        // Retorna resposta de sucesso
        return ResponseEntity.ok("Depósito realizado com sucesso!");
    }

    @PostMapping("/sacar")
    public ResponseEntity<String> sacar(@RequestBody TransacaoDTO dto, Authentication auth) {

        // Usa o login do usuário autenticado (extraído do token JWT)
        contaService.sacar(auth.getName(), dto.valor());
        
        // Retorna resposta de sucesso
        return ResponseEntity.ok("Saque realizado com sucesso!");
    }

    @GetMapping("/transacoes")
    public ResponseEntity<List<TransacaoDTO>> listarTransacaResponse(Authentication auth) {
        List<TransacaoDTO> transacoes = contaService.listaTransacoes(auth.getName());
        return ResponseEntity.ok(transacoes);
    }
    
    
    
}

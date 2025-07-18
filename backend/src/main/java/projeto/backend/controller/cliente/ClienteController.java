package projeto.backend.controller.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.backend.dto.ContaDTO.DashboardDTO;
import projeto.backend.dto.ContaDTO.FiltroTransacaoDTO;
import projeto.backend.dto.ContaDTO.OperacaoComSenhaDTO;
import projeto.backend.dto.ContaDTO.TransacaoDTO;
import projeto.backend.dto.ContaDTO.TransferenciaComSenhaDTO;
import projeto.backend.dto.cliente.ClienteCadastroDTO;
import projeto.backend.services.cliente.ClienteService;
import projeto.backend.services.conta.ContaService;

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
    
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> dashboard(Authentication auth) {
        DashboardDTO dashboard = contaService.getDashboard(auth.getName());
        return ResponseEntity.ok(dashboard);
    }

    @PostMapping("/transacoes/filtro")
    public ResponseEntity<List<TransacaoDTO>> filtraTransaco(@RequestBody FiltroTransacaoDTO filtro, Authentication auth) {
        List<TransacaoDTO> transacoes = contaService.filtrarTransacoesPorData(auth.getName(), filtro);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/transacoes/exportar")
    public ResponseEntity<byte[]> exportarTransacoesCSV(Authentication auth) {
        byte[] csv = contaService.gerarCSVTransacoes(auth.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transacoes.csv");
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        headers.setContentLength(csv.length);

        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }

    @PostMapping("/saque")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Void> sacar(@RequestBody OperacaoComSenhaDTO dto, Authentication auth){
        contaService.sacarComSenha(auth.getName(), dto.valor(), dto.senha());
        
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Void> transferir(@RequestBody TransferenciaComSenhaDTO dto, Authentication auth) {
        contaService.transferirComSenha(auth.getName(), dto.destinoLogin(), dto.valor(), dto.senha());
        
        return ResponseEntity.ok().build();
    }
    
}

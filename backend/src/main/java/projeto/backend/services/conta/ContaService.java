package projeto.backend.services.conta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.backend.dto.ContaDTO.TransacaoDTO;
import projeto.backend.model.cliente.Cliente;
import projeto.backend.model.conta.Conta;
import projeto.backend.model.conta.Transacao;
import projeto.backend.repository.cliente.ClienteRepository;
import projeto.backend.repository.conta.TransacaoRepository;

@Service
public class ContaService {
    public static final double SALARIO_MINIMO = 1530.00;

    public Conta criarContaParaCliente(Cliente cliente){
        Conta conta = new Conta();
        conta.setCliente(cliente);

        conta.setSaldo(cliente.getSalario());

        double salario = cliente.getSalario();
        double credito;

        if(salario < 2 * SALARIO_MINIMO){
            credito = 0.75 * SALARIO_MINIMO;
        }else if(salario <= 3 * SALARIO_MINIMO){
            credito = 5000.0;
        }else{
            double excedente = salario - (3 * SALARIO_MINIMO);
            int blocos = (int) (excedente / SALARIO_MINIMO);
            credito = 5000.0;

            for(int i = 0; i < blocos; i++){
                credito += credito * 0.6;
            }
        }
        conta.setCredito(credito);
        return conta;
    }

    @Autowired  // Injeta automaticamente o repositório de clientes
    private ClienteRepository clienteRepository;
    
    @Autowired
    private TransacaoRepository transacaoRepository;

    // Método para realizar depósito
    public void depositar(String login, Double valor){
        // Busca o cliente pelo login (extraído do token JWT)
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Conta conta = cliente.getConta();
        conta.depositar(valor);
        // Salva o cliente com o novo saldo atualizado
        clienteRepository.save(cliente);

        registrarTransacao(conta, valor, "DEPOSITO");
    }

    public boolean ultrapassouLimiteDiario(Conta conta, double valor){
        LocalDateTime hoje = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Transacao> transacoesHoje = transacaoRepository.findByContaIdOrderByDataHoraDesc(conta.getId())
            .stream()
            .filter(t -> t.getDataHora(). isAfter(hoje) && t.getTipo().equals("SAQUE"))
            .toList();

        double totalSacadoHoje = transacoesHoje.stream().mapToDouble(Transacao::getValor).sum();
        return (totalSacadoHoje + valor) > 2000.0; // Exemplo: limite diário de R$2000
    }

    // Método para realizar saque
    public void sacar(String login, Double valor){

        // Busca o cliente pelo login
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Conta conta = cliente.getConta();
        conta.sacar(valor);
        // Salva o cliente com o saldo atualizado
        clienteRepository.save(cliente);

        registrarTransacao(conta, valor, "SAQUE");
    }
    

    private void registrarTransacao(Conta conta, Double valor, String tipo){
        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setValor(valor);
        transacao.setTipo(tipo);
        transacao.setDataHora(LocalDateTime.now());
        transacaoRepository.save(transacao);

    }

    public void transferir(String origemLogin, String destinoLogin, Double valor){
        Cliente origem = clienteRepository.findByUsuarioLogin(origemLogin)
            .orElseThrow(() -> new RuntimeException("Cliente origem não encontrado"));
        Cliente destino = clienteRepository.findByUsuarioLogin(destinoLogin)
            .orElseThrow(() -> new RuntimeException("Cliente destino não encontrado"));

        Conta contaOrigem = origem.getConta();
        Conta contaDestino = destino.getConta();

        if (ultrapassouLimiteDiario(contaOrigem, valor)){
            throw new RuntimeException("Limite diario de transferencia excedido");
        }

        contaOrigem.sacar(valor);
        contaDestino.depositar(valor);

        clienteRepository.save(origem);
        clienteRepository.save(destino);

        registrarTransacao(contaOrigem, valor, "TRANSFERENCIA");
        registrarTransacao(contaDestino, valor, "TRANSFERENCIA");
    }

    public List<TransacaoDTO> listaTransacoes(String login){
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        Long contaId = cliente.getConta().getId();

        return transacaoRepository.findByContaIdOrderByDataHoraDesc(contaId)
        .stream()
        .map(t -> new TransacaoDTO(t.getTipo(), t.getValor(), t.getDataHora()))
        .toList();
    }
}


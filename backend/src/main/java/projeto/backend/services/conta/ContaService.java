package projeto.backend.services.conta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import projeto.backend.dto.ContaDTO.DashboardDTO;
import projeto.backend.dto.ContaDTO.FiltroTransacaoDTO;
import projeto.backend.dto.ContaDTO.PixDTO;
import projeto.backend.dto.ContaDTO.TransacaoDTO;
import projeto.backend.model.cliente.Cliente;
import projeto.backend.model.cliente.Usuario;
import projeto.backend.model.conta.Conta;
import projeto.backend.model.conta.Transacao;
import projeto.backend.repository.cliente.ClienteRepository;
import projeto.backend.repository.conta.TransacaoRepository;
import projeto.backend.repository.conta.ContaRepository;


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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ContaRepository contaRepository;

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

    public void realizarPix(PixDTO dto, String loginCliente){
        Conta origem = contaRepository.findByCliente_Usuario_Login(loginCliente);

        if (origem == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada.");
        }

        double saldoAtual = origem.getSaldo();        // supondo que saldo seja do tipo double
        double valorPix = dto.getValor();             // e PixDTO.valor também seja double

        if (saldoAtual < valorPix) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente.");
        }

        origem.setSaldo(saldoAtual - valorPix);       // subtrai o valor do Pix
        contaRepository.save(origem);

        Transacao transacao = new Transacao();
        transacao.setTipo("PIX");
        transacao.setValor(-valorPix);                // valor negativo para saída de saldo
        transacao.setConta(origem);
        transacao.setDataHora(LocalDateTime.now());   // supondo que o campo seja 'dataHora'

        transacaoRepository.save(transacao);
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

    public DashboardDTO getDashboard(String login){
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Conta conta = cliente.getConta();

        List<TransacaoDTO> ultimas = transacaoRepository.findByContaIdOrderByDataHoraDesc(conta.getId())
            .stream()
            .limit(5)
            .map(t -> new TransacaoDTO(t.getTipo(), t.getValor(), t.getDataHora()))
            .toList();

        return new DashboardDTO(conta.getSaldo(), conta.getCredito(), ultimas);
    }

    public List<TransacaoDTO> filtrarTransacoesPorData(String login, FiltroTransacaoDTO filtro){
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Long contaId = cliente.getConta().getId();

        return transacaoRepository.findByContaIdOrderByDataHoraDesc(contaId)
            .stream()
            .filter(t -> {
                LocalDate data = t.getDataHora().toLocalDate();
                return (data.isEqual(filtro.dataInicio()) || data.isAfter(filtro.dataInicio())) &&
                       (data.isEqual(filtro.dataFim()) || data.isBefore(filtro.dataFim()));    
            })
            .map(t -> new TransacaoDTO(t.getTipo(), t.getValor(), t.getDataHora()))
            .toList();
    }

    public byte[] gerarCSVTransacoes(String login){
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Long contaId = cliente.getConta().getId();

        List<Transacao> transacoes = transacaoRepository.findByContaIdOrderByDataHoraDesc(contaId);

        StringBuilder sb = new StringBuilder();
        sb.append("tipo,Valor,DataHora\n");

        for(Transacao t : transacoes){
            sb.append(t.getTipo()).append(",");
            sb.append(t.getValor()).append(",");
            sb.append(t.getDataHora()).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] gerarPDFTransacoes(String login){
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Transacao> transacoes = transacaoRepository.findByContaIdOrderByDataHoraDesc(cliente.getConta().getId());

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 14);
            content.beginText();
            content.newLineAtOffset(50, 750);
            content.showText("Historico de Transações - cliente: " + cliente.getNome());
            content.endText();

            content.setFont(PDType1Font.HELVETICA, 12);
            int y = 720;

            for (Transacao t : transacoes){
                if (y < 50){
                    content.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    content = new PDPageContentStream(document, page);
                    content.setFont(PDType1Font.HELVETICA, 12);
                    y = 750;

                }

                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(t.getTipo() + " | R$" + t.getValor() + " | " + t.getDataHora());
                content.endText();
                y -= 20;
            }

            content.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();

        } catch (IOException e){
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    public void sacarComSenha(String login, Double valor, String senha){
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Usuario usuario = cliente.getUsuario();

        if(usuario.getBloqueadoAte() != null && usuario.getBloqueadoAte().isAfter(LocalDateTime.now())){
            throw new RuntimeException("Conta bloqueada até " + usuario.getBloqueadoAte());
        }

        if(!passwordEncoder.matches(senha, cliente.getUsuario().getSenha())){
            int tentativas = usuario.getTentativasFalhas() + 1;
            usuario.setTentativasFalhas(tentativas);

            if(tentativas >= 3){
                usuario.setBloqueadoAte(LocalDateTime.now().plusMinutes(15));
                usuario.setTentativasFalhas(0);
            }
            
            clienteRepository.save(cliente);
            throw new RuntimeException("Senha incorreta");
        }

        usuario.setTentativasFalhas(0);
        usuario.setBloqueadoAte(null);

        Conta conta = cliente.getConta();
        conta.sacar(valor);
        
        clienteRepository.save(cliente);
        registrarTransacao(conta, valor, "SAQUE");
    }

    public void transferirComSenha(String origemLogin, String destinoLogin, Double valor, String senha){
        Cliente origem = clienteRepository.findByUsuarioLogin(origemLogin)
            .orElseThrow(() -> new RuntimeException("Cliente origem não encontrado"));

        Usuario usuario = origem.getUsuario();

        if(usuario.getBloqueadoAte() != null && usuario.getBloqueadoAte().isAfter(LocalDateTime.now())){
            throw new RuntimeException("Conta bloqueada até " + usuario.getBloqueadoAte());
        }

        if(!passwordEncoder.matches(senha, usuario.getSenha())){
            int tentativas = usuario.getTentativasFalhas() + 1;
            usuario.setTentativasFalhas(tentativas);

            if(tentativas >= 3){
                usuario.setBloqueadoAte(LocalDateTime.now().plusMinutes(15));
                usuario.setTentativasFalhas(0);
            }
            
            clienteRepository.save(origem);
            throw new RuntimeException("Senha incorreta");
        }

        usuario.setTentativasFalhas(0);
        usuario.setBloqueadoAte(null);
        Cliente destino = clienteRepository.findByUsuarioLogin(destinoLogin)
            .orElseThrow(() -> new RuntimeException("Cliente destino não encontrado"));

        Conta contaOrigem = origem.getConta();
        Conta contaDestino = destino.getConta();

        if (ultrapassouLimiteDiario(contaOrigem, valor)){
            throw new RuntimeException("Limite diario excedido");
        }

        contaOrigem.sacar(valor);
        contaDestino.depositar(valor);

        clienteRepository.save(origem);
        clienteRepository.save(destino);

        registrarTransacao(contaOrigem, valor, "TRANSFERENCIA");
        registrarTransacao(contaDestino, valor, "TRANSFERENCIA");

    }


}


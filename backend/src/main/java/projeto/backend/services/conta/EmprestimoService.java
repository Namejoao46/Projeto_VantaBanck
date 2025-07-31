package projeto.backend.services.conta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projeto.backend.dto.ContaDTO.SimulacaoResultadoDTO;
import projeto.backend.model.cliente.Cliente;
import projeto.backend.model.conta.Conta;
import projeto.backend.repository.cliente.ClienteRepository;
import projeto.backend.repository.conta.ContaRepository;

@Service
public class EmprestimoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    private SimulacaoResultadoDTO simularEmprestimoCompleto(Cliente cliente, double valor, int meses){
        double juros = 0.02;
        double valorFinal = valor * Math.pow(1 + juros, meses);

        SimulacaoResultadoDTO resultado = new SimulacaoResultadoDTO();
        resultado.setValorFinal(valorFinal);
        resultado.setParcelaMensal(valorFinal / meses);
        resultado.setMeses(meses);
        resultado.setTaxaJurosMensal(juros);

        String faixa;
        double salario = cliente.getSalario();

        if (salario < 2000) faixa = "Baixa";
        else if (salario < 8000) faixa = "Média";
        else faixa = "Alta";

        resultado.setFaixaSalarial(faixa);

        double limite = cliente.getSalario() * 10;
        resultado.setLimiteDisponivel(limite);

        return resultado;
    }

    public SimulacaoResultadoDTO simular(String login, double valor, int meses){
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return simularEmprestimoCompleto(cliente, valor, meses);
    }

    public void aceitarEmprestimo(String login, double valor, int meses){
        Cliente cliente = clienteRepository.findByUsuarioLogin(login)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        SimulacaoResultadoDTO resultado = simularEmprestimoCompleto(cliente, valor, meses);

        System.out.println(resultado);

        Conta conta = cliente.getConta();

        // Validação do crédito
        if (conta.getCredito() < valor) {
            throw new RuntimeException("Crédito insuficiente para o empréstimo.");
        }

        conta.depositar(valor);
        conta.setCredito(conta.getCredito() - valor);
        contaRepository.save(conta);
    }
}

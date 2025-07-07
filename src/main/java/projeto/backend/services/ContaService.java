package projeto.backend.services;

import org.springframework.stereotype.Service;

import projeto.backend.model.Cliente;
import projeto.backend.model.Conta;

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
}

package projeto.backend.model.conta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import projeto.backend.model.cliente.Cliente;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double saldo;
    private Double credito;
    private String agencia;

    @OneToOne
    @JoinColumn(name= "cliente_id")
    private Cliente cliente;

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public Double getSaldo(){ return saldo; }
    public void setSaldo(Double saldo){ this.saldo = saldo; }

    public Double getCredito(){ return credito; }
    public void setCredito(Double credito){this.credito = credito; }

    public String getAgencia(){ return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }

    public Cliente getCliente(){ return cliente; }
    public void setCliente( Cliente cliente){ this.cliente = cliente; }


    // Método para realizar depósito
    public void depositar(Double valor){

        // Valida se o valor é positivo
        if(valor <= 0 ) throw new IllegalArgumentException("Valor inválido");

        // Adiciona o valor ao saldo
        this.saldo += valor;
    }

    // Método para realizar saque
    public void sacar(Double valor){

        // Valida se o valor é positivo e se há saldo suficiente
        if(valor <= 0 || valor > saldo) throw new IllegalArgumentException("Saldo insuficiente");

        // Subtrai o valor do saldo
        this.saldo -= valor;
    }
}

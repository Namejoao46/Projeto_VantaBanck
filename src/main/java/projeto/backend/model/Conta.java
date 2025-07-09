package projeto.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double saldo;
    private Double credito;

    @OneToOne
    @JoinColumn(name= "cliente_id")
    private Cliente cliente;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Double getSaldo(){
        return saldo;
    }

    public void setSaldo(Double saldo){
        this.saldo = saldo;
    }

    public Double getCredito(){
        return credito;
    }

    public void setCredito(Double credito){
        this.credito = credito;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente( Cliente cliente){
        this.cliente = cliente;
    }
}

package projeto.backend.model.conta;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;
    
    private String tipo;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public Long getId(){ return id; }
    public void setId( Long id){ this.id = id; }

    public Double getValor(){ return valor; }
    public void setValor( Double valor){ this.valor = valor; }

    public String getTipo(){ return tipo; }
    public void setTipo( String tipo){ this.tipo = tipo; }

    public LocalDateTime getDataHora(){ return dataHora; }
    public void setDataHora( LocalDateTime dataHora){ this.dataHora = dataHora; }

    public Conta getConta(){ return conta; }
    public void setConta( Conta conta){ this.conta = conta; }


}

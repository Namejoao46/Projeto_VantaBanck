package projeto.backend.model.conta;

import jakarta.persistence.*;
import projeto.backend.model.cliente.Cliente;

import java.time.LocalDate;

@Entity
public class Cofrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String imagemUrl;
    private Double valorGuardado;
    private LocalDate dataInicio;
    private LocalDate dataRetirada;
    private Double rendimento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente dono;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public Double getValorGuardado() { return valorGuardado; }
    public void setValorGuardado(Double valorGuardado) { this.valorGuardado = valorGuardado; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataRetirada() { return dataRetirada; }
    public void setDataRetirada(LocalDate dataRetirada) { this.dataRetirada = dataRetirada; }

    public Double getRendimento() { return rendimento; }
    public void setRendimento(Double rendimento) { this.rendimento = rendimento; }

    public Cliente getDono() { return dono; }
    public void setDono(Cliente dono) { this.dono = dono; }
}
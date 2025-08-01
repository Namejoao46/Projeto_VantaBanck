package projeto.backend.dto.ContaDTO;

import java.time.LocalDate;

public class CofrinhoDTO {
    private String nome;
    private String imagem;
    private Double valor;
    private LocalDate dataRetirada;

    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public LocalDate getDataRetirada() { return dataRetirada; }
    public void setDataRetirada(LocalDate dataRetirada) { this.dataRetirada = dataRetirada; }

}

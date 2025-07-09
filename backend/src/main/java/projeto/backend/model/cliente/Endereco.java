package projeto.backend.model.cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Endereco {

    @Id
    @GeneratedValue
    private Long id;

    private String cep;
    private String rua;
    private String casa;
    private String bairo;
    private String cidade;
    private String estado;
    private String lougradouro;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCep(){
        return cep;
    }

    public void setCep(String cep){
        this.cep = cep;
    }

    public String getRua(){
        return rua;
    }

    public void setRua (String rua){
        this.rua = rua;
    }
    public String getCasa(){
        return casa;
    }

    public void setCasa(String casa){
        this. casa= casa;
    }

    public String getBairo(){
        return bairo;
    }

    public void setBairo(String bairo){
        this.bairo = bairo;
    }

    public String getCidade(){
        return cidade;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public String getLougradouro(){
        return lougradouro;
    }

    public void setLougradouro(String lougradouro){
        this.lougradouro = lougradouro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

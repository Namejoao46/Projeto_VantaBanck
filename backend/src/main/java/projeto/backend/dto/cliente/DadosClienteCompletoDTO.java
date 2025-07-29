package projeto.backend.dto.cliente;

public class DadosClienteCompletoDTO {
    private String nome;
    private String cpf;
    private String email;
    private String banco;
    private String agencia;
    private String numeroConta;

    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }
}

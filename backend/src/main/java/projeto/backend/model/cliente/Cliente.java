package projeto.backend.model.cliente;

import java.time.LocalDate;
import java.util.List;

//import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import projeto.backend.model.conta.Cofrinho;
import projeto.backend.model.conta.Conta;

@Entity  // Indica que essa classe será mapeada como uma tabela no banco de dados
public class Cliente {

    @Id  // Define o campo 'id' como chave primária
    @GeneratedValue(strategy= GenerationType.IDENTITY)  // Gera o ID automaticamente (auto-incremento)
    private Long id;

    @Column(nullable= false)  // Campo obrigatório no banco
    private String nome;
    @Column(nullable= false, unique= true)  // CPF deve ser único e obrigatório
    private String cpf;
    @Column(name = "data_nascimento")// Define o nome da coluna no banco
    private LocalDate dataNascimento;
    private String telefone;
    @Column(nullable = false)
    private Double salario;
    private String funcao;

    // Relacionamento 1:1 com a entidade Usuario
    // Cascade.ALL garante que ao salvar o cliente, o usuário também será salvo
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id") // Cria a coluna 'usuario_id' como chave estrangeira
    private Usuario usuario; // Dados de login e autenticação

    // Relacionamento 1:1 com a entidade Endereco
    // Também com cascade para salvar tudo junto
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")  // Cria a coluna 'endereco_id' como chave estrangeira
    private Endereco endereco;  // Endereço residencial do cliente

    @OneToOne(mappedBy= "cliente", cascade = CascadeType.ALL)
    private Conta conta; 

    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL)
    private List<Cofrinho> cofrinhos;

    // Construtor vazio necessário para o JPA
    public Cliente() {}

    // Construtor com todos os campos (opcional, útil para testes ou criação manual)
    public Cliente(String nome, String cpf, LocalDate dataNascimento, String telefone, Double salario, String funcao, Usuario usuario, Endereco endereco, Conta conta, List<Cofrinho> cofrinhos){
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.salario = salario;
        this.funcao = funcao;
        this.usuario = usuario;
        this.endereco = endereco;
        this.conta = conta;
        this.cofrinhos = cofrinhos;
    }
    
    // Getters e Setters para acessar e modificar os atributos
    public Long getId (){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public String getCpf(){ return cpf; }
    public void setCpf(String cpf){ this.cpf = cpf; }

    public LocalDate getDataNascimento(){ return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento){ this.dataNascimento = dataNascimento; }

    public String getTelefone(){ return telefone; }
    public void setTelefone(String telefone){ this.telefone = telefone; }

    public Double getSalario(){ return salario; }
    public void setSalario(Double salario){ this.salario = salario; }

    public String getFuncao(){ return funcao; }
    public void setFuncao(String funcao){ this.funcao = funcao; }

    public Usuario getUsuario(){ return usuario; }
    public void setUsuario(Usuario usuario){ this.usuario = usuario; }

    public Endereco getEndereco(){ return endereco; }
    public void setEndereco(Endereco endereco){ this.endereco = endereco; }

    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }

    public List<Cofrinho> getCofrinhos() { return cofrinhos; }
    public void setCofrinhos(List<Cofrinho> cofrinhos) { this.cofrinhos = cofrinhos; }
    
}

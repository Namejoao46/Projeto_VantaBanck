package projeto.backend.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity  // Indica que essa classe será mapeada como uma tabela no banco de dados
public class Cliente {

    @Id  // Define o campo 'id' como chave primária
    @GeneratedValue(strategy= GenerationType.IDENTITY)  // Gera o ID automaticamente (auto-incremento)
    private Long id_usuario;

    @Column(nullable= false)  // Campo obrigatório no banco
    private String nome;

    @Column(nullable= false, unique= true)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalData dataNascimento;

    private String telefone;

    // Relacionamento 1:1 com a entidade Usuario
    // Cascade.ALL garante que ao salvar o cliente, o usuário também será salvo
    @OneToOne(cascada = CascadeType.ALL)
    @JoinColumn(name = "usuario_id") // Cria a coluna 'usuario_id' como chave estrangeira
    private Usuario usuario; // Dados de login e autenticação

    // Relacionamento 1:1 com a entidade Endereco
    // Também com cascade para salvar tudo junto
    @OneToOne(cascada = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")  // Cria a coluna 'endereco_id' como chave estrangeira
    private Endereco endereco;  // Endereço residencial do cliente


    public cliente() {}

    public Cliente(String nome, String cpf, LocalData dataNascimento, String telefone, Usuario usuario, Endereco endereco){
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.usuario = usuario;
        this.endereco = endereco;
    }
}

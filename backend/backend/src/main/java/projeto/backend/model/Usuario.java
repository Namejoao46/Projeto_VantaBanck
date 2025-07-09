package projeto.backend.model;

import jakarta.persistence.Column;

// Importa as anotações da JPA (Jakarta Persistence API),
// que são usadas para mapear a classe para uma tabela no banco de dados.

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


// Marca essa classe como uma entidade JPA.
// Isso significa que ela será mapeada para uma tabela no banco de dados.
// Cada instância dessa classe representa uma linha na tabela.
@Entity
public class Usuario {
    
    // Indica que o valor do ID será gerado automaticamente pelo banco de dados.
    // A estratégia IDENTITY é usada para auto-incremento (ex: MySQL).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Define a coluna 'login' como única e obrigatória.
    // Isso garante que dois usuários não possam ter o mesmo login (ex: e-mail ou CPF).
    @Column(unique = true, nullable= false)
    private String login;
    
    // Define a coluna 'senha' como obrigatória.
    // A senha será usada para autenticação, então não pode ser nula.
    @Column(nullable= false)
    private String senha;

    // Define a coluna 'tipo' como obrigatória.
    // Esse campo indica o tipo de usuário (ex: CLIENTE ou FUNCIONARIO),
    // o que permite controlar permissões e acessos no sistema.
    private String tipo;
    
    // Construtor vazio obrigatório para o JPA funcionar corretamente.
    // O JPA usa reflexão para instanciar objetos, então precisa de um construtor padrão.
    public Usuario() {}

    // Construtor com parâmetros para facilitar a criação de objetos manualmente.
    // Útil em testes ou quando você quiser criar um usuário diretamente no código.
    public Usuario(String login, String senha, String tipo){
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters permitem acessar e modificar os atributos da classe.
    // Eles são necessários para que o Spring e o JPA consigam manipular os dados.

    public long getId_usuario(){
        return id;
    }

    public void setId_usuario(Long id){
        this.id = id;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getSenha (){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }
}

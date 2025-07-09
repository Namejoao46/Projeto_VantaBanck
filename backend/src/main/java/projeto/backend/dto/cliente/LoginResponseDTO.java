package projeto.backend.dto.cliente;

public class LoginResponseDTO {

    private String login;
    private String tipo;
    private String token;

    public LoginResponseDTO(String login, String tipo, String token){
        this.login = login;
        this.tipo = tipo;
        this.token = token;
    }

    public String getLogin(){ return login; }
    public String getTipo(){ return tipo; }
    public String getToken(){ return token; }
}

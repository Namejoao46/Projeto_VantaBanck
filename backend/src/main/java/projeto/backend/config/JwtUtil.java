package projeto.backend.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import projeto.backend.model.cliente.Usuario;

import java.security.Key;
import java.util.Date;
import java.util.List;

public class JwtUtil {

    private static final String SECRET = "namelesslion1210namelesslion1210";
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(String login, String tipo) {
        return Jwts.builder()
        .setSubject(login)
        .claim("roles", List.of(tipo))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key)
        .compact();
    }

    public static String getLoginFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public static String getTipoFromToken(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("tipo");
    }

    @SuppressWarnings("unchecked")
    public static List<String> getRolesFromToken(String token){
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("roles", List.class);
    }

    public static String gerarToken(Usuario usuario){
        return generateToken(usuario.getLogin(), usuario.getTipo());
    }
}

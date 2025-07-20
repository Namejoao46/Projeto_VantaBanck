package projeto.backend.config;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

     @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull FilterChain filterChain)
                                throws ServletException, IOException {

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        System.out.println("🔄 Requisição OPTIONS recebida — ignorando autenticação");
        filterChain.doFilter(request, response);
        return;
    }
    
    System.out.println("🛡️ Verificando se tem JWT...");

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        try {
            System.out.println("🔐 Token recebido: " + token);

            String login = JwtUtil.getLoginFromToken(token);
            List<String> roles = JwtUtil.getRolesFromToken(token);

            System.out.println("👤 Login extraído: " + login);
            System.out.println("🛡️ Roles extraídas: " + roles);

            List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

            System.out.println("✅ Authorities construídas: " + authorities);

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(login, null, authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("🔓 Usuário autenticado com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao processar token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    } else {
        System.out.println("⚠️ Nenhum token JWT encontrado no header Authorization.");
    }

    filterChain.doFilter(request, response);
}
}

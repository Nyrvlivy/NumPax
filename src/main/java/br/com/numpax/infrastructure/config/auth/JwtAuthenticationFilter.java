package br.com.numpax.infrastructure.config.auth;

import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.UserRepository;
import br.com.numpax.infrastructure.repositories.impl.UserRepositoryImpl;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter implements Filter {

    private JwtUtil jwtUtil;
    private UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Instanciar as dependências necessárias
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        userRepository = new UserRepositoryImpl(connectionManager.getConnection());
        jwtUtil = new JwtUtil();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Cast para HttpServletRequest e HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Obter o header Authorization
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            if (jwtUtil.validateJwtToken(jwtToken)) {
                String userId = jwtUtil.getUserIdFromJwtToken(jwtToken);
                User user = userRepository.findById(userId).orElse(null);
                if (user != null && user.isActive()) {
                    // Definir o usuário nos atributos da requisição
                    httpRequest.setAttribute("user", user);
                    // Prosseguir com a cadeia de filtros
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        // Se o token for inválido ou ausente, retornar não autorizado
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token");
    }

    @Override
    public void destroy() {
        // Limpeza se necessário
    }
}

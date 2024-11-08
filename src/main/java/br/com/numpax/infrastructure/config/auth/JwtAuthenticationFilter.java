package br.com.numpax.infrastructure.config.auth;

import br.com.numpax.application.services.UserService;
import br.com.numpax.infrastructure.entities.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JwtAuthenticationFilter implements Filter {

    private UserService userService;

    public JwtAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialização, se necessário
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String jwt = getJwtFromRequest(httpServletRequest);

        if (jwt != null && JwtUtil.validateToken(jwt)) {
            String userId = JwtUtil.getUserIdFromJWT(jwt);
            User user = userService.findUserById(userId);
            // Você pode armazenar o usuário na sessão ou contexto para uso posterior
            httpServletRequest.setAttribute("authenticatedUser", user);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Limpeza, se necessário
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

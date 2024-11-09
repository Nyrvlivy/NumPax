package br.com.numpax.infrastructure.config.auth;

import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            if (jwtUtil.validateJwtToken(jwtToken)) {
                String userId = jwtUtil.getUserIdFromJwtToken(jwtToken);
                User user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    httpRequest.setAttribute("user", user);
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token");
    }

    @Override
    public void destroy() {
    }
}

package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.LoginRequestDTO;
import br.com.numpax.API.V1.dto.response.AuthResponseDTO;
import br.com.numpax.application.services.AuthService;
import br.com.numpax.application.services.impl.AuthServiceImpl;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.repositories.impl.UserRepositoryImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@WebServlet("/api/v1/auth/*")
public class AuthController extends HttpServlet {
    
    private AuthService authService;
    private ObjectMapper objectMapper;

    @Override
    public void init() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(connectionManager.getConnection());
        authService = new AuthServiceImpl(userRepository);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String pathInfo = request.getPathInfo();
            
            if ("/login".equals(pathInfo)) {
                LoginRequestDTO loginRequest = objectMapper.readValue(request.getReader(), LoginRequestDTO.class);
                AuthResponseDTO authResponse = authService.login(loginRequest);
                sendJsonResponse(response, HttpServletResponse.SC_OK, authResponse);
            } 
            else if ("/refresh".equals(pathInfo)) {
                String refreshToken = request.getHeader("Refresh-Token");
                AuthResponseDTO authResponse = authService.refreshToken(refreshToken);
                sendJsonResponse(response, HttpServletResponse.SC_OK, authResponse);
            }
            
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private void sendJsonResponse(HttpServletResponse response, int status, Object data) {
        try {
            response.setStatus(status);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), data);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) {
        try {
            response.setStatus(status);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), Map.of("error", message));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
} 
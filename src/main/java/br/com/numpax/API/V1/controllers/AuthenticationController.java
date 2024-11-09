package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.UserLoginRequestDTO;
import br.com.numpax.API.V1.dto.response.LoginResponseDTO;
import br.com.numpax.API.V1.dto.response.ErrorResponse;
import br.com.numpax.API.V1.exceptions.AuthenticationException;
import br.com.numpax.application.services.UserService;
import br.com.numpax.infrastructure.config.auth.JwtUtil;
import br.com.numpax.infrastructure.entities.User;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    public Response login(@Valid UserLoginRequestDTO loginRequest) {
        try {
            // Autenticar usuário
            User user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
            
            // Gerar token JWT
            String token = JwtUtil.generateToken(user.getUserId());
            
            // Criar resposta
            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(token);
            response.setUserId(user.getUserId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setActive(user.isActive());
            
            return Response.ok(response).build();
            
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResponse("Authentication failed", e.getMessage()))
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal server error", "An unexpected error occurred"))
                .build();
        }
    }
} 
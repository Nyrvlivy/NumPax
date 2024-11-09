package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.LoginRequestDTO;
import br.com.numpax.API.V1.dto.response.LoginResponseDTO;
import br.com.numpax.application.services.AuthService;
import br.com.numpax.application.services.impl.AuthServiceImpl;
import br.com.numpax.infrastructure.config.auth.JwtUtil;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.repositories.UserRepository;
import br.com.numpax.infrastructure.repositories.impl.UserRepositoryImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.Connection;

@Path("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController() {
        // Instanciar as dependências necessárias
        Connection connection = ConnectionManager.getInstance().getConnection();
        UserRepository userRepository = new UserRepositoryImpl(connection);
        JwtUtil jwtUtil = new JwtUtil();
        this.authService = new AuthServiceImpl(userRepository, jwtUtil);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO responseDTO = authService.login(loginRequestDTO);
            return Response.ok(responseDTO).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }
}

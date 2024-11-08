package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.UserLoginRequestDTO;
import br.com.numpax.API.V1.dto.response.LoginResponseDTO;
import br.com.numpax.application.services.UserService;
import br.com.numpax.infrastructure.config.auth.JwtUtil;
import br.com.numpax.infrastructure.entities.User;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/auth")
public class AuthenticationController {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid UserLoginRequestDTO loginRequest) {
        try {
            User user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
            String token = JwtUtil.generateToken(user.getUserId());
            
            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(token);
            response.setUserId(user.getUserId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            
            return Response.ok(response).build();
            
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResponse("Authentication failed", e.getMessage()))
                .build();
        }
    }
} 
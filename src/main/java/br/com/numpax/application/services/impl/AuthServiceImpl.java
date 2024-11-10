package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.LoginRequestDTO;
import br.com.numpax.API.V1.dto.response.LoginResponseDTO;
import br.com.numpax.application.services.AuthService;
import br.com.numpax.application.utils.PasswordValidatorUtil;
import br.com.numpax.infrastructure.config.auth.JwtUtil;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.UserRepository;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.getEmail());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email e/ou senha inválidos");
        }

        User user = userOptional.get();
        boolean passwordMatches = PasswordValidatorUtil.checkPassword(loginRequestDTO.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new RuntimeException("Email e/ou senha inválidos");
        }

        String token = jwtUtil.generateToken(user);
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);
        return responseDTO;
    }
}

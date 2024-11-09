package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.LoginRequestDTO;
import br.com.numpax.API.V1.dto.response.AuthResponseDTO;
import br.com.numpax.API.V1.exceptions.UnauthorizedException;
import br.com.numpax.API.V1.mappers.UserMapper;
import br.com.numpax.application.services.AuthService;
import br.com.numpax.application.utils.PasswordValidatorUtil;
import br.com.numpax.infrastructure.config.auth.JwtUtil;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.UserRepository;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Credenciais inválidas"));

        if (!PasswordValidatorUtil.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Credenciais inválidas");
        }

        String accessToken = JwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = JwtUtil.generateRefreshToken(user.getUserId());

        return new AuthResponseDTO(
            accessToken,
            refreshToken,
            UserMapper.toResponseDTO(user)
        );
    }

    @Override
    public AuthResponseDTO refreshToken(String refreshToken) {
        if (!JwtUtil.validateToken(refreshToken, true)) {
            throw new UnauthorizedException("Refresh token inválido");
        }

        String userId = JwtUtil.getUserIdFromToken(refreshToken, true);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UnauthorizedException("Usuário não encontrado"));

        String newAccessToken = JwtUtil.generateAccessToken(userId);
        String newRefreshToken = JwtUtil.generateRefreshToken(userId);

        return new AuthResponseDTO(
            newAccessToken,
            newRefreshToken,
            UserMapper.toResponseDTO(user)
        );
    }
} 
package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.LoginRequestDTO;
import br.com.numpax.API.V1.dto.response.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}

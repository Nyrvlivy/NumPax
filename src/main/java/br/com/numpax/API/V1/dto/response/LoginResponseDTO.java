package br.com.numpax.API.V1.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String token;
    private String userId;
    private String name;
    private String email;
    private boolean isActive;
} 
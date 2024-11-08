package br.com.numpax.API.V1.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO {
    private String userId;
    private String name;
    private String email;
    private LocalDate birthdate;
}

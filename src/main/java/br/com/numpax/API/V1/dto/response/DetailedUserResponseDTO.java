package br.com.numpax.API.V1.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DetailedUserResponseDTO {
    private String userId;
    private String name;
    private String email;
    private LocalDate birthdate;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

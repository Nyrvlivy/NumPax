package br.com.numpax.API.V1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequestDTO {

    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Email(message = "Invalid email format")
    @Size(max = 320, message = "Email must not exceed 320 characters")
    private String email;

    @Size(min = 6, max = 256, message = "Password must be between 6 and 256 characters")
    private String password;

    @PastOrPresent(message = "Birthdate cannot be a future date")
    private LocalDate birthdate;
}

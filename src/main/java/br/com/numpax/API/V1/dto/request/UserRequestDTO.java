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
public class UserRequestDTO {

    @NotBlank(message = "Name value is required.")
    @Size(max = 100, message = "Name value must have a maximum of 100 characters.")
    private String name;

    @NotBlank(message = "Email value is required.")
    @Email(message = "Email value must be a valid email.")
    @Size(max = 320, message = "Email value must have a maximum of 320 characters.")
    private String email;

    @NotBlank(message = "Password value is required.")
    @Size(min = 8, max = 255, message = "Password value must have between 8 and 255 characters.")
    private String password;

    @PastOrPresent(message = "Birthdate cannot be a future date.")
    private LocalDate birthdate;
}

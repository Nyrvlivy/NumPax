package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequestDTO {

    @NotBlank(message = "Name value is required.")
    @Size(max = 100, message = "Name value must have a maximum of 100 characters.")
    private String name;

    @Size(max = 255, message = "Description value must have a maximum of 255 characters.")
    private String description;

    @NotNull(message = "Account type value is required.")
    private AccountType accountType;
}

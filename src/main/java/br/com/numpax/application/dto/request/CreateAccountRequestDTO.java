package br.com.numpax.application.dto.request;

import br.com.numpax.application.model.AccountType;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class CreateAccountRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Account type is required")
    private AccountType accountType;
} 
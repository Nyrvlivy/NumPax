package br.com.numpax.application.dto.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CreateCheckingAccountRequestDTO extends CreateAccountRequestDTO {
    @NotBlank(message = "Bank code is required")
    @Pattern(regexp = "\\d{1,4}", message = "Bank code must be 1-4 digits")
    private String bankCode;
    
    @NotBlank(message = "Agency is required")
    @Pattern(regexp = "\\d{1,4}", message = "Agency must be 1-4 digits")
    private String agency;
    
    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "\\d{1,12}", message = "Account number must be 1-12 digits")
    private String accountNumber;
} 
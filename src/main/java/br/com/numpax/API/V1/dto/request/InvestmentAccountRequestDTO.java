package br.com.numpax.API.V1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestmentAccountRequestDTO {
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    private String name;
    
    @Size(max = 255, message = "Description must have a maximum of 255 characters")
    private String description;
    
    @NotBlank(message = "Institution is required")
    @Size(max = 100, message = "Institution must have a maximum of 100 characters")
    private String institution;
    
    private String accountNumber;
    private String agency;
    private String note;
} 
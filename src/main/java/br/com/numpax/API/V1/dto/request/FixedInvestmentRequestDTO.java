package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.FixedInvestmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FixedInvestmentRequestDTO {
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    private String name;
    
    @Size(max = 255, message = "Description must have a maximum of 255 characters")
    private String description;
    
    @NotNull(message = "Investment type is required")
    private FixedInvestmentType investmentType;
    
    @NotNull(message = "Investment account ID is required")
    private String investmentAccountId;
    
    @NotNull(message = "Investment amount is required")
    @Positive(message = "Investment amount must be positive")
    private BigDecimal investmentAmount;
    
    @NotNull(message = "Tax rate is required")
    @Positive(message = "Tax rate must be positive")
    private BigDecimal taxRate;
    
    @NotNull(message = "Investment date is required")
    private LocalDate investmentDate;
    
    @NotNull(message = "Maturity date is required")
    private LocalDate maturityDate;
    
    @NotNull(message = "Expected return is required")
    @Positive(message = "Expected return must be positive")
    private BigDecimal expectedReturn;
    
    private String broker;
    private String institution;
    private String note;
} 
package br.com.numpax.API.V1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FixedInvestmentRequestDTO {
    @NotBlank(message = "Code is required")
    private String code;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "Category ID is required")
    private String categoryId;
    
    @NotNull(message = "Account ID is required")
    private String accountId;
    
    @NotNull(message = "Investment date is required")
    private LocalDate investmentDate;
    
    @NotNull(message = "Investment type is required")
    private String fixedInvestmentType;
    
    @NotNull(message = "Expiration date is required")
    private LocalDate expirationDate;
    
    @NotNull(message = "Institution is required")
    private String institution;
    
    @NotNull(message = "Interest rate is required")
    @Positive(message = "Interest rate must be positive")
    private BigDecimal interestRate;
    
    private Integer liquidityPeriod;
    private String note;
} 
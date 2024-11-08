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
    
    @NotBlank(message = "Category ID is required")
    private String categoryId;
    
    @NotBlank(message = "Account ID is required")
    private String accountId;
    
    @NotBlank(message = "Investment type is required")
    private String fixedInvestmentType;
    
    @NotNull(message = "Investment date is required")
    private LocalDate investmentDate;
    
    private LocalDate expirationDate;
    
    private String institution;
    
    private String receiver;
    private String sender;
    private String note;
} 
package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.FixedInvestmentType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FixedInvestmentUpdateRequestDTO {
    
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    private String name;
    
    @Size(max = 255, message = "Description must have a maximum of 255 characters")
    private String description;
    
    private FixedInvestmentType investmentType;
    private String investmentAccountId;
    
    @Positive(message = "Investment amount must be positive")
    private BigDecimal investmentAmount;
    
    @Positive(message = "Tax rate must be positive")
    private BigDecimal taxRate;
    
    private LocalDate investmentDate;
    private LocalDate maturityDate;
    
    @Positive(message = "Expected return must be positive")
    private BigDecimal expectedReturn;
    
    private String broker;
    private String institution;
    private String note;
    private Boolean isRedeemed;
    private LocalDate redemptionDate;
    
    @Positive(message = "Redemption amount must be positive")
    private BigDecimal redemptionAmount;
} 
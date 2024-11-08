package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.VariableInvestmentType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class VariableInvestmentUpdateRequestDTO {
    
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    private String name;
    
    @Size(max = 255, message = "Description must have a maximum of 255 characters")
    private String description;
    
    private VariableInvestmentType investmentType;
    private String investmentAccountId;
    
    @Size(max = 10, message = "Ticker must have a maximum of 10 characters")
    private String ticker;
    
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    @Positive(message = "Purchase price must be positive")
    private BigDecimal purchasePrice;
    
    private LocalDate purchaseDate;
    private String sector;
    private String broker;
    private String note;
    private BigDecimal currentPrice;
} 
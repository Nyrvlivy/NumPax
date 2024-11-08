package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.VariableInvestmentType;
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
public class VariableInvestmentRequestDTO {
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    private String name;
    
    @Size(max = 255, message = "Description must have a maximum of 255 characters")
    private String description;
    
    @NotNull(message = "Investment type is required")
    private VariableInvestmentType investmentType;
    
    @NotNull(message = "Investment account ID is required")
    private String investmentAccountId;
    
    @NotNull(message = "Ticker is required")
    @Size(max = 10, message = "Ticker must have a maximum of 10 characters")
    private String ticker;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    @NotNull(message = "Purchase price is required")
    @Positive(message = "Purchase price must be positive")
    private BigDecimal purchasePrice;
    
    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;
    
    private String sector;
    private String broker;
    private String note;
} 
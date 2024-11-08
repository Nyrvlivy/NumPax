package br.com.numpax.API.V1.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FixedInvestmentRedeemRequestDTO {
    
    @NotNull(message = "Redemption date is required")
    private LocalDate redeemDate;
    
    @NotNull(message = "Redemption amount is required")
    @Positive(message = "Redemption amount must be positive")
    private BigDecimal redeemValue;
    
    private String note;
} 
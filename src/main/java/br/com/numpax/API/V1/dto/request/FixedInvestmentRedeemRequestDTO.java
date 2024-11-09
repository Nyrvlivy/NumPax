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
    @NotNull(message = "Redeem date is required")
    private LocalDate redeemDate;
    
    @NotNull(message = "Redeem value is required")
    @Positive(message = "Redeem value must be positive")
    private BigDecimal redeemValue;
    
    private String note;
} 
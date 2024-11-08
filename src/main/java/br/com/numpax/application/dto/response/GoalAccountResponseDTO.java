package br.com.numpax.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalAccountResponseDTO extends AccountResponseDTO {
    private BigDecimal targetValue;
    private BigDecimal amountValue;
    private BigDecimal targetTaxRate;
    private BigDecimal monthlyTaxRate;
    private BigDecimal monthlyEstimate;
    private BigDecimal monthlyAchievement;
    private String categoryId;
    private LocalDate targetDate;
    private LocalDate startDate;
    private LocalDate endDate;
} 
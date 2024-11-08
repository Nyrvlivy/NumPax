package br.com.numpax.API.V1.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GoalAccountUpdateRequestDTO {

    private String name;
    private String description;
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

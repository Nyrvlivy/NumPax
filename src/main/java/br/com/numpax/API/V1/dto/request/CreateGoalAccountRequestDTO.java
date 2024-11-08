package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.AccountType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateGoalAccountRequestDTO extends CreateAccountRequestDTO {
    @NotNull(message = "Target value is required")
    @Positive(message = "Target value must be positive")
    private BigDecimal targetValue;

    private BigDecimal amountValue;
    private BigDecimal targetTaxRate;
    private BigDecimal monthlyTaxRate;
    private BigDecimal monthlyEstimate;
    private BigDecimal monthlyAchievement;

    @NotNull(message = "Category is required")
    private String categoryId;

    @NotNull(message = "Target date is required")
    @Future(message = "Target date must be in the future")
    private LocalDate targetDate;

    private LocalDate startDate;
    private LocalDate endDate;

    public CreateGoalAccountRequestDTO() {
        this.setAccountType(AccountType.GOAL);
    }
}
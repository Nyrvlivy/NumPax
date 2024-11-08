package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class GoalAccount extends Account {
    private BigDecimal targetValue;
    private BigDecimal amountValue;
    private BigDecimal targetTaxRate;
    private BigDecimal monthlyTaxRate;
    private BigDecimal monthlyEstimate;
    private BigDecimal monthlyAchievement;
    private Category category;
    private LocalDate targetDate;
    private LocalDate startDate;
    private LocalDate endDate;

    public GoalAccount() {
    }

    public GoalAccount(String accountId, String name, String description, BigDecimal balance,
                       User userId, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
                       BigDecimal targetValue, BigDecimal amountValue, BigDecimal targetTaxRate,
                       BigDecimal monthlyTaxRate, BigDecimal monthlyEstimate, BigDecimal monthlyAchievement,
                       Category category, LocalDate targetDate, LocalDate startDate, LocalDate endDate) {
        super(accountId, name, description, balance, AccountType.GOAL, isActive, userId, createdAt, updatedAt);
        this.targetValue = targetValue != null ? targetValue : BigDecimal.ZERO;
        this.amountValue = amountValue != null ? amountValue : BigDecimal.ZERO;
        this.targetTaxRate = targetTaxRate != null ? targetTaxRate : BigDecimal.ZERO;
        this.monthlyTaxRate = monthlyTaxRate != null ? monthlyTaxRate : BigDecimal.ZERO;
        this.monthlyEstimate = monthlyEstimate != null ? monthlyEstimate : BigDecimal.ZERO;
        this.monthlyAchievement = monthlyAchievement != null ? monthlyAchievement : BigDecimal.ZERO;
        this.category = category;
        this.targetDate = targetDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

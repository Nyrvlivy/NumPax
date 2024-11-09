package br.com.numpax.infrastructure.entities;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GoalAccount {
    private String accountId;
    private String name;
    private String description;
    private BigDecimal balance;
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
    private boolean isActive;
    private User userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

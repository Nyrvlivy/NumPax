package br.com.numpax.API.V1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.numpax.application.enums.AccountType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class GoalAccountDTO extends AccountDTO {
    private BigDecimal targetAmount;
    private LocalDateTime targetDate;
    private BigDecimal monthlyContribution;
    private Double progressPercentage;
    private String category;
    private String priority;
    private Boolean isCompleted;

    @Builder
    public GoalAccountDTO(String id, String name, String description, BigDecimal balance,
                          Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
                          String userId, BigDecimal targetAmount, LocalDateTime targetDate,
                          BigDecimal monthlyContribution, Double progressPercentage,
                          String category, String priority, Boolean isCompleted) {
        super(id, name, description, balance, AccountType.GOAL, isActive,
            createdAt, updatedAt, userId);
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.monthlyContribution = monthlyContribution;
        this.progressPercentage = progressPercentage;
        this.category = category;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }
}

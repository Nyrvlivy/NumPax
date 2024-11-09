package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SavingsAccount extends Account {
    private LocalDateTime nearestDeadline;
    private LocalDateTime furthestDeadline;
    private LocalDateTime latestDeadline;
    private BigDecimal averageTaxRate;
    private Integer numberOfFixedInvestments;
    private BigDecimal totalMaturityAmount;
    private BigDecimal totalDepositAmount;

    public SavingsAccount(String accountId, String name, String description, BigDecimal balance, boolean isActive, User userId,
                          LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime nearestDeadline,
                          LocalDateTime furthestDeadline, LocalDateTime latestDeadline, BigDecimal averageTaxRate,
                          Integer numberOfFixedInvestments, BigDecimal totalMaturityAmount,
                          BigDecimal totalDepositAmount) {
        super(accountId, name, description, balance, AccountType.SAVINGS, isActive, userId, createdAt, updatedAt);
        this.nearestDeadline = nearestDeadline;
        this.furthestDeadline = furthestDeadline;
        this.latestDeadline = latestDeadline;
        this.averageTaxRate = averageTaxRate  != null ? averageTaxRate : BigDecimal.ZERO;
        this.numberOfFixedInvestments = numberOfFixedInvestments != null ? numberOfFixedInvestments : 0;
        this.totalMaturityAmount = totalMaturityAmount != null ? totalMaturityAmount : BigDecimal.ZERO;
        this.totalDepositAmount = totalDepositAmount != null ? totalDepositAmount : BigDecimal.ZERO;
    }

}

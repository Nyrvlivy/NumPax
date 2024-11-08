package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class SavingsAccount extends Account {
    private LocalDateTime nearestDeadline;
    private LocalDateTime furthestDeadline;
    private LocalDateTime latestDeadline;
    private BigDecimal averageTaxRate;
    private Integer numberOfFixedInvestments;
    private BigDecimal totalMaturityAmount;
    private BigDecimal totalDepositAmount;

    public SavingsAccount() {
    }

    public SavingsAccount(String id, String name, String description, BigDecimal balance, boolean active, User userId,
                          LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime nearestDeadline,
                          LocalDateTime furthestDeadline, LocalDateTime latestDeadline, BigDecimal averageTaxRate,
                          Integer numberOfFixedInvestments, BigDecimal totalMaturityAmount,
                          BigDecimal totalDepositAmount) {
        super(id, name, description, balance, AccountType.SAVINGS, active, userId, createdAt, updatedAt);
        this.nearestDeadline = nearestDeadline;
        this.furthestDeadline = furthestDeadline;
        this.latestDeadline = latestDeadline;
        this.averageTaxRate = averageTaxRate != null ? averageTaxRate : BigDecimal.ZERO;
        this.numberOfFixedInvestments = numberOfFixedInvestments != null ? numberOfFixedInvestments : 0;
        this.totalMaturityAmount = totalMaturityAmount != null ? totalMaturityAmount : BigDecimal.ZERO;
        this.totalDepositAmount = totalDepositAmount != null ? totalDepositAmount : BigDecimal.ZERO;
    }

    public SavingsAccount(String name, String description, AccountType accountType, User userId,
                          LocalDateTime nearestDeadline, LocalDateTime furthestDeadline, LocalDateTime latestDeadline,
                          BigDecimal averageTaxRate, Integer numberOfFixedInvestments, BigDecimal totalMaturityAmount,
                          BigDecimal totalDepositAmount) {
        super(name, description, accountType, userId);
        this.nearestDeadline = nearestDeadline;
        this.furthestDeadline = furthestDeadline;
        this.latestDeadline = latestDeadline;
        this.averageTaxRate = averageTaxRate != null ? averageTaxRate : BigDecimal.ZERO;
        this.numberOfFixedInvestments = numberOfFixedInvestments != null ? numberOfFixedInvestments : 0;
        this.totalMaturityAmount = totalMaturityAmount != null ? totalMaturityAmount : BigDecimal.ZERO;
        this.totalDepositAmount = totalDepositAmount != null ? totalDepositAmount : BigDecimal.ZERO;
    }
}

package br.com.numpax.API.V1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.numpax.application.enums.AccountType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SavingsAccountDTO extends AccountDTO {
    private LocalDateTime nearestDeadline;
    private LocalDateTime furthestDeadline;
    private LocalDateTime latestDeadline;
    private BigDecimal averageTaxRate;
    private Integer numberOfFixedInvestments;
    private BigDecimal totalMaturityAmount;
    private BigDecimal totalDepositAmount;
    
    @Builder
    public SavingsAccountDTO(String id, String name, String description, BigDecimal balance,
                           Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
                           String userId, LocalDateTime nearestDeadline, LocalDateTime furthestDeadline,
                           LocalDateTime latestDeadline, BigDecimal averageTaxRate,
                           Integer numberOfFixedInvestments, BigDecimal totalMaturityAmount,
                           BigDecimal totalDepositAmount) {
        super(id, name, description, balance, AccountType.SAVINGS, isActive, createdAt, updatedAt, userId);
        this.nearestDeadline = nearestDeadline;
        this.furthestDeadline = furthestDeadline;
        this.latestDeadline = latestDeadline;
        this.averageTaxRate = averageTaxRate;
        this.numberOfFixedInvestments = numberOfFixedInvestments;
        this.totalMaturityAmount = totalMaturityAmount;
        this.totalDepositAmount = totalDepositAmount;
    }
} 
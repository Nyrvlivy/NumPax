package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class SavingsAccount extends RegularAccount {
    private LocalDateTime nearestDeadline;
    private LocalDateTime furthestDeadline;
    private LocalDateTime latestDeadline;
    private BigDecimal averageTaxRate;
    private BigDecimal numberOfFixedInvestments;
    private BigDecimal totalMaturityAmount;
    private BigDecimal totalDepositAmount;

    public SavingsAccount(String name, String description, String userId, LocalDateTime nearestDeadline,
                          LocalDateTime furthestDeadline, LocalDateTime latestDeadline, BigDecimal averageTaxRate,
                          BigDecimal numberOfFixedInvestments, BigDecimal totalMaturityAmount, BigDecimal totalDepositAmount) {
        super(name, description, AccountType.SAVINGS, userId);
        this.nearestDeadline = nearestDeadline;
        this.furthestDeadline = furthestDeadline;
        this.latestDeadline = latestDeadline;
        this.averageTaxRate = averageTaxRate != null ? averageTaxRate : BigDecimal.valueOf(0.0);
        this.numberOfFixedInvestments = numberOfFixedInvestments != null ? numberOfFixedInvestments : BigDecimal.valueOf(0.0);
        this.totalMaturityAmount = totalMaturityAmount != null ? totalMaturityAmount : BigDecimal.valueOf(0.0);
        this.totalDepositAmount = totalDepositAmount != null ? totalDepositAmount : BigDecimal.valueOf(0.0);
    }

    // Getters and Setters with updatedAt tracking

    public LocalDateTime getNearestDeadline() {
        return nearestDeadline;
    }

    public void setNearestDeadline(LocalDateTime nearestDeadline) {
        this.nearestDeadline = nearestDeadline;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public LocalDateTime getFurthestDeadline() {
        return furthestDeadline;
    }

    public void setFurthestDeadline(LocalDateTime furthestDeadline) {
        this.furthestDeadline = furthestDeadline;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public LocalDateTime getLatestDeadline() {
        return latestDeadline;
    }

    public void setLatestDeadline(LocalDateTime latestDeadline) {
        this.latestDeadline = latestDeadline;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getAverageTaxRate() {
        return averageTaxRate;
    }

    public void setAverageTaxRate(BigDecimal averageTaxRate) {
        this.averageTaxRate = averageTaxRate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getNumberOfFixedInvestments() {
        return numberOfFixedInvestments;
    }

    public void setNumberOfFixedInvestments(BigDecimal numberOfFixedInvestments) {
        this.numberOfFixedInvestments = numberOfFixedInvestments;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getTotalMaturityAmount() {
        return totalMaturityAmount;
    }

    public void setTotalMaturityAmount(BigDecimal totalMaturityAmount) {
        this.totalMaturityAmount = totalMaturityAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getTotalDepositAmount() {
        return totalDepositAmount;
    }

    public void setTotalDepositAmount(BigDecimal totalDepositAmount) {
        this.totalDepositAmount = totalDepositAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }
}

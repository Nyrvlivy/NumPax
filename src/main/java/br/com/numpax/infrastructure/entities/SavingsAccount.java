package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;

import java.time.LocalDateTime;

public class SavingsAccount extends RegularAccount {
    private LocalDateTime nearestDeadline;
    private LocalDateTime furthestDeadline;
    private LocalDateTime latestDeadline;
    private Double averageTaxRate;
    private Double numberOfFixedInvestments;
    private Double totalMaturityAmount;
    private Double totalDepositAmount;

    public SavingsAccount(String name, String description, String userId, LocalDateTime nearestDeadline,
                          LocalDateTime furthestDeadline, LocalDateTime latestDeadline, Double averageTaxRate,
                          Double numberOfFixedInvestments, Double totalMaturityAmount, Double totalDepositAmount) {
        super(name, description, AccountType.SAVINGS, userId);
        this.nearestDeadline = nearestDeadline;
        this.furthestDeadline = furthestDeadline;
        this.latestDeadline = latestDeadline;
        this.averageTaxRate = averageTaxRate != null ? averageTaxRate : 0.0;
        this.numberOfFixedInvestments = numberOfFixedInvestments != null ? numberOfFixedInvestments : 0.0;
        this.totalMaturityAmount = totalMaturityAmount != null ? totalMaturityAmount : 0.0;
        this.totalDepositAmount = totalDepositAmount != null ? totalDepositAmount : 0.0;
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

    public Double getAverageTaxRate() {
        return averageTaxRate;
    }

    public void setAverageTaxRate(Double averageTaxRate) {
        this.averageTaxRate = averageTaxRate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getNumberOfFixedInvestments() {
        return numberOfFixedInvestments;
    }

    public void setNumberOfFixedInvestments(Double numberOfFixedInvestments) {
        this.numberOfFixedInvestments = numberOfFixedInvestments;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getTotalMaturityAmount() {
        return totalMaturityAmount;
    }

    public void setTotalMaturityAmount(Double totalMaturityAmount) {
        this.totalMaturityAmount = totalMaturityAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getTotalDepositAmount() {
        return totalDepositAmount;
    }

    public void setTotalDepositAmount(Double totalDepositAmount) {
        this.totalDepositAmount = totalDepositAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }
}

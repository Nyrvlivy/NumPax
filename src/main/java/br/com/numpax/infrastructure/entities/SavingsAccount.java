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

    public SavingsAccount(String name, String description, User user, AccountType accountType, LocalDateTime nearestDeadline, LocalDateTime furthestDeadline, LocalDateTime latestDeadline, Double averageTaxRate, Double numberOfFixedInvestments, Double totalMaturityAmount, Double totalDepositAmount) {
        super(name, description, user, accountType);
        this.nearestDeadline = nearestDeadline;
        this.furthestDeadline = furthestDeadline;
        this.latestDeadline = latestDeadline;
        this.averageTaxRate = averageTaxRate;
        this.numberOfFixedInvestments = numberOfFixedInvestments;
        this.totalMaturityAmount = totalMaturityAmount;
        this.totalDepositAmount = totalDepositAmount;
    }

    public LocalDateTime getNearestDeadline() {
        return nearestDeadline;
    }

    public LocalDateTime getFurthestDeadline() {
        return furthestDeadline;
    }

    public LocalDateTime getLatestDeadline() {
        return latestDeadline;
    }

    public Double getAverageTaxRate() {
        return averageTaxRate;
    }

    public Double getNumberOfFixedInvestments() {
        return numberOfFixedInvestments;
    }

    public Double getTotalMaturityAmount() {
        return totalMaturityAmount;
    }

    public Double getTotalDepositAmount() {
        return totalDepositAmount;
    }
}

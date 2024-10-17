package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GoalAccount extends Account {
    private Double targetValue;
    private Double amountValue;
    private Double targetTaxRate;
    private Double monthlyTaxRate;
    private Double monthlyEstimate;
    private Double monthlyAchievement;
    private Category category;
    private LocalDate targetDate;
    private LocalDate startDate;
    private LocalDate endDate;

    public GoalAccount(String name, String description, AccountType accountType, String userId, Double targetValue, Double targetTaxRate, Double monthlyTaxRate, Double monthlyEstimate, Double monthlyAchievement, Category category, LocalDate targetDate, LocalDate startDate, LocalDate endDate) {
        super(name, description, accountType, userId);
        this.targetValue = targetValue;
        this.amountValue = 0.0;
        this.targetTaxRate = targetTaxRate;
        this.monthlyTaxRate = monthlyTaxRate;
        this.monthlyEstimate = monthlyEstimate;
        this.monthlyAchievement = monthlyAchievement;
        this.category = category;
        this.targetDate = targetDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Double targetValue) {
        this.targetValue = targetValue;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getAmountValue() {
        return amountValue;
    }

    public void setAmountValue(Double amountValue) {
        this.amountValue = amountValue;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getTargetTaxRate() {
        return targetTaxRate;
    }

    public void setTargetTaxRate(Double targetTaxRate) {
        this.targetTaxRate = targetTaxRate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getMonthlyTaxRate() {
        return monthlyTaxRate;
    }

    public void setMonthlyTaxRate(Double monthlyTaxRate) {
        this.monthlyTaxRate = monthlyTaxRate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getMonthlyEstimate() {
        return monthlyEstimate;
    }

    public void setMonthlyEstimate(Double monthlyEstimate) {
        this.monthlyEstimate = monthlyEstimate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getMonthlyAchievement() {
        return monthlyAchievement;
    }

    public void setMonthlyAchievement(Double monthlyAchievement) {
        this.monthlyAchievement = monthlyAchievement;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void viewGoalProgress() {
        System.out.println("Progresso do objetivo: " + amountValue + " de " + targetValue);
    }
}

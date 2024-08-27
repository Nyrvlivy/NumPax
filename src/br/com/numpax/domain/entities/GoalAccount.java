package br.com.numpax.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.numpax.domain.enums.AccountType;

public class GoalAccount extends RegularAccount{
    private Double targetValue;         // Valor alvo
    private Double amountValue;         // Valor acumulado
    private Double targetTaxRate;       // Juros no valor alvo
    private Double monthlyTaxRate;      // Juros mensal no valor acumulado
    private Double monthlyEstimate;     // Estimativa de entrada mensal
    private Double monthlyAchievement;  // Entrada mensal alvo
    private Category category;          // Categoria
    private LocalDate targetDate;       // Data alvo
    private LocalDate startDate;        // Data de início manual (se o usuário quiser começar antes da data atual)
    private LocalDate endDate;          // Data de conquista do objetivo

    public GoalAccount(String name, String description, User user, AccountType accountType, Double targetValue, Double targetTaxRate, Double monthlyTaxRate, Double monthlyEstimate, Double monthlyAchievement, Category category, LocalDate targetDate, LocalDate startDate, LocalDate endDate) {
        super(name, description, user, accountType);
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

    public Double getTargetValue() { return targetValue; }

    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; this.setUpdatedAt(LocalDateTime.now());}

    public Double getAmountValue() { return amountValue; }

    public void setAmountValue(Double amountValue) { this.amountValue = amountValue; this.setUpdatedAt(LocalDateTime.now());}

    public Double getTargetTaxRate() { return targetTaxRate; }

    public void setTargetTaxRate(Double targetTaxRate) { this.targetTaxRate = targetTaxRate; this.setUpdatedAt(LocalDateTime.now());}

    public Double getMonthlyTaxRate() { return monthlyTaxRate; }

    public void setMonthlyTaxRate(Double monthlyTaxRate) { this.monthlyTaxRate = monthlyTaxRate; this.setUpdatedAt(LocalDateTime.now());}

    public Double getMonthlyEstimate() { return monthlyEstimate; }

    public void setMonthlyEstimate(Double monthlyEstimate) { this.monthlyEstimate = monthlyEstimate; this.setUpdatedAt(LocalDateTime.now());}

    public Double getMonthlyAchievement() { return monthlyAchievement; }

    public void setMonthlyAchievement(Double monthlyAchievement) {
        this.monthlyAchievement = monthlyAchievement;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; this.setUpdatedAt(LocalDateTime.now());}

    public LocalDate getTargetDate() { return targetDate; }

    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; this.setUpdatedAt(LocalDateTime.now());}

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; this.setUpdatedAt(LocalDateTime.now());}

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; this.setUpdatedAt(LocalDateTime.now());}
}

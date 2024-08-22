package br.com.numpax.domain.entities;

import java.time.LocalDate;

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
    private LocalDate conclusionDate;   // Data de conquista do objetivo

    public GoalAccount(String name, String description, User user, String type, Double targetValue, Double targetTaxRate, Double monthlyTaxRate, Double monthlyEstimate, Double monthlyAchievement, Category category, LocalDate targetDate, LocalDate startDate, LocalDate conclusionDate) {
        super(name, description, user, type);
        this.targetValue = targetValue;
        this.amountValue = 0.0;
        this.targetTaxRate = targetTaxRate;
        this.monthlyTaxRate = monthlyTaxRate;
        this.monthlyEstimate = monthlyEstimate;
        this.monthlyAchievement = monthlyAchievement;
        this.category = category;
        this.targetDate = targetDate;
        this.startDate = startDate;
        this.conclusionDate = conclusionDate;
    }
}

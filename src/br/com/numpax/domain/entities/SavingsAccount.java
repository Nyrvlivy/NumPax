package br.com.numpax.domain.entities;

import java.time.LocalDateTime;

import br.com.numpax.domain.enums.AccountType;

public class SavingsAccount extends RegularAccount {
    private LocalDateTime nearestDeadline;    // Data de vencimento mais próxima
    private LocalDateTime furthestDeadline;   // Data de vencimento mais distante
    private LocalDateTime lastestDeadline;    // Data de vencimento mais recente
    private Double averageTaxRate;            // Taxa média de juros das contas de poupança
    private Double numberOfFixedInvestments;  // Total de rendas diferentes (mudar o nome)
    private Double totalMaturityAmount;       // Valor total esperado de todos os investimentos
    private Double totalDepositAmount;        // Valor total depositado dos investimentos

    public SavingsAccount(String name, String description, User user, AccountType accountType, LocalDateTime nearestDeadline, LocalDateTime furthestDeadline, LocalDateTime lastestDeadline, Double averageTaxRate, Double numberOfFixedInvestments, Double totalMaturityAmount, Double totalDepositAmount) {
        super(name, description, user, accountType);
        this.nearestDeadline = nearestDeadline;
        this.furthestDeadline = furthestDeadline;
        this.lastestDeadline = lastestDeadline;
        this.averageTaxRate = averageTaxRate;
        this.numberOfFixedInvestments = numberOfFixedInvestments;
        this.totalMaturityAmount = totalMaturityAmount;
        this.totalDepositAmount = totalDepositAmount;
    }

    public LocalDateTime getNearestDeadline() { return nearestDeadline; }

    public LocalDateTime getFurthestDeadline() { return furthestDeadline; }

    public LocalDateTime getLastestDeadline() { return lastestDeadline; }

    public Double getAverageTaxRate() { return averageTaxRate; }

    public Double getNumberOfFixedInvestments() { return numberOfFixedInvestments; }

    public Double getTotalMaturityAmount() { return totalMaturityAmount; }

    public Double getTotalDepositAmount() { return totalDepositAmount; }

    // OK / Resolver lógica sobre transação na transacão (ficar colocando mais dinheiro em cada)
}

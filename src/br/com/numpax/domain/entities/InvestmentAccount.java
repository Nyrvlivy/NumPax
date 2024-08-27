package br.com.numpax.domain.entities;

import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.RiskLevelType;

public class InvestmentAccount extends RegularAccount {
    private Double totalInvestedAmount;   // Valor total investido (entradas) ao longo do tempo (sem lucro nem saídas)
    private Double totalProfit;           // Lucro total
    private Double totalCurrentAmount;    // Valor total investido atualmente (com lucro e saídas)
    private Double totalWithdrawnAmount;  // Valor total sacado ao longo do tempo
    private Double numberOfWithdrawals;   // Número de saques
    private Double numberOfEntries;       // Número de entradas
    private Double numberOfAssets;        // Número de ativos diferentes (ações, títulos, etc)
    private Double averagePurchasePrice;  // Preço médio de compra dos ativos
    private Double totalGainLoss;         // Ganho ou perda total acumulado com base na diferença entre os valores de compra e venda dos ativos
    private Double totalDividendYield;    // Dividendos totais
    private RiskLevelType riskLevelType;  // Nível de risco (aqui será a média, então aplicar a lógica com base nos riscos das transações)
    // Enum riskLevel = VERY LOW, LOW, MEDIUM, HIGH, VERY_HIGH ou logica por numeros

    public InvestmentAccount(String name, String description, User user, AccountType accountType, Double totalInvestedAmount, Double totalProfit, Double totalCurrentAmount, Double totalWithdrawnAmount, Double numberOfWithdrawals, Double numberOfEntries, Double numberOfAssets, Double averagePurchasePrice, Double totalGainLoss, Double totalDividendYield, RiskLevelType riskLevelType) {
        super(name, description, user, accountType);
        this.totalInvestedAmount = totalInvestedAmount;
        this.totalProfit = totalProfit;
        this.totalCurrentAmount = totalCurrentAmount;
        this.totalWithdrawnAmount = totalWithdrawnAmount;
        this.numberOfWithdrawals = numberOfWithdrawals;
        this.numberOfEntries = numberOfEntries;
        this.numberOfAssets = numberOfAssets;
        this.averagePurchasePrice = averagePurchasePrice;
        this.totalGainLoss = totalGainLoss;
        this.totalDividendYield = totalDividendYield;
        this.riskLevelType = riskLevelType;
    }

    public Double getTotalInvestedAmount() { return totalInvestedAmount; }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public Double getTotalCurrentAmount() {
        return totalCurrentAmount;
    }

    public Double getTotalWithdrawnAmount() {
        return totalWithdrawnAmount;
    }

    public Double getNumberOfWithdrawals() {
        return numberOfWithdrawals;
    }

    public Double getNumberOfEntries() {
        return numberOfEntries;
    }

    public Double getNumberOfAssets() {
        return numberOfAssets;
    }

    public Double getAveragePurchasePrice() {
        return averagePurchasePrice;
    }

    public Double getTotalGainLoss() {
        return totalGainLoss;
    }

    public Double getTotalDividendYield() {
        return totalDividendYield;
    }

    public RiskLevelType getRiskLevelType() {
        return riskLevelType;
    }

    public void setRiskLevelType(RiskLevelType riskLevelType) {
        this.riskLevelType = riskLevelType;
    }
}

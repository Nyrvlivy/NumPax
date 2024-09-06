package br.com.numpax.domain.entities;

import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.RiskLevelType;

import java.time.LocalDateTime;

public class InvestmentAccount extends RegularAccount {
    private Double totalInvestedAmount;
    private Double totalProfit;
    private Double totalCurrentAmount;
    private Double totalWithdrawnAmount;
    private Double numberOfWithdrawals;
    private Double numberOfEntries;
    private Double numberOfAssets;
    private Double averagePurchasePrice;
    private Double totalGainLoss;
    private Double totalDividendYield;
    private RiskLevelType riskLevelType;
    private AccountType.InvestmentSubtypeAccount investmentSubtypeAccount;

    public InvestmentAccount(String name, String description, User user, AccountType accountType, Double totalInvestedAmount, Double totalProfit, Double totalCurrentAmount, Double totalWithdrawnAmount, Double numberOfWithdrawals, Double numberOfEntries, Double numberOfAssets, Double averagePurchasePrice, Double totalGainLoss, Double totalDividendYield, RiskLevelType riskLevelType, AccountType.InvestmentSubtypeAccount investmentSubtypeAccount) {
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
        this.investmentSubtypeAccount = investmentSubtypeAccount;
    }

    public Double getTotalInvestedAmount() {
        return totalInvestedAmount;
    }

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
        this.setUpdatedAt(LocalDateTime.now());
    }

    public AccountType.InvestmentSubtypeAccount getInvestmentSubtypeAccount() {
        return investmentSubtypeAccount;
    }

    public void setInvestmentSubtypeAccount(AccountType.InvestmentSubtypeAccount investmentSubtypeAccount) {
        this.investmentSubtypeAccount = investmentSubtypeAccount;
    }

    public void viewInvestmentStatement() {
        System.out.println("Extrato de Investimentos:");
        System.out.println("Saldo atual: " + getBalance());
        System.out.println("Nível de risco: " + riskLevelType);
        System.out.println("Total investido: " + totalInvestedAmount);
        System.out.println("Total de lucro: " + totalProfit);
        System.out.println("Total atual: " + totalCurrentAmount);
        System.out.println("Total retirado: " + totalWithdrawnAmount);
        System.out.println("Número de retiradas: " + numberOfWithdrawals);
        System.out.println("Número de entradas: " + numberOfEntries);
        System.out.println("Número de ativos: " + numberOfAssets);
        System.out.println("Preço médio de compra: " + averagePurchasePrice);
        System.out.println("Ganho/Perda total: " + totalGainLoss);
        System.out.println("Dividend Yield total: " + totalDividendYield);
    }
}

package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.InvestmentSubtype;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvestmentAccount extends Account {
    private InvestmentSubtype investmentSubtype;
    private BigDecimal totalInvestedAmount;
    private BigDecimal totalProfit;
    private BigDecimal totalCurrentAmount;
    private BigDecimal totalWithdrawnAmount;
    private int numberOfWithdrawals;
    private int numberOfEntries;
    private int numberOfAssets;
    private BigDecimal averagePurchasePrice;
    private BigDecimal totalGainLoss;
    private BigDecimal totalDividendYield;
    private String riskLevelType;

    public InvestmentAccount(String name, String description, String userId, InvestmentSubtype investmentSubtype) {
        super(name, description, AccountType.INVESTMENT, userId);
        this.investmentSubtype = investmentSubtype;
        this.totalInvestedAmount = BigDecimal.ZERO;
        this.totalProfit = BigDecimal.ZERO;
        this.totalCurrentAmount = BigDecimal.ZERO;
        this.totalWithdrawnAmount = BigDecimal.ZERO;
        this.numberOfWithdrawals = 0;
        this.numberOfEntries = 0;
        this.numberOfAssets = 0;
        this.averagePurchasePrice = BigDecimal.ZERO;
        this.totalGainLoss = BigDecimal.ZERO;
        this.totalDividendYield = BigDecimal.ZERO;
        this.riskLevelType = "LOW";
    }

    public InvestmentSubtype getInvestmentSubtype() {
        return investmentSubtype;
    }

    public void setInvestmentSubtype(InvestmentSubtype investmentSubtype) {
        this.investmentSubtype = investmentSubtype;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getTotalInvestedAmount() {
        return totalInvestedAmount;
    }

    public void setTotalInvestedAmount(BigDecimal totalInvestedAmount) {
        this.totalInvestedAmount = totalInvestedAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getTotalCurrentAmount() {
        return totalCurrentAmount;
    }

    public void setTotalCurrentAmount(BigDecimal totalCurrentAmount) {
        this.totalCurrentAmount = totalCurrentAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getTotalWithdrawnAmount() {
        return totalWithdrawnAmount;
    }

    public void setTotalWithdrawnAmount(BigDecimal totalWithdrawnAmount) {
        this.totalWithdrawnAmount = totalWithdrawnAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public int getNumberOfWithdrawals() {
        return numberOfWithdrawals;
    }

    public void setNumberOfWithdrawals(int numberOfWithdrawals) {
        this.numberOfWithdrawals = numberOfWithdrawals;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setNumberOfEntries(int numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public int getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(int numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getAveragePurchasePrice() {
        return averagePurchasePrice;
    }

    public void setAveragePurchasePrice(BigDecimal averagePurchasePrice) {
        this.averagePurchasePrice = averagePurchasePrice;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getTotalGainLoss() {
        return totalGainLoss;
    }

    public void setTotalGainLoss(BigDecimal totalGainLoss) {
        this.totalGainLoss = totalGainLoss;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getTotalDividendYield() {
        return totalDividendYield;
    }

    public void setTotalDividendYield(BigDecimal totalDividendYield) {
        this.totalDividendYield = totalDividendYield;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getRiskLevelType() {
        return riskLevelType;
    }

    public void setRiskLevelType(String riskLevelType) {
        this.riskLevelType = riskLevelType;
        this.setUpdatedAt(LocalDateTime.now());
    }

}

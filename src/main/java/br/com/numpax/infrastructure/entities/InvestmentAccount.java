package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.InvestmentSubtype;
import br.com.numpax.application.enums.RiskLevelType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InvestmentAccount extends Account {
    private BigDecimal totalInvestedAmount;
    private BigDecimal totalProfit;
    private BigDecimal totalCurrentAmount;
    private BigDecimal totalWithdrawnAmount;
    private BigDecimal numberOfWithdrawals;
    private BigDecimal numberOfEntries;
    private BigDecimal numberOfAssets;
    private BigDecimal averagePurchasePrice;
    private BigDecimal totalGainLoss;
    private BigDecimal totalDividendYield;
    private RiskLevelType riskLevelType;
    private InvestmentSubtype investmentSubtype;

    public InvestmentAccount() {
    }

    public InvestmentAccount(String accountId, String name, String description, BigDecimal balance,
                             User userId, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
                             BigDecimal totalInvestedAmount, BigDecimal totalProfit, BigDecimal totalCurrentAmount,
                             BigDecimal totalWithdrawnAmount, BigDecimal numberOfWithdrawals,
                             BigDecimal numberOfEntries, BigDecimal numberOfAssets, BigDecimal averagePurchasePrice,
                             BigDecimal totalGainLoss, BigDecimal totalDividendYield,
                             List<String> riskLevelType, List<String> investmentSubtype) {
        super(accountId, name, description, balance, AccountType.INVESTMENT, isActive, userId, createdAt, updatedAt);
        this.totalInvestedAmount = totalInvestedAmount != null ? totalInvestedAmount : BigDecimal.ZERO;
        this.totalProfit = totalProfit != null ? totalProfit : BigDecimal.ZERO;
        this.totalCurrentAmount = totalCurrentAmount != null ? totalCurrentAmount : BigDecimal.ZERO;
        this.totalWithdrawnAmount = totalWithdrawnAmount != null ? totalWithdrawnAmount : BigDecimal.ZERO;
        this.numberOfWithdrawals = numberOfWithdrawals != null ? numberOfWithdrawals : BigDecimal.ZERO;
        this.numberOfEntries = numberOfEntries != null ? numberOfEntries : BigDecimal.ZERO;
        this.numberOfAssets = numberOfAssets != null ? numberOfAssets : BigDecimal.ZERO;
        this.averagePurchasePrice = averagePurchasePrice != null ? averagePurchasePrice : BigDecimal.ZERO;
        this.totalGainLoss = totalGainLoss != null ? totalGainLoss : BigDecimal.ZERO;
        this.totalDividendYield = totalDividendYield != null ? totalDividendYield : BigDecimal.ZERO;
        this.riskLevelType = riskLevelType != null ? RiskLevelType.valueOf(riskLevelType.getFirst()) : null;
        this.investmentSubtype = investmentSubtype != null ? InvestmentSubtype.valueOf(investmentSubtype.getFirst()) : null;
    }

}

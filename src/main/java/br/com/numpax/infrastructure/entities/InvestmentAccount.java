package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.RiskLevelType;
import br.com.numpax.application.enums.InvestmentSubtype;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvestmentAccount {
    private String accountId;
    private String name;
    private String description;
    private BigDecimal balance;
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
    private boolean isActive;
    private User userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

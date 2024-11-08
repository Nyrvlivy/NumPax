package br.com.numpax.application.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInvestmentAccountRequestDTO extends CreateAccountRequestDTO {
    @NotNull(message = "Total invested amount is required")
    @PositiveOrZero(message = "Total invested amount must be zero or positive")
    private BigDecimal totalInvestedAmount;
    
    private BigDecimal totalProfit;
    private BigDecimal totalCurrentAmount;
    private BigDecimal totalWithdrawnAmount;
    private Integer numberOfWithdrawals;
    private Integer numberOfEntries;
    private Integer numberOfAssets;
    private BigDecimal averagePurchasePrice;
    private BigDecimal totalGainLoss;
    private BigDecimal totalDividendYield;
    
    @NotNull(message = "Risk level type is required")
    private RiskLevelType riskLevelType;
    
    @NotNull(message = "Investment subtype is required")
    private InvestmentSubtype investmentSubtype;
} 
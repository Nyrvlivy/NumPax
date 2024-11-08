package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.InvestmentSubtype;
import br.com.numpax.application.enums.RiskLevelType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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

    public CreateInvestmentAccountRequestDTO() {
        this.setAccountType(AccountType.INVESTMENT);
    }
} 
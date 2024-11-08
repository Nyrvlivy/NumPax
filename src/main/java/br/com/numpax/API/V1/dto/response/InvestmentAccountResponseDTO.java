package br.com.numpax.API.V1.dto.response;

import java.math.BigDecimal;

import br.com.numpax.application.enums.InvestmentSubtype;
import br.com.numpax.application.enums.RiskLevelType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestmentAccountResponseDTO extends AccountResponseDTO {
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
    private RiskLevelType riskLevelType;
    private InvestmentSubtype investmentSubtype;
} 
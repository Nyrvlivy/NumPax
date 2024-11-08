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
public class InvestmentAccountRequestDTO extends AccountRequestDTO {

//    private BigDecimal totalInvestedAmount;
//    private BigDecimal totalProfit;
//    private BigDecimal totalCurrentAmount;
//    private BigDecimal totalWithdrawnAmount;
//    private Integer numberOfWithdrawals;
//    private Integer numberOfEntries;
//    private Integer numberOfAssets;
//    private BigDecimal averagePurchasePrice;
//    private BigDecimal totalGainLoss;
//    private BigDecimal totalDividendYield;
//    private RiskLevelType riskLevelType;
//    private InvestmentSubtype investmentSubtype;

    public InvestmentAccountRequestDTO() {
        this.setAccountType(AccountType.INVESTMENT);
    }
} 
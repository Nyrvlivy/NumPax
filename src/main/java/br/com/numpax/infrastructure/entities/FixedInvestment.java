package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.FixedInvestmentType;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FixedInvestment extends Transaction {
    private FixedInvestmentType fixedInvestmentType;
    private LocalDate investmentDate;
    private LocalDate expirationDate;
    private String institution;
    private BigDecimal redeemValue;
    private LocalDate redeemDate;
    private Integer liquidityPeriod;
    private BigDecimal netGainLoss;
}

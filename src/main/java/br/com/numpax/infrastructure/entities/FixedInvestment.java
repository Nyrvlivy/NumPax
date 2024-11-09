package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.FixedInvestmentType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FixedInvestment {
    private String transactionId;
    private FixedInvestmentType fixedInvestmentType;
    private LocalDate investmentDate;
    private LocalDate expirationDate;
    private String institution;
    private BigDecimal redeemValue;
    private LocalDate redeemDate;
    private Integer liquidityPeriod;
    private BigDecimal netGainLoss;
    private Transaction transaction;
}

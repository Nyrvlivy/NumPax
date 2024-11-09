package br.com.numpax.infrastructure.entities;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FixedInvestmentTaxRate {
    private String taxRateId;
    private String transactionId;
    private BigDecimal taxRate;
    private FixedInvestment fixedInvestment;
} 
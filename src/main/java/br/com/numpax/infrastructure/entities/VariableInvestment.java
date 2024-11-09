package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.VariableInvestmentType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VariableInvestment {
    private String transactionId;
    private VariableInvestmentType variableInvestmentType;
    private String broker;
    private LocalDate purchaseDate;
    private LocalDate expirationDate;
    private String assetCode;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private LocalDate saleDate;
    private BigDecimal salePrice;
    private Transaction transaction;
}

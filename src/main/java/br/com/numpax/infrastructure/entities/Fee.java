package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.FeeType;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class Fee {
    private String feeId;
    private String transactionId;
    private FeeType feeType;
    private BigDecimal feeAmount;
    private Transaction transaction;
} 
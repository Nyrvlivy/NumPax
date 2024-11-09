package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.NatureOfTransactionType;
import br.com.numpax.application.enums.RepeatableType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private String transactionId;
    private String code;
    private boolean isEffective;
    private String name;
    private String description;
    private BigDecimal amount;
    private String categoryId;
    private String accountId;
    private NatureOfTransactionType natureOfTransaction;
    private String receiver;
    private String sender;
    private LocalDate transactionDate;
    private boolean isRepeatable;
    private RepeatableType repeatableType;
    private String note;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

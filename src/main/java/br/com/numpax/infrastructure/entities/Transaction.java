package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private String transactionId;
    private String code;
    private String name;
    private String description;
    private BigDecimal amount;
    private NatureOfTransaction natureOfTransaction;
    private String receiver;
    private String sender;
    private LocalDate transactionDate;
    private boolean isRepeatable;
    private RepeatableType repeatableType;
    private String note;
    private boolean isActive;
    private boolean isEffective;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime effectiveDate;
    
    // Relacionamentos
    private Account account;
    private Category category;

    // Construtor para herança (usado por FixedInvestment e VariableInvestment)
    protected Transaction(String code, String name, String description, BigDecimal amount, 
                      Category category, Account account, NatureOfTransaction natureOfTransaction, 
                      String receiver, String sender, LocalDate transactionDate, 
                      RepeatableType repeatableType, String note) {
        this.transactionId = UUID.randomUUID().toString();
        this.code = code;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.natureOfTransaction = natureOfTransaction;
        this.receiver = receiver;
        this.sender = sender;
        this.transactionDate = transactionDate;
        this.repeatableType = repeatableType;
        this.note = note;
        this.isActive = true;
        this.isEffective = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setDate() {
        LocalDateTime now = LocalDateTime.now();
        if (this.createdAt == null) {
            this.createdAt = now;
        }
        this.updatedAt = now;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        setDate();
    }
}

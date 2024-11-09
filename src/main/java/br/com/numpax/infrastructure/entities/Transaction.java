package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String transactionId;
    private String code;
    private String name;
    private String description;
    private BigDecimal amount;
    private Category category;
    private Account account;
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

    public Transaction(String code, String name, String description, BigDecimal amount, Category category, Account account, NatureOfTransaction natureOfTransaction, String receiver, String sender, LocalDate transactionDate, boolean isRepeatable, RepeatableType repeatableType, String note, boolean isActive, boolean isEffective, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.transactionId = UUID.randomUUID().toString();
        this.code = code;
        this.isEffective = isEffective;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.natureOfTransaction = natureOfTransaction;
        this.receiver = receiver;
        this.sender = sender;
        this.transactionDate = transactionDate;
        this.isRepeatable = isRepeatable;
        this.repeatableType = repeatableType;
        this.note = note;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}

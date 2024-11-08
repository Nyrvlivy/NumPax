package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Account {
    private String accountId;
    private String name;
    private String description;
    private BigDecimal balance;
    private AccountType accountType;
    private boolean isActive;
    private User userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Account() {
    }

    public Account(String accountId, String name, String description, BigDecimal balance, AccountType accountType,
                   boolean isActive, User userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.accountId = accountId != null ? accountId : UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
        this.accountType = accountType;
        this.isActive = isActive;
        this.userId = userId;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }

    public Account(String name, String description, AccountType accountType, User userId) {
        this(null, name, description, BigDecimal.ZERO, accountType, true, userId, null, null);
    }

    public void setIsActive(boolean isActive) {
    }
}

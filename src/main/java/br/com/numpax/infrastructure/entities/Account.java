package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Account {
    private String id;
    private String name;
    private String description;
    private BigDecimal balance;
    private AccountType accountType;
    private String userId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Account(String name, String description, AccountType accountType, String userId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.balance = BigDecimal.ZERO;
        this.accountType = accountType;
        this.userId = userId;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getBalance() { return balance; }
    public AccountType getAccountType() { return accountType; }
    public String getUserId() { return userId; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setActive(boolean active) { this.active = active; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

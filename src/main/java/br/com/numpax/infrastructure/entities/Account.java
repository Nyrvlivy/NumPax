package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    private final String id;
    private String name;
    private String description;
    private BigDecimal balance;
    private AccountType accountType;
    private Boolean isActive;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;

    public Account(String name, String description, AccountType accountType, String userId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.balance = BigDecimal.ZERO;
        this.accountType = accountType;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userId = userId;
    }

    public Account(String id, String name, String description, BigDecimal balance, AccountType accountType, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, String userId) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
        this.accountType = accountType;
        this.isActive = isActive != null ? isActive : true;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
        this.userId = userId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; this.updatedAt = LocalDateTime.now(); }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; this.updatedAt = LocalDateTime.now(); }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; this.updatedAt = LocalDateTime.now(); }
    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; this.updatedAt = LocalDateTime.now(); }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; this.updatedAt = LocalDateTime.now(); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; this.updatedAt = LocalDateTime.now(); }

    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

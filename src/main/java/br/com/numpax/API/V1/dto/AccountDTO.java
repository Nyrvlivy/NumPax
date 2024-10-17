package br.com.numpax.API.V1.dto;

import br.com.numpax.application.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDTO {
    private String id;
    private String name;
    private String description;
    private BigDecimal balance;
    private AccountType accountType;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;

    public AccountDTO(String id, String name, String description, BigDecimal balance, AccountType accountType, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, String userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.balance = balance;
        this.accountType = accountType;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getBalance() { return balance; }
    public AccountType getAccountType() { return accountType; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getUserId() { return userId; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setUserId(String userId) { this.userId = userId; }

    @Override
    public String toString() {
        return String.format(
            "AccountDTO{\n" +
                "  id: '%s',\n" +
                "  name: '%s',\n" +
                "  description: '%s',\n" +
                "  balance: %s,\n" +
                "  accountType: %s,\n" +
                "  isActive: %s,\n" +
                "  createdAt: %s,\n" +
                "  updatedAt: %s,\n" +
                "  userId: '%s'\n" +
                "}",
            id, name, description, balance, accountType, isActive, createdAt, updatedAt, userId
        );
    }
}

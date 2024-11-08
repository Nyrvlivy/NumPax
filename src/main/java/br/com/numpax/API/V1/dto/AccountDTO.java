package br.com.numpax.API.V1.dto;

import br.com.numpax.application.enums.AccountType;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuperBuilder
public abstract class AccountDTO {
    private String id;
    private String name;
    private String description;
    private BigDecimal balance;
    private AccountType accountType;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;

    protected AccountDTO(String id, String name, String description, BigDecimal balance, AccountType accountType, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, String userId) {
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

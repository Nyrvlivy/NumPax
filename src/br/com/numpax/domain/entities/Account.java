package br.com.numpax.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    private final String id;
    private String name;                      // Nome da conta
    private String description;               // Descrição da conta
    private BigDecimal balance;               // Saldo -> começa com 0.0
    private boolean isActive;                 // Ativa ou Inativa
    private User user;                        // Usuário associado
    private final LocalDateTime createdAt;    // Data de criação
    private LocalDateTime updatedAt;          // Data de atualização

    public Account(String name, String description, User user) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.balance = BigDecimal.valueOf(0.0);
        this.isActive = true;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setUser(User user) {
        this.user = user;
        this.updatedAt = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean getIsActive() { return isActive; }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
        this.updatedAt = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) {
        this.isActive = active;
    }
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 && this.balance.compareTo(amount) >= 0) {
            this.balance = this.balance.subtract(amount);
            this.updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Insufficient balance or invalid amount.");
        }
    }

    
}



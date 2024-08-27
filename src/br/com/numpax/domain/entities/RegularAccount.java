package br.com.numpax.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.numpax.domain.enums.AccountType;

public class RegularAccount extends Account{
    private AccountType accountType;    // Tipo de conta -> Corrente, Poupança, Investimento ou Objetivo
                            // Pode ser um enum: private AccountType accountType;

    public RegularAccount(String name, String description, User user, AccountType accountType) {
        super(name, description, user);
        this.accountType = accountType;
    }

    public AccountType getType() {
        return accountType;
    }

    public void setType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= super.getBalance().doubleValue()) {
            super.setBalance(super.getBalance().subtract(BigDecimal.valueOf(amount)));
            super.setUpdatedAt(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("Insufficient balance or invalid amount");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            super.setBalance(super.getBalance().add(BigDecimal.valueOf(amount)));
            super.setUpdatedAt(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    public void apply(double amount) {
        BigDecimal bigAmount = BigDecimal.valueOf(amount);

        if (amount < 0) {  // Lógica para saque
            BigDecimal withdrawAmount = bigAmount.negate();
            if (withdrawAmount.compareTo(super.getBalance()) <= 0) {
                super.setBalance(super.getBalance().subtract(withdrawAmount));
                super.setUpdatedAt(LocalDateTime.now());
            } else {
                throw new IllegalArgumentException("Insufficient balance for withdrawal");
            }
        } else if (amount > 0) {  // Lógica para depósito
            super.setBalance(super.getBalance().add(bigAmount));
            super.setUpdatedAt(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("Amount must be non-zero");
        }
    }
}

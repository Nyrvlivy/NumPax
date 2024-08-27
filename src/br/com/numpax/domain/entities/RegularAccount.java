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

}

package br.com.numpax.infrastructure.entities;

import br.com.numpax.API.V1.exceptions.InsufficientFundsException;
import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegularAccount extends Account {

    public RegularAccount() {
    }

    public RegularAccount(String name, String description, AccountType accountType, User userId) {
        super(name, description, accountType, userId);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }
        if (amount > super.getBalance().doubleValue()) {
            throw new InsufficientFundsException("Saldo insuficiente.");
        }
        super.setBalance(super.getBalance().subtract(BigDecimal.valueOf(amount)));
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void deposit(double amount) {
        if (amount > 0) {
            super.setBalance(super.getBalance().add(BigDecimal.valueOf(amount)));
            this.setUpdatedAt(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
    }

    public void apply(double amount) {
        BigDecimal bigAmount = BigDecimal.valueOf(amount);

        if (amount < 0) {
            BigDecimal withdrawAmount = bigAmount.negate();
            if (withdrawAmount.compareTo(super.getBalance()) <= 0) {
                super.setBalance(super.getBalance().subtract(withdrawAmount));
                this.setUpdatedAt(LocalDateTime.now());
            } else {
                throw new IllegalArgumentException("Saldo insuficiente para saque.");
            }
        } else if (amount > 0) {
            super.setBalance(super.getBalance().add(bigAmount));
            this.setUpdatedAt(LocalDateTime.now());
        } else {
            throw new IllegalArgumentException("O valor deve ser diferente de zero.");
        }
    }
}

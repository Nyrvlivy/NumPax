package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CheckingAccount extends Account {
    private String bankCode;
    private String agency;
    private String accountNumber;

    public CheckingAccount() {
    }

    public CheckingAccount(String id, String name, String description, BigDecimal balance, boolean active, User userId,
                           LocalDateTime createdAt, LocalDateTime updatedAt, String bankCode, String agency,
                           String accountNumber) {
        super(id, name, description, balance, AccountType.CHECKING, active, userId, createdAt, updatedAt);
        this.bankCode = bankCode;
        this.agency = agency;
        this.accountNumber = accountNumber;
    }

    public CheckingAccount(String name, String description, AccountType accountType, User userId,
                           String bankCode, String agency, String accountNumber) {
        super(name, description, accountType, userId);
        this.bankCode = bankCode;
        this.agency = agency;
        this.accountNumber = accountNumber;
    }
}

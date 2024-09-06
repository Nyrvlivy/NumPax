package br.com.numpax.domain.entities;

import br.com.numpax.domain.enums.AccountType;

import java.time.LocalDateTime;

public class CheckingAccount extends RegularAccount {
    private String bankName;
    private String agency;
    private String accountNumber;

    public CheckingAccount(String name, String description, User user, AccountType accountType, String bankName, String agency, String accountNumber) {
        super(name, description, user, accountType);
        this.bankName = bankName;
        this.agency = agency;
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        this.setUpdatedAt(LocalDateTime.now());
    }
}

package br.com.numpax.infrastructure.entities;

import java.time.LocalDateTime;

public class RelatedAccounts extends Account {
    private Double totalBalance;
    private Account[] relatedAccounts;
    private Integer totalAccounts;

    public RelatedAccounts(String name, String description, User user, Double totalBalance, Account[] relatedAccounts, Integer totalAccounts) {
        super(name, description, user);
        this.totalBalance = totalBalance;
        this.relatedAccounts = relatedAccounts;
        this.totalAccounts = totalAccounts;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public Account[] getRelatedAccounts() {
        return relatedAccounts;
    }

    public void setRelatedAccounts(Account[] relatedAccounts) {
        this.relatedAccounts = relatedAccounts;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Integer getTotalAccounts() {
        return totalAccounts;
    }
}

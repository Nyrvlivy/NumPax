package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RelatedAccounts extends Account {
    private BigDecimal totalBalance;
    private int totalAccounts;
    private List<Account> relatedAccounts;

    public RelatedAccounts(String name, String description, AccountType accountType, User userId) {
        super(name, description, accountType, userId);
        this.totalBalance = BigDecimal.ZERO;
        this.totalAccounts = 0;
        this.relatedAccounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        this.relatedAccounts.add(account);
        this.totalBalance = this.totalBalance.add(account.getBalance());
        this.totalAccounts++;
        this.setUpdatedAt(LocalDateTime.now());
    }

}

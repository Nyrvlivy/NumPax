package br.com.numpax.domain.entities;

public class RelatedAccounts extends Account {
    private Double totalBalance;          // Saldo total das contas relacionadas
    private Account[] relatedAccounts;    // Contas relacionadas
    private Integer totalAccounts;        // Total de contas relacionadas

    public RelatedAccounts(String name, String description, User user, Double totalBalance, Account[] relatedAccounts, Integer totalAccounts) {
        super(name, description, user);
        this.totalBalance = totalBalance;
        this.relatedAccounts = relatedAccounts;
        this.totalAccounts = totalAccounts;
    }

}

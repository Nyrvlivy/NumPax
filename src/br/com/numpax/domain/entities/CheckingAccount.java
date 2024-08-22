package br.com.numpax.domain.entities;

public class CheckingAccount extends RegularAccount {
    private String bankName;         // Nome do banco
    private String agency;           // Agência
    private String accountNumber;    // Número da conta

    public CheckingAccount(String name, String description, User user, String type, String bankName, String agency, String accountNumber) {
        super(name, description, user, type);
        this.bankName = bankName;
        this.agency = agency;
        this.accountNumber = accountNumber;
    }

}

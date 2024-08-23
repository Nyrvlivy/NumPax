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

    public String getBankName() { return bankName; }

    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAgency() { return agency; }

    public void setAgency(String agency) { this.agency = agency; }

    public String getAccountNumber() { return accountNumber; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}

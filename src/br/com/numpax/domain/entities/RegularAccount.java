package br.com.numpax.domain.entities;

public class RegularAccount extends Account{
    private String type;    // Tipo de conta -> Corrente, Poupança, Investimento ou Objetivo
                            // Pode ser um enum: private AccountType accountType;

    public RegularAccount(String name, String description, User user, String type) {
        super(name, description, user);
        this.type = type;
    }

}

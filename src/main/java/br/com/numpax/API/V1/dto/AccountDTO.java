package br.com.numpax.API.V1.dto;

import java.math.BigDecimal;

public class AccountDTO {
    private String id;
    private String name;
    private BigDecimal balance;

    public AccountDTO(String id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getBalance() { return balance; }
}

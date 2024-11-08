package br.com.numpax.API.V1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CheckingAccountDTO extends AccountDTO {
    private String bankName;
    private String agency;
    private String accountNumber;
    private BigDecimal overdraftLimit;
    private BigDecimal monthlyFee;
    private String bankCode;
    private String pixKeys;

    public CheckingAccountDTO(String id, String name, String description, BigDecimal balance,
                              Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
                              String userId, String bankName, String agency, String accountNumber,
                              BigDecimal overdraftLimit, BigDecimal monthlyFee, String bankCode,
                              String pixKeys) {
        super(id, name, description, balance, AccountType.CHECKING, isActive,
            createdAt, updatedAt, userId);
        this.bankName = bankName;
        this.agency = agency;
        this.accountNumber = accountNumber;
        this.overdraftLimit = overdraftLimit;
        this.monthlyFee = monthlyFee;
        this.bankCode = bankCode;
        this.pixKeys = pixKeys;
    }
}
package br.com.numpax.API.V1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CheckingAccountDTO extends AccountDTO {
    private String bankName;
    private String agency;
    private String accountNumber;
    
    @Builder
    public CheckingAccountDTO(String id, String name, String description, BigDecimal balance,
                            Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
                            String userId, String bankName, String agency, String accountNumber) {
        super(id, name, description, balance, AccountType.CHECKING, isActive, createdAt, updatedAt, userId);
        this.bankName = bankName;
        this.agency = agency;
        this.accountNumber = accountNumber;
    }
} 
package br.com.numpax.API.V1.dto.response;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountResponseDTO {
    private String accountId;
    private String name;
    private String description;
    private BigDecimal balance;
    private AccountType accountType;
    private boolean isActive;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
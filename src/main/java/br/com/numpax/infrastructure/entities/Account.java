package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.AccountType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Account {
    private String accountId;
    private String name;
    private String description;
    private BigDecimal balance;
    private AccountType accountType;
    private boolean isActive;
    private User userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package br.com.numpax.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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
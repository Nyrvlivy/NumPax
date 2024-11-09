package br.com.numpax.API.V1.dto.response;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponseDTO {
    private String transactionId;
    private String code;
    private String name;
    private String description;
    private BigDecimal amount;
    private Category category;
    private Account account;
    private NatureOfTransaction natureOfTransaction;
    private String receiver;
    private String sender;
    private LocalDate transactionDate;
    private boolean isRepeatable;
    private RepeatableType repeatableType;
    private String note;
    private boolean isActive;
    private boolean isEffective;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

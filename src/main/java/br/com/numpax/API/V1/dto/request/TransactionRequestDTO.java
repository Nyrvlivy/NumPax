package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequestDTO {
    
    @NotBlank(message = "Code is required")
    @Size(max = 50, message = "Code must have a maximum of 50 characters")
    private String code;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    private String name;
    
    @Size(max = 255, message = "Description must have a maximum of 255 characters")
    private String description;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "Category ID is required")
    private String categoryId;
    
    @NotNull(message = "Account ID is required")
    private String accountId;
    
    @NotNull(message = "Nature of transaction is required")
    private NatureOfTransaction natureOfTransaction;
    
    @Size(max = 100, message = "Receiver must have a maximum of 100 characters")
    private String receiver;
    
    @Size(max = 100, message = "Sender must have a maximum of 100 characters")
    private String sender;
    
    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;
    
    private boolean isRepeatable;
    
    private RepeatableType repeatableType;
    
    @Size(max = 255, message = "Note must have a maximum of 255 characters")
    private String note;
} 
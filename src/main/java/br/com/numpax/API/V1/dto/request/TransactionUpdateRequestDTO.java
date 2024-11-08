package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionUpdateRequestDTO {
    
    @Size(max = 50, message = "Code must have a maximum of 50 characters")
    private String code;
    
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    private String name;
    
    @Size(max = 255, message = "Description must have a maximum of 255 characters")
    private String description;
    
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    private String categoryId;
    private String accountId;
    private NatureOfTransaction natureOfTransaction;
    
    @Size(max = 100, message = "Receiver must have a maximum of 100 characters")
    private String receiver;
    
    @Size(max = 100, message = "Sender must have a maximum of 100 characters")
    private String sender;
    
    private LocalDate transactionDate;
    private Boolean isRepeatable;
    private RepeatableType repeatableType;
    
    @Size(max = 255, message = "Note must have a maximum of 255 characters")
    private String note;
} 
package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    @NotBlank(message = "O código é obrigatório")
    private String code;
    
    @NotBlank(message = "O nome é obrigatório")
    private String name;
    
    private String description;
    
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal amount;
    
    @NotNull(message = "A natureza da transação é obrigatória")
    private NatureOfTransaction natureOfTransaction;
    
    private String receiver;
    private String sender;
    
    @NotNull(message = "A data da transação é obrigatória")
    private LocalDate transactionDate;
    
    private boolean isRepeatable;
    private RepeatableType repeatableType;
    private String note;
    
    @NotBlank(message = "O ID da conta é obrigatório")
    private String accountId;
    
    @NotBlank(message = "O ID da categoria é obrigatório")
    private String categoryId;
} 
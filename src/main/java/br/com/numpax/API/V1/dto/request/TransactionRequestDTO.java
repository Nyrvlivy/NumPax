package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequestDTO {
    @Size(max = 100, message = "Code value must have a maximum of 100 characters")
    private String code;

    @NotBlank(message = "Name value is required")
    @Size(max = 100, message = "Name value must have a maximum of 100 characters")
    private String name;

    @Size(max = 255, message = "Description value must have a maximum of 255 characters")
    private String description;

    @Positive(message = "Amount value must be greater than zero")
    private BigDecimal amount;

    private NatureOfTransaction natureOfTransaction;

    @Size(max = 100, message = "Receiver value must have a maximum of 100 characters")
    private String receiver;

    @Size(max = 100, message = "Sender value must have a maximum of 100 characters")
    private String sender;

    private LocalDate transactionDate;

    private boolean isRepeatable;
    private RepeatableType repeatableType;

    @Size(max = 255, message = "Note value must have a maximum of 255 characters")
    private String note;

    @NotBlank(message = "Account ID value is required")
    private String accountId;

    @NotBlank(message = "Category ID value is required")
    private String categoryId;
}

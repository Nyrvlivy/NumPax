package br.com.numpax.API.V1.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
//@ToString
public class ActiveTransactionDTO {
    private boolean isEffective;
    private LocalDate transactionDate;
    private String name;
    private String categoryName;
    private String accountName;
    private BigDecimal amount;
}

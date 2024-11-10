package br.com.numpax.API.V1.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ActiveTransactionDTO {
    private String transactionId;
    private boolean isEffective;
    private Date transactionDate;
    private String name;
    private String categoryName;
    private String accountName;
    private BigDecimal amount;
}

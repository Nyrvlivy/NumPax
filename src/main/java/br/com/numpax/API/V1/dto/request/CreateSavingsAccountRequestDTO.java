package br.com.numpax.API.V1.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSavingsAccountRequestDTO extends CreateAccountRequestDTO {
    private LocalDateTime nearestDeadline;
    private LocalDateTime furthestDeadline;
    private LocalDateTime latestDeadline;
    private BigDecimal averageTaxRate;
    private Integer numberOfFixedInvestments;
    private BigDecimal totalMaturityAmount;
    private BigDecimal totalDepositAmount;

    public CreateSavingsAccountRequestDTO() {
        this.setAccountType(AccountType.SAVINGS);
    }
} 
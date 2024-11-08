package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class SavingsAccountRequestDTO extends AccountRequestDTO {
    private LocalDateTime nearestDeadline;
    private LocalDateTime furthestDeadline;
    private LocalDateTime latestDeadline;
    private BigDecimal averageTaxRate;
    private Integer numberOfFixedInvestments;
    private BigDecimal totalMaturityAmount;
    private BigDecimal totalDepositAmount;

    public SavingsAccountRequestDTO() {
        this.setAccountType(AccountType.SAVINGS);
    }

}
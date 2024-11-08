package br.com.numpax.API.V1.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsAccountResponseDTO extends AccountResponseDTO {
    private LocalDateTime nearestDeadline;
    private LocalDateTime furthestDeadline;
    private LocalDateTime latestDeadline;
    private BigDecimal averageTaxRate;
    private Integer numberOfFixedInvestments;
    private BigDecimal totalMaturityAmount;
    private BigDecimal totalDepositAmount;
}
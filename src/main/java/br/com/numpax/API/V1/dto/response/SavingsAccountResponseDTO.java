package br.com.numpax.API.V1.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
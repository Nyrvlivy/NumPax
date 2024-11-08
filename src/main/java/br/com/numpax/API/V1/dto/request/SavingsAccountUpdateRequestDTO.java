package br.com.numpax.API.V1.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SavingsAccountUpdateRequestDTO {

    private String name;
    private String description;
    private LocalDate nearestDeadline;
    private LocalDate furthestDeadline;
    private LocalDate latestDeadline;
    private BigDecimal averageTaxRate;
    private Integer numberOfFixedInvestments;
    private BigDecimal totalMaturityAmount;
    private BigDecimal totalDepositAmount;
}

package br.com.numpax.API.V1.dto.response;

import br.com.numpax.application.enums.FixedInvestmentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FixedInvestmentResponseDTO {
    private String fixedInvestmentId;
    private String name;
    private String description;
    private FixedInvestmentType investmentType;
    private String investmentAccountId;
    private BigDecimal investmentAmount;
    private BigDecimal taxRate;
    private LocalDate investmentDate;
    private LocalDate maturityDate;
    private BigDecimal expectedReturn;
    private BigDecimal currentAmount;
    private BigDecimal profitAmount;
    private String broker;
    private String institution;
    private String note;
    private boolean isRedeemed;
    private LocalDate redemptionDate;
    private BigDecimal redemptionAmount;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 
package br.com.numpax.API.V1.dto.response;

import br.com.numpax.application.enums.VariableInvestmentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class VariableInvestmentResponseDTO {
    private String variableInvestmentId;
    private String name;
    private String description;
    private VariableInvestmentType investmentType;
    private String investmentAccountId;
    private String ticker;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private BigDecimal currentPrice;
    private BigDecimal totalInvested;
    private BigDecimal currentTotal;
    private BigDecimal profitAmount;
    private BigDecimal profitPercentage;
    private LocalDate purchaseDate;
    private String sector;
    private String broker;
    private String note;
    private boolean isActive;
    private LocalDateTime lastUpdate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 
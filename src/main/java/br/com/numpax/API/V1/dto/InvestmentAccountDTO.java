package br.com.numpax.API.V1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvestmentAccountDTO extends AccountDTO {
    private String broker;
    private String accountNumber;
    private BigDecimal totalInvested;
    private BigDecimal profitability;
    private BigDecimal currentYield;
    private LocalDateTime lastUpdate;
    private List<String> investmentTypes;
    
    @Builder
    public InvestmentAccountDTO(String id, String name, String description, BigDecimal balance,
                              Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt,
                              String userId, String broker, String accountNumber, 
                              BigDecimal totalInvested, BigDecimal profitability,
                              BigDecimal currentYield, LocalDateTime lastUpdate,
                              List<String> investmentTypes) {
        super(id, name, description, balance, AccountType.INVESTMENT, isActive, 
              createdAt, updatedAt, userId);
        this.broker = broker;
        this.accountNumber = accountNumber;
        this.totalInvested = totalInvested;
        this.profitability = profitability;
        this.currentYield = currentYield;
        this.lastUpdate = lastUpdate;
        this.investmentTypes = investmentTypes;
    }
} 
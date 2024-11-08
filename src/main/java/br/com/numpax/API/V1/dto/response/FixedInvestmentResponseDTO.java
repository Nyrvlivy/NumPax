package br.com.numpax.API.V1.dto.response;

import br.com.numpax.application.enums.FixedInvestmentType;
import br.com.numpax.application.enums.NatureOfTransaction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FixedInvestmentResponseDTO {
    // Campos herdados de Transaction
    private String transactionId;
    private String code;
    private String name;
    private String description;
    private BigDecimal amount;
    private String categoryId;
    private String accountId;
    private NatureOfTransaction natureOfTransaction;
    private String receiver;
    private String sender;
    private LocalDate transactionDate;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Campos específicos de FixedInvestment
    private FixedInvestmentType fixedInvestmentType;
    private LocalDate investmentDate;
    private LocalDate expirationDate;
    private String institution;
    private BigDecimal redeemValue;
    private LocalDate redeemDate;
    private Integer liquidityPeriod;
    private BigDecimal netGainLoss;
    
    // Status calculados
    private boolean isActive;
    private boolean isMatured;
    private boolean isRedeemed;
    
    public boolean getIsActive() {
        return redeemDate == null && 
               (expirationDate == null || expirationDate.isAfter(LocalDate.now()));
    }
    
    public boolean getIsMatured() {
        return expirationDate != null && 
               (expirationDate.isBefore(LocalDate.now()) || 
                expirationDate.isEqual(LocalDate.now()));
    }
    
    public boolean getIsRedeemed() {
        return redeemDate != null;
    }
} 
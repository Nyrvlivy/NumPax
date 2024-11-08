package br.com.numpax.API.V1.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FixedInvestmentUpdateRequestDTO {
    private String code;
    
    @Size(max = 100)
    private String name;
    
    @Size(max = 255)
    private String description;
    
    @Positive
    private BigDecimal amount;
    
    private String categoryId;
    private String fixedInvestmentType;
    private LocalDate investmentDate;
    private LocalDate expirationDate;
    private String institution;
    private BigDecimal redeemValue;
    private LocalDate redeemDate;
    private Integer liquidityPeriod;
    private String note;
} 
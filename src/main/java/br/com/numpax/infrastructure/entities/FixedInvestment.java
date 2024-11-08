package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.FixedInvestmentType;
import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FixedInvestment extends Transaction {
    private FixedInvestmentType fixedInvestmentType;
    private LocalDate investmentDate;
    private LocalDate expirationDate;
    private String institution;
    private Double[] TaxRates;
    private Double redeemValue;
    private LocalDate redeemDate;
    private Integer liquidityPeriod;
    private Double netGainLoss;


    public FixedInvestment(String code, String name, String description, BigDecimal amount, Category category, RegularAccount regularAccount, NatureOfTransaction natureOfTransaction, String receiver, String sender, LocalDate transactionDate, boolean isRepeatable, RepeatableType repeatableType, String note, FixedInvestmentType fixedInvestmentType, LocalDate investmentDate, LocalDate expirationDate, String institution, Double[] taxRates, Double redeemValue, LocalDate redeemDate, Integer liquidityPeriod, Double netGainLoss) {
        super(code, name, description, amount, category, regularAccount, natureOfTransaction, receiver, sender, transactionDate, repeatableType, note);
        this.fixedInvestmentType = fixedInvestmentType;
        this.investmentDate = investmentDate;
        this.expirationDate = expirationDate;
        this.institution = institution;
        this.TaxRates = taxRates;
        this.redeemValue = redeemValue;
        this.redeemDate = redeemDate;
        this.liquidityPeriod = liquidityPeriod;
        this.netGainLoss = netGainLoss;

    }

    public void setFixedInvestmentType(FixedInvestmentType fixedInvestmentType) {
        this.fixedInvestmentType = fixedInvestmentType;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setInvestmentDate(LocalDate investmentDate) {
        this.investmentDate = investmentDate;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setInstitution(String institution) {
        this.institution = institution;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setTaxRates(Double[] taxRates) {
        TaxRates = taxRates;
    }

    public void setRedeemValue(Double redeemValue) {
        this.redeemValue = redeemValue;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setRedeemDate(LocalDate redeemDate) {
        this.redeemDate = redeemDate;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setLiquidityPeriod(Integer liquidityPeriod) {
        this.liquidityPeriod = liquidityPeriod;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setNetGainLoss(Double netGainLoss) {
        this.netGainLoss = netGainLoss;
    }
}

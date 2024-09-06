package br.com.numpax.domain.entities;

import br.com.numpax.domain.enums.FixedInvestmentType;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public FixedInvestmentType getFixedInvestmentType() {
        return fixedInvestmentType;
    }

    public void setFixedInvestmentType(FixedInvestmentType fixedInvestmentType) {
        this.fixedInvestmentType = fixedInvestmentType;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public LocalDate getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(LocalDate investmentDate) {
        this.investmentDate = investmentDate;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public Double[] getTaxRates() {
        return TaxRates;
    }

    public void setTaxRates(Double[] taxRates) {
        TaxRates = taxRates;
    }

    public Double getRedeemValue() {
        return redeemValue;
    }

    public void setRedeemValue(Double redeemValue) {
        this.redeemValue = redeemValue;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public LocalDate getRedeemDate() {
        return redeemDate;
    }

    public void setRedeemDate(LocalDate redeemDate) {
        this.redeemDate = redeemDate;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public Integer getLiquidityPeriod() {
        return liquidityPeriod;
    }

    public void setLiquidityPeriod(Integer liquidityPeriod) {
        this.liquidityPeriod = liquidityPeriod;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public Double getNetGainLoss() {
        return netGainLoss;
    }

    public void setNetGainLoss(Double netGainLoss) {
        this.netGainLoss = netGainLoss;
    }
}

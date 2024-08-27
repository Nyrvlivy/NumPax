package br.com.numpax.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.numpax.domain.enums.FixedInvestmentType;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

public class FixedInvestment extends Transaction {
    private FixedInvestmentType fixedInvestmentType;   // Tipo de investimento -> CDB, LCI, LCA, LC, LF, CRI, CRA, Debêntures, FIDC, FII, Cotas de Fundos, Previdência Privada
    private LocalDate investmentDate;                  // Data do investimento
    private LocalDate expirationDate;                  // Data de vencimento
    private String institution;                        // Instituição financeira
    private Double[] TaxRates;                         // Taxas de rendimento
    private Double redeemValue;                        // Valor de resgate
    private LocalDate redeemDate;                      // Data de resgate
    private Integer liquidityPeriod;                   // Prazo de liquidez (quantidade de dias para receber o resgate)
    private Double netGainLoss;                        // Ganho ou perda líquido (Valor de resgate - Valor investido)
    private LocalDateTime updatedAt;                   // Data de atualização


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
        this.updatedAt = LocalDateTime.now();
    }

    public FixedInvestmentType getFixedInvestmentType() {
        return fixedInvestmentType;
    }

    public void setFixedInvestmentType(FixedInvestmentType fixedInvestmentType) {
        this.fixedInvestmentType = fixedInvestmentType;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(LocalDate investmentDate) {
        this.investmentDate = investmentDate;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        this.updatedAt = LocalDateTime.now();
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
        this.updatedAt = LocalDateTime.now();
    }

    public Double[] getTaxRates() {
        return TaxRates;
    }

    public Double getRedeemValue() {
        return redeemValue;
    }

    public void setRedeemValue(Double redeemValue) {
        this.redeemValue = redeemValue;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getRedeemDate() {
        return redeemDate;
    }

    public void setRedeemDate(LocalDate redeemDate) {
        this.redeemDate = redeemDate;
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getLiquidityPeriod() {
        return liquidityPeriod;
    }

    public void setLiquidityPeriod(Integer liquidityPeriod) {
        this.liquidityPeriod = liquidityPeriod;
        this.updatedAt = LocalDateTime.now();
    }

    public Double getNetGainLoss() {
        return netGainLoss;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}


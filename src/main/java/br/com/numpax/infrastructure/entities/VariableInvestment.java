package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import br.com.numpax.application.enums.VariableInvestmentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class VariableInvestment extends Transaction {
    private VariableInvestmentType variableInvestmentType;
    private String broker;
    private LocalDate purchaseDate;
    private LocalDate expirationDate;
    private String assetCode;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private LocalDate saleDate;
    private BigDecimal salePrice;
    private BigDecimal[] brokerFees;
    private BigDecimal[] otherFees;

    public VariableInvestment(String code, String name, String description, BigDecimal amount, Category category, RegularAccount regularAccount, NatureOfTransaction natureOfTransaction, String receiver, String sender, LocalDate transactionDate, boolean isRepeatable, RepeatableType repeatableType, String note, VariableInvestmentType variableInvestmentType, LocalDate expirationDate, String broker, LocalDate purchaseDate, String assetCode, BigDecimal quantity, BigDecimal unitPrice, LocalDate saleDate, BigDecimal salePrice, BigDecimal[] brokerFees, BigDecimal[] otherFees) {
        super(code, name, description, amount, category, regularAccount, natureOfTransaction, receiver, sender, transactionDate, repeatableType, note);
        this.variableInvestmentType = variableInvestmentType;
        this.broker = broker;
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
        this.assetCode = assetCode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.saleDate = saleDate;
        this.salePrice = salePrice;
        this.brokerFees = brokerFees;
        this.otherFees = otherFees;
    }

    public void setVariableInvestmentType(VariableInvestmentType variableInvestmentType) {
        this.variableInvestmentType = variableInvestmentType;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setBroker(String broker) {
        this.broker = broker;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void setBrokerFees(BigDecimal[] brokerFees) {
        this.brokerFees = brokerFees;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void setOtherFees(BigDecimal[] otherFees) {
        this.otherFees = otherFees;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public BigDecimal getInvestedAmount() {
        return this.quantity.multiply(this.unitPrice);
    }

    public BigDecimal getReceivedAmount() {
        if (this.salePrice != null && this.quantity != null) {
            return this.salePrice.multiply(this.quantity);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalGainLoss() {
        return getReceivedAmount().subtract(getInvestedAmount());
    }

    public BigDecimal getTotalFees() {
        BigDecimal totalBrokerFees = BigDecimal.ZERO;
        BigDecimal totalOtherFees = BigDecimal.ZERO;

        if (brokerFees != null) {
            for (BigDecimal fee : brokerFees) {
                totalBrokerFees = totalBrokerFees.add(fee);
            }
        }

        if (otherFees != null) {
            for (BigDecimal fee : otherFees) {
                totalOtherFees = totalOtherFees.add(fee);
            }
        }

        return totalBrokerFees.add(totalOtherFees);
    }

    public BigDecimal getNetGainLoss() {
        return getTotalGainLoss().subtract(getTotalFees());
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
        this.setUpdatedAt(LocalDateTime.now());
    }
}

package br.com.numpax.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;
import br.com.numpax.domain.enums.VariableInvestmentType;

public class VariableInvestment extends Transaction {
    private VariableInvestmentType variableInvestmentType;        // Tipo de investimento variável
    private String broker;                                        // Corretora
    private LocalDate purchaseDate;                               // Data de compra
    private LocalDate expirationDate;                             // Data de vencimento
    private final String assetCode;                               // Código do ativo
    private BigDecimal quantity;                                  // Quantidade de ativos
    private BigDecimal unitPrice;                                 // Preço unitário do ativo
    private LocalDate saleDate;                                   // Data de venda (se houver)
    private BigDecimal salePrice;                                 // Preço de venda (se houver)
    private BigDecimal[] brokerFees;                              // Taxas da corretora
    private BigDecimal[] otherFees;                               // Outras taxas
    private LocalDateTime updatedAt;                              // Data de atualização

    public VariableInvestment(String code, String name, String description, BigDecimal amount, Category category, Account account, NatureOfTransaction natureOfTransaction, String receiver, String sender, LocalDate transactionDate, boolean isRepeatable, RepeatableType repeatableType, String note, VariableInvestmentType variableInvestmentType, LocalDate expirationDate, String broker, LocalDate purchaseDate, String assetCode, BigDecimal quantity, BigDecimal unitPrice, LocalDate saleDate, BigDecimal salePrice, BigDecimal[] brokerFees, BigDecimal[] otherFees) {
        super(code, name, description, amount, category, account, natureOfTransaction, receiver, sender, transactionDate, repeatableType, note);
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
        this.updatedAt = LocalDateTime.now();
    }

    // Getters e Setters
    public VariableInvestmentType getVariableInvestmentType() {
        return variableInvestmentType;
    }

    public void setVariableInvestmentType(VariableInvestmentType variableInvestmentType) {
        this.variableInvestmentType = variableInvestmentType;
        this.updatedAt = LocalDateTime.now();
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal[] getBrokerFees() {
        return brokerFees;
    }

    public void setBrokerFees(BigDecimal[] brokerFees) {
        this.brokerFees = brokerFees;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal[] getOtherFees() {
        return otherFees;
    }

    public void setOtherFees(BigDecimal[] otherFees) {
        this.otherFees = otherFees;
        this.updatedAt = LocalDateTime.now();
    }

    // Cálculos Dinâmicos
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

    public BigDecimal getTotalFees() { //calcula a soma total de todas as taxas associadas a uma transação de investimento variável. 
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

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

package br.com.numpax.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

public class Transaction {
    private final String id;                          // Identificador da transação
    private final String code;                              // Código da transação
    private String name;                              // Nome da transação
    private String description;                       // Descrição da transação
    private BigDecimal amount;                        // Valor da transação 
    private Category category;                        // Categoria associada
    private RegularAccount regularAccount;                          // Conta associada
    private NatureOfTransaction natureOfTransaction;  // Natureza da transação -> Receita, Despesa ou Transferência (junçao de Receita e Despesa)
    private String receiver;                          // Recebedor da transação
    private String sender;                            // Remetente da transação
    private LocalDate transactionDate;                // Data da transação (Agendada ou não)
    private boolean isRepeatable;                     // A transação é repetível?
    private RepeatableType repeatableType;            // Tipo de repetição (Nunca, Diária, Semanal, Mensal, Anual)
    private String note;                              // Nota da transação
    private boolean isActive;                         // Ativa ou Inativa
    private LocalDateTime createdAt;                  // Data de criação
    private LocalDateTime updatedAt;                  // Data de atualização
    private boolean isEffective;                      // Efetivado ou não

    public Transaction(String code, String name, String description, BigDecimal amount, Category category, RegularAccount regularAccount, NatureOfTransaction natureOfTransaction, String receiver, String sender, LocalDate transactionDate, RepeatableType repeatableType, String note) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.regularAccount = regularAccount;
        this.natureOfTransaction = natureOfTransaction; // Automático dependendo do acesso no app
        this.receiver = receiver;
        this.sender = sender;
        this.transactionDate = transactionDate;
        this.isRepeatable = false; // está sendo inicializado como false, independentemente do valor passado. Se a intenção for permitir uma transação repetível, o valor deveria ser definido com base no argumento ou na lógica interna. Oque torna uma transação repetitivel ou não?
        this.repeatableType = repeatableType; // Padrão Never
        this.note = note;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isEffective = false;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        this.updatedAt = LocalDateTime.now();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public RegularAccount getAccount() {
        return regularAccount;
    }

    public void setAccount(RegularAccount regularAccount) {
        this.regularAccount = regularAccount;
        this.updatedAt = LocalDateTime.now();
    }

    public NatureOfTransaction getNatureOfTransaction() {
        return natureOfTransaction;
    }

    public void setNatureOfTransaction(NatureOfTransaction natureOfTransaction) {
        this.natureOfTransaction = natureOfTransaction;
        this.updatedAt = LocalDateTime.now();
    }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
        this.updatedAt = LocalDateTime.now();
    }

    public String getSender() { return sender; }

    public void setSender(String sender) {
        this.sender = sender;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getTransactionDate() { return transactionDate; }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isRepeatable() { return isRepeatable; }

    public void setRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
        this.updatedAt = LocalDateTime.now();
    }

    public RepeatableType getRepeatableType() {
        return repeatableType;
    }

    public void setRepeatableType(RepeatableType repeatableType) {
        this.repeatableType = repeatableType;
        this.updatedAt = LocalDateTime.now();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getIsEffective() { return isEffective; }

    public void setIsEffective(boolean effective) {
        isEffective = effective;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean getIsRepeatable() { return isRepeatable; }

    public void setIsRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean getIsActive() { return isActive; }

    public void setActive(Boolean active) {
        isActive = active;
        this.updatedAt = LocalDateTime.now();
    }

    public void apply() {
        if (!isEffective) {
            if (natureOfTransaction == NatureOfTransaction.EXPENSE) {
                regularAccount.withdraw(amount.doubleValue());
            } else if (natureOfTransaction == NatureOfTransaction.INCOME) {
                regularAccount.deposit(amount.doubleValue());
            }
            isEffective = true;
            updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalStateException("Transaction has already been applied.");
        }
    }

    public boolean isEffective() { return isEffective; }

    public void setEffective(boolean effective) { isEffective = effective; }
}



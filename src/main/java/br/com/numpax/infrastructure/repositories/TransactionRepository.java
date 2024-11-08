package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends BaseRepository<Transaction, String> {
    
    List<Transaction> findByAccountId(String accountId);
    
    List<Transaction> findByCategoryId(String categoryId);
    
    List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<Transaction> findEffectiveTransactions();
    
    List<Transaction> findPendingTransactions();
    
    void markAsEffective(String transactionId);
    
    BigDecimal calculateTotalInvestedAmount(String accountId);
    BigDecimal calculateTotalDividendYield(String accountId);
} 
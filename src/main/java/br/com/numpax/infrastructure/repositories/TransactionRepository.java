package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    void create(Transaction transaction);
    Optional<Transaction> findById(String transactionId);
    List<Transaction> findAll();
    List<Transaction> findByAccountId(String accountId);
    void update(Transaction transaction);
    void delete(String transactionId);
} 
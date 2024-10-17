package br.com.numpax.infrastructure.dao;

import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.entities.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionDAO {
    void save(Transaction transaction);
    void update(Transaction transaction);
    void deleteById(String id);
    Optional<Transaction> findById(String id);
    List<Transaction> findByAccount(Account account);
    List<Transaction> findAll();
    List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<Transaction> findRepeatable();
}

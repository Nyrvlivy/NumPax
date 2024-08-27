package br.com.numpax.domain.repositories;

import java.time.LocalDate;
import java.util.List;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.Transaction;

public interface TransactionRepository extends GenericRepository<Transaction, String>{
    List<Transaction> findByAccount(Account account);
    List<Transaction> findAllByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
    List<Transaction> findByIsRepeatableTrue();
}

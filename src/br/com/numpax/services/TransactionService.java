package br.com.numpax.services;

import java.time.LocalDate;
import java.util.List;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.Transaction;

public interface TransactionService {
   Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(String id);
    List<Transaction> getTransactionsByAccount(Account account);
    void deleteTransaction(String id);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate);
    List<Transaction> getRepeatableTransactions();
    Transaction applyTransaction(String id);
    Transaction updateTransaction(String id, Transaction updatedTransaction);
}

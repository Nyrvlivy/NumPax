package br.com.numpax.services;

import java.util.List;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.Transaction;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(String id);
    List<Transaction> getTransactionsByAccount(Account account);
    void deleteTransaction(String id);
    List<Transaction> getAllTransactions();

}

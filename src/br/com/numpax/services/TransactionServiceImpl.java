package br.com.numpax.services;

import java.util.List;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.Transaction;
import br.com.numpax.domain.repositories.TransactionRepository;
import br.com.numpax.exceptions.ResourceNotFoundException;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(String id) {
        return transactionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.findByAccount(account);
    }

    @Override
    public void deleteTransaction(String id) {
        if (transactionRepository.findById(id).isPresent()) {
            transactionRepository.delete(id);
        } else {
            throw new ResourceNotFoundException("Transaction not found with id: " + id);
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

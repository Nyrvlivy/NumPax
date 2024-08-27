package br.com.numpax.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        transaction.setUpdatedAt(LocalDateTime.now());
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
            transactionRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Transaction not found with id: " + id);
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findAllByTransactionDateBetween(startDate, endDate);
    }

    @Override
    public List<Transaction> getRepeatableTransactions() {
        return transactionRepository.findByIsRepeatableTrue();
    }

    @Override
    public Transaction applyTransaction(String id) {
        return transactionRepository.findById(id)
            .map(transaction -> {
                if (!transaction.isEffective()) {
                    transaction.apply();
                    transaction.setEffective(true);
                    transaction.setUpdatedAt(LocalDateTime.now());
                    return transactionRepository.save(transaction);
                } else {
                    throw new IllegalStateException("Transaction has already been applied.");
                }
            })
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
    }

    @Override
    public Transaction updateTransaction(String id, Transaction updatedTransaction) {
        return transactionRepository.findById(id)
            .map(transaction -> {
                transaction.setName(updatedTransaction.getName());
                transaction.setDescription(updatedTransaction.getDescription());
                transaction.setAmount(updatedTransaction.getAmount());
                transaction.setCategory(updatedTransaction.getCategory());
                transaction.setAccount(updatedTransaction.getAccount());
                transaction.setNatureOfTransaction(updatedTransaction.getNatureOfTransaction());
                transaction.setReceiver(updatedTransaction.getReceiver());
                transaction.setSender(updatedTransaction.getSender());
                transaction.setTransactionDate(updatedTransaction.getTransactionDate());
                transaction.setRepeatable(updatedTransaction.isRepeatable());
                transaction.setRepeatableType(updatedTransaction.getRepeatableType());
                transaction.setNote(updatedTransaction.getNote());
                transaction.setActive(updatedTransaction.isActive());
                transaction.setUpdatedAt(LocalDateTime.now());
                return transactionRepository.save(transaction);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
    }
}

package br.com.numpax.application.services.impl;

import br.com.numpax.infrastructure.dao.TransactionDAO;
import br.com.numpax.infrastructure.dao.impl.TransactionDAOImpl;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.API.V1.exceptions.ResourceNotFoundException;
import br.com.numpax.application.services.TransactionService;

import java.time.LocalDate;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDAO;

    public TransactionServiceImpl() {
        this.transactionDAO = new TransactionDAOImpl();
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction.setUpdatedAt(LocalDate.now().atStartOfDay());
        transactionDAO.save(transaction);
        return transaction;
    }

    @Override
    public Transaction getTransactionById(String id) {
        return transactionDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada com id: " + id));
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionDAO.findByAccount(account);
    }

    @Override
    public void deleteTransaction(String id) {
        transactionDAO.deleteById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionDAO.findByDateRange(startDate, endDate);
    }

    @Override
    public List<Transaction> getRepeatableTransactions() {
        return transactionDAO.findRepeatable();
    }

    @Override
    public Transaction applyTransaction(String id) {
        return transactionDAO.findById(id)
            .map(transaction -> {
                if (!transaction.isEffective()) {
                    transaction.apply();
                    transaction.setEffective(true);
                    transaction.setUpdatedAt(LocalDate.now().atStartOfDay());
                    transactionDAO.update(transaction);
                    return transaction;
                } else {
                    throw new IllegalStateException("Transação já foi aplicada.");
                }
            })
            .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada com id: " + id));
    }

    @Override
    public Transaction updateTransaction(String id, Transaction updatedTransaction) {
        return transactionDAO.findById(id)
            .map(transaction -> {
                transaction.setCode(updatedTransaction.getCode());
                transaction.setName(updatedTransaction.getName());
                transaction.setDescription(updatedTransaction.getDescription());
                transaction.setAmount(updatedTransaction.getAmount());
                transaction.setCategory(updatedTransaction.getCategory());
                transaction.setRegularAccount(updatedTransaction.getRegularAccount());
                transaction.setNatureOfTransaction(updatedTransaction.getNatureOfTransaction());
                transaction.setReceiver(updatedTransaction.getReceiver());
                transaction.setSender(updatedTransaction.getSender());
                transaction.setTransactionDate(updatedTransaction.getTransactionDate());
                transaction.setRepeatable(updatedTransaction.isRepeatable());
                transaction.setRepeatableType(updatedTransaction.getRepeatableType());
                transaction.setNote(updatedTransaction.getNote());
                transaction.setActive(updatedTransaction.isActive());
                transaction.setUpdatedAt(LocalDate.now().atStartOfDay());
                
                transactionDAO.update(transaction);
                return transaction;
            })
            .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada com id: " + id));
    }
}

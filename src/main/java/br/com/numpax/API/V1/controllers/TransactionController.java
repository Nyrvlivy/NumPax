package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.application.services.TransactionService;
import br.com.numpax.application.utils.ValidatorUtil;

import java.util.List;

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {
        ValidatorUtil.validate(dto);
        return transactionService.createTransaction(dto);
    }

    public TransactionResponseDTO getTransactionById(String transactionId) {
        return transactionService.findById(transactionId);
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        return transactionService.findAll();
    }

    public List<TransactionResponseDTO> getTransactionsByAccountId(String accountId) {
        return transactionService.findByAccountId(accountId);
    }

    public TransactionResponseDTO updateTransaction(String transactionId, TransactionRequestDTO dto) {
        ValidatorUtil.validate(dto);
        return transactionService.update(transactionId, dto);
    }

    public void deleteTransaction(String transactionId) {
        transactionService.delete(transactionId);
    }
} 
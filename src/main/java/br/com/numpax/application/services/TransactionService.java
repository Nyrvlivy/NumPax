package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.request.TransactionUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    
    TransactionResponseDTO createTransaction(TransactionRequestDTO dto);
    
    TransactionResponseDTO getTransactionById(String transactionId);
    
    TransactionResponseDTO updateTransaction(String transactionId, TransactionUpdateRequestDTO dto);
    
    void markAsEffective(String transactionId);
    
    void deactivateTransaction(String transactionId);
    
    void deleteTransaction(String transactionId);
    
    List<TransactionResponseDTO> listAllTransactions();
    
    List<TransactionResponseDTO> listAllActiveTransactions();
    
    List<TransactionResponseDTO> listAllInactiveTransactions();
    
    List<TransactionResponseDTO> findByAccountId(String accountId);
    
    List<TransactionResponseDTO> findByCategoryId(String categoryId);
    
    List<TransactionResponseDTO> findByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<TransactionResponseDTO> findEffectiveTransactions();
    
    List<TransactionResponseDTO> findPendingTransactions();
} 
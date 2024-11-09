package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import java.util.List;

public interface TransactionService {
    TransactionResponseDTO createTransaction(TransactionRequestDTO dto);
    TransactionResponseDTO findById(String transactionId);
    List<TransactionResponseDTO> findAll();
    List<TransactionResponseDTO> findByAccountId(String accountId);
    TransactionResponseDTO update(String transactionId, TransactionRequestDTO dto);
    void delete(String transactionId);
} 
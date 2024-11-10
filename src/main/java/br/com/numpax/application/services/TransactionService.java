package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.ActiveTransactionDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
    TransactionResponseDTO createTransaction(TransactionRequestDTO dto, String accountId, String categoryId);

    List<ActiveTransactionDTO> listActiveTransactionsByUserId(String userId);

    List<ActiveTransactionDTO> listActiveTransactionsByUserId(String userId, String nature);
}

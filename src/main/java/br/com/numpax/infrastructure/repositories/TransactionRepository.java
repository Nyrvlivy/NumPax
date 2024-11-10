package br.com.numpax.infrastructure.repositories;

import br.com.numpax.API.V1.dto.response.ActiveTransactionDTO;
import br.com.numpax.infrastructure.entities.Transaction;

import java.util.List;

public interface TransactionRepository {
    void create(Transaction transaction);
    List<ActiveTransactionDTO> findActiveTransactionsByUserId(String userId);
}

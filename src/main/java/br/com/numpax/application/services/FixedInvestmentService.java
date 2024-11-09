package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import java.util.List;

public interface FixedInvestmentService {
    TransactionResponseDTO createInvestment(TransactionRequestDTO dto);
    TransactionResponseDTO findById(String investmentId);
    List<TransactionResponseDTO> findAllByAccountId(String accountId);
    TransactionResponseDTO update(String investmentId, TransactionRequestDTO dto);
    void redeem(String investmentId);
} 
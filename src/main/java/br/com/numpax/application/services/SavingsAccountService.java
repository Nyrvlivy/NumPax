package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.SavingsAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.SavingsAccountResponseDTO;

import java.util.List;

public interface SavingsAccountService {

    SavingsAccountResponseDTO createAccount(SavingsAccountRequestDTO dto, String userId);

    SavingsAccountResponseDTO getAccountById(String accountId);

    SavingsAccountResponseDTO updateAccount(String accountId, SavingsAccountRequestDTO dto);

    void deactivateAccount(String accountId);

    void deleteAccount(String accountId);

    List<SavingsAccountResponseDTO> listAllAccounts();

    List<SavingsAccountResponseDTO> listAllActiveAccounts();

    List<SavingsAccountResponseDTO> listAllInactiveAccounts();
}

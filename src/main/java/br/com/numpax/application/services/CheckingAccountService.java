package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.CheckingAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.CheckingAccountResponseDTO;

import java.util.List;

public interface CheckingAccountService {

    CheckingAccountResponseDTO createAccount(CheckingAccountRequestDTO dto, String userId);

    CheckingAccountResponseDTO getAccountById(String accountId);

    CheckingAccountResponseDTO updateAccount(String accountId, CheckingAccountRequestDTO dto);

    void deactivateAccount(String accountId);

    void deleteAccount(String accountId);

    List<CheckingAccountResponseDTO> listAllAccounts();

    List<CheckingAccountResponseDTO> listAllActiveAccounts();

    List<CheckingAccountResponseDTO> listAllInactiveAccounts();
}

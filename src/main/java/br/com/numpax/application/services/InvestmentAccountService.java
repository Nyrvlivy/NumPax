package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.InvestmentAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.InvestmentAccountResponseDTO;

import java.util.List;

public interface InvestmentAccountService {

    InvestmentAccountResponseDTO createAccount(InvestmentAccountRequestDTO dto, String userId);

    InvestmentAccountResponseDTO getAccountById(String accountId);

    InvestmentAccountResponseDTO updateAccount(String accountId, InvestmentAccountRequestDTO dto);

    void deactivateAccount(String accountId);

    void deleteAccount(String accountId);

    List<InvestmentAccountResponseDTO> listAllAccounts();

    List<InvestmentAccountResponseDTO> listAllActiveAccounts();

    List<InvestmentAccountResponseDTO> listAllInactiveAccounts();
}

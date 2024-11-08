package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.InvestmentAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.InvestmentAccountResponseDTO;

import java.util.List;

public interface InvestmentAccountService {

    InvestmentAccountResponseDTO createAccount(InvestmentAccountRequestDTO dto);

    InvestmentAccountResponseDTO getAccountById(String accountId);

    InvestmentAccountResponseDTO updateAccount(String accountId, InvestmentAccountRequestDTO dto);

    void deleteAccount(String accountId);

    void deactivateAccount(String accountId);

    List<InvestmentAccountResponseDTO> listAllAccounts();

    List<InvestmentAccountResponseDTO> listAllActiveAccounts();

    List<InvestmentAccountResponseDTO> listAllInactiveAccounts();

    List<InvestmentAccountResponseDTO> findAccountsByInstitution(String institution);

    InvestmentAccountResponseDTO findByAccountNumber(String accountNumber);

    InvestmentAccountResponseDTO findByAgencyAndAccountNumber(String agency, String accountNumber);
}

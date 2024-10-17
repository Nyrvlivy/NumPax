package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO, String userId);
    AccountDTO getAccountById(String id);
    List<AccountDTO> getAccountsByUserId(String userId);
    List<AccountDTO> getAllAccounts();
    void disableAccountById(String id);
}

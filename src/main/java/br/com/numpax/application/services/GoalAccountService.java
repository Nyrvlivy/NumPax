package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.GoalAccountRequestDTO;
import br.com.numpax.API.V1.dto.request.GoalAccountUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.GoalAccountResponseDTO;

import java.util.List;

public interface GoalAccountService {

    GoalAccountResponseDTO createAccount(GoalAccountRequestDTO dto, String userId);

    GoalAccountResponseDTO getAccountById(String accountId);

    GoalAccountResponseDTO updateAccount(String accountId, GoalAccountUpdateRequestDTO dto);

    void deactivateAccount(String accountId);

    void deleteAccount(String accountId);

    List<GoalAccountResponseDTO> listAllAccounts();

    List<GoalAccountResponseDTO> listAllActiveAccounts();

    List<GoalAccountResponseDTO> listAllInactiveAccounts();
}

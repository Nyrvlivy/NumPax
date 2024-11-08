package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.CheckingAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.CheckingAccountResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.mappers.CheckingAccountMapper;
import br.com.numpax.application.services.CheckingAccountService;
import br.com.numpax.application.services.UserService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.CheckingAccount;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.CheckingAccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CheckingAccountServiceImpl implements CheckingAccountService {

    private final CheckingAccountRepository repository;
    private final UserService userService; // Ainda não existe um UserService

    public CheckingAccountServiceImpl(CheckingAccountRepository repository, UserService userService) { // Ainda não existe um UserService
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public CheckingAccountResponseDTO createAccount(CheckingAccountRequestDTO dto, String userId) {
        // Validate DTO
        ValidatorUtil.validate(dto);

        // Fetch User entity
        User user = userService.findUserById(userId);

        // Map DTO to Entity
        CheckingAccount account = CheckingAccountMapper.toEntity(dto, user);

        // Set additional fields
        account.setAccountId(UUID.randomUUID().toString());
        account.setBalance(BigDecimal.ZERO);
        account.setIsActive(true);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        // Save account
        repository.create(account);

        // Return response DTO
        return CheckingAccountMapper.toResponseDTO(account);
    }


    @Override
    public CheckingAccountResponseDTO getAccountById(String accountId) {
        Optional<CheckingAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }
        return CheckingAccountMapper.toResponseDTO(accountOptional.get());
    }

    @Override
    public CheckingAccountResponseDTO updateAccount(String accountId, CheckingAccountRequestDTO dto) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        Optional<CheckingAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }

        CheckingAccount account = accountOptional.get();
        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setBankCode(dto.getBankCode());
        account.setAgency(dto.getAgency());
        account.setAccountNumber(dto.getAccountNumber());
        account.setUpdatedAt(LocalDateTime.now());

        repository.update(account);

        return CheckingAccountMapper.toResponseDTO(account);
    }

    @Override
    public void deactivateAccount(String accountId) {
        Optional<CheckingAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }
        CheckingAccount account = accountOptional.get();
        account.setIsActive(false);
        account.setUpdatedAt(LocalDateTime.now());
        repository.update(account);
    }

    @Override
    public void deleteAccount(String accountId) {
        repository.delete(accountId);
    }

    @Override
    public List<CheckingAccountResponseDTO> listAllAccounts() {
        List<CheckingAccount> accounts = repository.findAll();
        return accounts.stream()
            .map(CheckingAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<CheckingAccountResponseDTO> listAllActiveAccounts() {
        List<CheckingAccount> accounts = repository.findAllActive();
        return accounts.stream()
            .map(CheckingAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<CheckingAccountResponseDTO> listAllInactiveAccounts() {
        List<CheckingAccount> accounts = repository.findAllInactive();
        return accounts.stream()
            .map(CheckingAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

}

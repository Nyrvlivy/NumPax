package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.SavingsAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.SavingsAccountResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.mappers.SavingsAccountMapper;
import br.com.numpax.application.services.SavingsAccountService;
import br.com.numpax.application.services.UserService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.SavingsAccount;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.SavingsAccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SavingsAccountServiceImpl implements SavingsAccountService {

    private final SavingsAccountRepository repository;
    private final UserService userService;

    public SavingsAccountServiceImpl(SavingsAccountRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public SavingsAccountResponseDTO createAccount(SavingsAccountRequestDTO dto, String userId) {

        ValidatorUtil.validate(dto);

        User user = userService.findUserById(userId);

        SavingsAccount account = SavingsAccountMapper.toEntity(dto, user);

        account.setAccountId(UUID.randomUUID().toString());
        account.setBalance(BigDecimal.ZERO);
        account.setIsActive(true);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        repository.create(account);

        return SavingsAccountMapper.toResponseDTO(account);
    }

    @Override
    public SavingsAccountResponseDTO getAccountById(String accountId) {
        Optional<SavingsAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }
        return SavingsAccountMapper.toResponseDTO(accountOptional.get());
    }

    @Override
    public SavingsAccountResponseDTO updateAccount(String accountId, SavingsAccountRequestDTO dto) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        Optional<SavingsAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }

        SavingsAccount account = accountOptional.get();
        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setNearestDeadline(dto.getNearestDeadline());
        account.setFurthestDeadline(dto.getFurthestDeadline());
        account.setLatestDeadline(dto.getLatestDeadline());
        account.setAverageTaxRate(dto.getAverageTaxRate());
        account.setNumberOfFixedInvestments(dto.getNumberOfFixedInvestments());
        account.setTotalMaturityAmount(dto.getTotalMaturityAmount());
        account.setTotalDepositAmount(dto.getTotalDepositAmount());
        account.setUpdatedAt(LocalDateTime.now());

        repository.update(account);

        return SavingsAccountMapper.toResponseDTO(account);
    }

    @Override
    public void deactivateAccount(String accountId) {
        Optional<SavingsAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }

        SavingsAccount account = accountOptional.get();
        account.setIsActive(false);
        account.setUpdatedAt(LocalDateTime.now());

        repository.update(account);
    }

    @Override
    public void deleteAccount(String accountId) {
        repository.delete(accountId);
    }

    @Override
    public List<SavingsAccountResponseDTO> listAllAccounts() {
        return repository.findAll().stream()
            .map(SavingsAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<SavingsAccountResponseDTO> listAllActiveAccounts() {
        return repository.findAllActive().stream()
            .map(SavingsAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<SavingsAccountResponseDTO> listAllInactiveAccounts() {
        return repository.findAllInactive().stream()
            .map(SavingsAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

}

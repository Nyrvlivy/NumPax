package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.GoalAccountRequestDTO;
import br.com.numpax.API.V1.dto.request.GoalAccountUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.GoalAccountResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.mappers.GoalAccountMapper;
import br.com.numpax.application.services.CategoryService;
import br.com.numpax.application.services.GoalAccountService;
import br.com.numpax.application.services.UserService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.GoalAccount;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.GoalAccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GoalAccountServiceImpl implements GoalAccountService {

    private final GoalAccountRepository repository;
    private final UserService userService;
    private final CategoryService categoryService; // Adicionado aqui

    public GoalAccountServiceImpl(GoalAccountRepository repository, UserService userService, CategoryService categoryService) {
        this.repository = repository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public GoalAccountResponseDTO createAccount(GoalAccountRequestDTO dto, String userId) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        User user = userService.findUserById(userId);

        Category category = categoryService.findCategoryById(dto.getCategoryId());

        GoalAccount account = GoalAccountMapper.toEntity(dto, user, category);
        account.setAccountId(UUID.randomUUID().toString());
        account.setBalance(BigDecimal.ZERO);
        account.setIsActive(true);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        repository.create(account);

        return GoalAccountMapper.toResponseDTO(account);
    }

    @Override
    public GoalAccountResponseDTO getAccountById(String accountId) {
        Optional<GoalAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }
        return GoalAccountMapper.toResponseDTO(accountOptional.get());
    }

    @Override
    public GoalAccountResponseDTO updateAccount(String accountId, GoalAccountUpdateRequestDTO dto) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        Optional<GoalAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }

        GoalAccount account = accountOptional.get();

        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setTargetValue(dto.getTargetValue());
        account.setAmountValue(dto.getAmountValue());
        account.setTargetTaxRate(dto.getTargetTaxRate());
        account.setMonthlyTaxRate(dto.getMonthlyTaxRate());
        account.setMonthlyEstimate(dto.getMonthlyEstimate());
        account.setMonthlyAchievement(dto.getMonthlyAchievement());

        // Atualizar categoria se fornecida
        if (dto.getCategoryId() != null) {
            Category category = categoryService.findCategoryById(dto.getCategoryId());
            account.setCategory(category);
        }

        account.setTargetDate(dto.getTargetDate());
        account.setStartDate(dto.getStartDate());
        account.setEndDate(dto.getEndDate());
        account.setUpdatedAt(LocalDateTime.now());

        repository.update(account);

        return GoalAccountMapper.toResponseDTO(account);
    }

    @Override
    public void deactivateAccount(String accountId) {
        Optional<GoalAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }
        GoalAccount account = accountOptional.get();
        account.setIsActive(false); // Certifique-se de que o método seja setActive
        account.setUpdatedAt(LocalDateTime.now());
        repository.update(account);
    }

    @Override
    public void deleteAccount(String accountId) {
        repository.delete(accountId);
    }

    @Override
    public List<GoalAccountResponseDTO> listAllAccounts() {
        return repository.findAll().stream()
            .map(GoalAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<GoalAccountResponseDTO> listAllActiveAccounts() {
        return repository.findAllActive().stream()
            .map(GoalAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<GoalAccountResponseDTO> listAllInactiveAccounts() {
        List<GoalAccount> accounts = repository.findAllInactive();
        return accounts.stream()
            .map(GoalAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

}

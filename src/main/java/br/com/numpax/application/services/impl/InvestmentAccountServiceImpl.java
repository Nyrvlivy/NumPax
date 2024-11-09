package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.InvestmentAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.InvestmentAccountResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.mappers.InvestmentAccountMapper;
import br.com.numpax.application.services.InvestmentAccountService;
import br.com.numpax.application.services.UserService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InvestmentAccountServiceImpl implements InvestmentAccountService {

    private final InvestmentAccountRepository repository;
    private final UserService userService; // Ainda não existe um UserService

    public InvestmentAccountServiceImpl(InvestmentAccountRepository repository, UserService userService) { // Ainda não existe um UserService
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public InvestmentAccountResponseDTO createAccount(InvestmentAccountRequestDTO dto, String userId) {

        ValidatorUtil.validate(dto);

        User user = userService.findUserById(userId);
        if (user == null) {
            throw new AccountNotFoundException("Usuário não encontrado" + userId);
        }

        InvestmentAccount account = InvestmentAccountMapper.toEntity(dto, user);
        account.setAccountId(UUID.randomUUID().toString());
        account.setBalance(BigDecimal.ZERO);
        account.setIsActive(true);
        account.setUserId(user);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        repository.create(account);

        return InvestmentAccountMapper.toResponseDTO(account);
    }

    @Override
    public InvestmentAccountResponseDTO getAccountById(String accountId) {
        Optional<InvestmentAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }
        return InvestmentAccountMapper.toResponseDTO(accountOptional.get());
    }

    @Override
    public InvestmentAccountResponseDTO updateAccount(String accountId, InvestmentAccountRequestDTO dto) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        Optional<InvestmentAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }

        InvestmentAccount account = accountOptional.get();
        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        
        // Campos específicos da InvestmentAccount com validação null-safe
        account.setTotalInvestedAmount(dto.getTotalInvestedAmount() != null ? dto.getTotalInvestedAmount() : account.getTotalInvestedAmount());
        account.setTotalProfit(dto.getTotalProfit() != null ? dto.getTotalProfit() : account.getTotalProfit());
        account.setTotalCurrentAmount(dto.getTotalCurrentAmount() != null ? dto.getTotalCurrentAmount() : account.getTotalCurrentAmount());
        account.setTotalWithdrawnAmount(dto.getTotalWithdrawnAmount() != null ? dto.getTotalWithdrawnAmount() : account.getTotalWithdrawnAmount());
        account.setNumberOfWithdrawals(dto.getNumberOfWithdrawals() != null ? dto.getNumberOfWithdrawals() : account.getNumberOfWithdrawals());
        account.setNumberOfEntries(dto.getNumberOfEntries() != null ? dto.getNumberOfEntries() : account.getNumberOfEntries());
        account.setNumberOfAssets(dto.getNumberOfAssets() != null ? dto.getNumberOfAssets() : account.getNumberOfAssets());
        account.setAveragePurchasePrice(dto.getAveragePurchasePrice() != null ? dto.getAveragePurchasePrice() : account.getAveragePurchasePrice());
        account.setTotalGainLoss(dto.getTotalGainLoss() != null ? dto.getTotalGainLoss() : account.getTotalGainLoss());
        account.setTotalDividendYield(dto.getTotalDividendYield() != null ? dto.getTotalDividendYield() : account.getTotalDividendYield());
        account.setRiskLevelType(dto.getRiskLevelType() != null ? dto.getRiskLevelType() : account.getRiskLevelType());
        account.setInvestmentSubtype(dto.getInvestmentSubtype() != null ? dto.getInvestmentSubtype() : account.getInvestmentSubtype());
        
        account.setUpdatedAt(LocalDateTime.now());

        repository.update(account);

        return InvestmentAccountMapper.toResponseDTO(account);
    }

    @Override
    public void deactivateAccount(String accountId) {
        Optional<InvestmentAccount> accountOptional = repository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta não encontrada: " + accountId);
        }
        
        InvestmentAccount account = accountOptional.get();
        account.setIsActive(false);
        account.setUpdatedAt(LocalDateTime.now());
        
        repository.update(account);
    }

    @Override
    public void deleteAccount(String accountId) {
        repository.delete(accountId);
    }

    @Override
    public List<InvestmentAccountResponseDTO> listAllAccounts() {
        List<InvestmentAccount> accounts = repository.findAll();
        return accounts.stream()
            .map(InvestmentAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<InvestmentAccountResponseDTO> listAllActiveAccounts() {
        List<InvestmentAccount> accounts = repository.findAllActive();
        return accounts.stream()
            .map(InvestmentAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<InvestmentAccountResponseDTO> listAllInactiveAccounts() {
        List<InvestmentAccount> accounts = repository.findAllInactive();
        return accounts.stream()
            .map(InvestmentAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<InvestmentAccountResponseDTO> findAllByUserId(String userId) {
        // Validar se o usuário existe
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new AccountNotFoundException("Usuário não encontrado: " + userId);
        }

        // Buscar todas as contas do usuário
        List<InvestmentAccount> accounts = repository.findByUserId(userId);
        
        // Converter para DTO e retornar
        return accounts.stream()
            .map(InvestmentAccountMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

}

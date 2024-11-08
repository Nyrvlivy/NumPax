package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.InvestmentAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.InvestmentAccountResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.exceptions.DuplicateAccountException;
import br.com.numpax.API.V1.mappers.InvestmentAccountMapper;
import br.com.numpax.application.enums.RiskLevelType;
import br.com.numpax.application.services.InvestmentAccountService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.entities.VariableInvestment;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;
import br.com.numpax.infrastructure.repositories.TransactionRepository;
import br.com.numpax.infrastructure.repositories.VariableInvestmentRepository;
import br.com.numpax.infrastructure.transaction.TransactionManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

public class InvestmentAccountServiceImpl implements InvestmentAccountService {

    private final InvestmentAccountRepository investmentAccountRepository;
    private final TransactionRepository transactionRepository;
    private final VariableInvestmentRepository variableInvestmentRepository;

    @Inject
    private TransactionManager transactionManager;

    public InvestmentAccountServiceImpl(
            InvestmentAccountRepository investmentAccountRepository,
            TransactionRepository transactionRepository,
            VariableInvestmentRepository variableInvestmentRepository) {
        this.investmentAccountRepository = investmentAccountRepository;
        this.transactionRepository = transactionRepository;
        this.variableInvestmentRepository = variableInvestmentRepository;
    }

    @Override
    public InvestmentAccountResponseDTO createAccount(InvestmentAccountRequestDTO dto) {
        try {
            transactionManager.beginTransaction();
            
            ValidatorUtil.validate(dto);

            if (dto.getAccountNumber() != null && 
                investmentAccountRepository.existsByAccountNumber(dto.getAccountNumber())) {
                throw new DuplicateAccountException("Account number already exists");
            }

            if (dto.getAccountNumber() != null && dto.getAgency() != null && 
                investmentAccountRepository.existsByAgencyAndAccountNumber(dto.getAgency(), dto.getAccountNumber())) {
                throw new DuplicateAccountException("Account with this agency and number already exists");
            }

            InvestmentAccount account = InvestmentAccountMapper.toEntity(dto);
            investmentAccountRepository.create(account);

            Map<String, BigDecimal> calculatedValues = initializeCalculatedValues();
            Map<String, Integer> calculatedCounts = initializeCalculatedCounts();
            RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(account.getAccountId());

            transactionManager.commitTransaction();
            return InvestmentAccountMapper.toResponseDTO(account, riskLevel, calculatedValues, calculatedCounts);
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public InvestmentAccountResponseDTO getAccountById(String accountId) {
        InvestmentAccount account = findAccountOrThrow(accountId);
        
        Map<String, BigDecimal> calculatedValues = calculateMonetaryValues(accountId);
        Map<String, Integer> calculatedCounts = calculateCounts(accountId);
        RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(accountId);

        return InvestmentAccountMapper.toResponseDTO(account, riskLevel, calculatedValues, calculatedCounts);
    }

    @Override
    public InvestmentAccountResponseDTO updateAccount(String accountId, InvestmentAccountRequestDTO dto) {
        ValidatorUtil.validate(dto);

        InvestmentAccount account = findAccountOrThrow(accountId);

        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setInstitution(dto.getInstitution());
        account.setAccountNumber(dto.getAccountNumber());
        account.setAgency(dto.getAgency());
        account.setNote(dto.getNote());
        account.setUpdatedAt(LocalDateTime.now());

        investmentAccountRepository.update(account);

        Map<String, BigDecimal> calculatedValues = calculateMonetaryValues(accountId);
        Map<String, Integer> calculatedCounts = calculateCounts(accountId);
        RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(accountId);

        return InvestmentAccountMapper.toResponseDTO(account, riskLevel, calculatedValues, calculatedCounts);
    }

    @Override
    public void deleteAccount(String accountId) {
        findAccountOrThrow(accountId);
        investmentAccountRepository.delete(accountId);
    }

    @Override
    public void deactivateAccount(String accountId) {
        InvestmentAccount account = findAccountOrThrow(accountId);
        account.setActive(false);
        account.setUpdatedAt(LocalDateTime.now());
        investmentAccountRepository.update(account);
    }

    @Override
    public List<InvestmentAccountResponseDTO> listAllAccounts() {
        return investmentAccountRepository.findAll().stream()
            .map(account -> {
                Map<String, BigDecimal> values = calculateMonetaryValues(account.getAccountId());
                Map<String, Integer> counts = calculateCounts(account.getAccountId());
                RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(account.getAccountId());
                return InvestmentAccountMapper.toResponseDTO(account, riskLevel, values, counts);
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<InvestmentAccountResponseDTO> listAllActiveAccounts() {
        return investmentAccountRepository.findAllActive().stream()
            .map(account -> {
                Map<String, BigDecimal> values = calculateMonetaryValues(account.getAccountId());
                Map<String, Integer> counts = calculateCounts(account.getAccountId());
                RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(account.getAccountId());
                return InvestmentAccountMapper.toResponseDTO(account, riskLevel, values, counts);
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<InvestmentAccountResponseDTO> listAllInactiveAccounts() {
        return investmentAccountRepository.findAllInactive().stream()
            .map(account -> {
                Map<String, BigDecimal> values = calculateMonetaryValues(account.getAccountId());
                Map<String, Integer> counts = calculateCounts(account.getAccountId());
                RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(account.getAccountId());
                return InvestmentAccountMapper.toResponseDTO(account, riskLevel, values, counts);
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<InvestmentAccountResponseDTO> findAccountsByInstitution(String institution) {
        return investmentAccountRepository.findByInstitution(institution).stream()
            .map(account -> {
                Map<String, BigDecimal> values = calculateMonetaryValues(account.getAccountId());
                Map<String, Integer> counts = calculateCounts(account.getAccountId());
                RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(account.getAccountId());
                return InvestmentAccountMapper.toResponseDTO(account, riskLevel, values, counts);
            })
            .collect(Collectors.toList());
    }

    @Override
    public InvestmentAccountResponseDTO findByAccountNumber(String accountNumber) {
        InvestmentAccount account = investmentAccountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));

        Map<String, BigDecimal> values = calculateMonetaryValues(account.getAccountId());
        Map<String, Integer> counts = calculateCounts(account.getAccountId());
        RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(account.getAccountId());

        return InvestmentAccountMapper.toResponseDTO(account, riskLevel, values, counts);
    }

    @Override
    public InvestmentAccountResponseDTO findByAgencyAndAccountNumber(String agency, String accountNumber) {
        InvestmentAccount account = investmentAccountRepository.findByAgencyAndAccountNumber(agency, accountNumber)
            .orElseThrow(() -> new AccountNotFoundException("Account not found with agency: " + agency + " and number: " + accountNumber));

        Map<String, BigDecimal> values = calculateMonetaryValues(account.getAccountId());
        Map<String, Integer> counts = calculateCounts(account.getAccountId());
        RiskLevelType riskLevel = investmentAccountRepository.findMostFrequentRiskLevel(account.getAccountId());

        return InvestmentAccountMapper.toResponseDTO(account, riskLevel, values, counts);
    }

    // Métodos auxiliares de cálculo
    private Map<String, BigDecimal> calculateMonetaryValues(String accountId) {
        Map<String, BigDecimal> values = new HashMap<>();

        // totalInvestedAmount: soma de amounts da Transactions onde is_active = 1 e nature = VARIABLE_INVESTMENT
        values.put("totalInvestedAmount", transactionRepository.calculateTotalInvestedAmount(accountId));

        // totalProfit: soma de (salePrice * quantity) - amount onde is_active = 1, sale_date IS NOT NULL
        values.put("totalProfit", variableInvestmentRepository.calculateTotalProfit(accountId));

        // totalCurrentAmount: soma de lucros + valores não vendidos
        values.put("totalCurrentAmount", variableInvestmentRepository.calculateTotalCurrentAmount(accountId));

        // totalWithdrawnAmount: soma de (salePrice * quantity) - amount com expiration
        values.put("totalWithdrawnAmount", variableInvestmentRepository.calculateTotalWithdrawnAmount(accountId));

        // averagePurchasePrice: média de unit_price onde IS_ACTIVE = 1
        values.put("averagePurchasePrice", variableInvestmentRepository.calculateAveragePurchasePrice(accountId));

        // totalGainLoss: soma de (quantity * sale_price - amount) onde is_active = 1, com venda
        values.put("totalGainLoss", variableInvestmentRepository.calculateTotalGainLoss(accountId));

        // totalDividendYield: soma de amounts onde is_active = 1, categoria DIVIDEND
        values.put("totalDividendYield", transactionRepository.calculateTotalDividendYield(accountId));

        return values;
    }

    private Map<String, Integer> calculateCounts(String accountId) {
        Map<String, Integer> counts = new HashMap<>();

        // numberOfWithdrawals: contagem de transactions com venda
        counts.put("numberOfWithdrawals", variableInvestmentRepository.countWithdrawals(accountId));

        // numberOfEntries: contagem de transactions sem venda
        counts.put("numberOfEntries", variableInvestmentRepository.countEntries(accountId));

        // numberOfAssets: contagem DISTINCT de asset_code ativos
        counts.put("numberOfAssets", variableInvestmentRepository.countDistinctAssets(accountId));

        return counts;
    }

    private Map<String, BigDecimal> initializeCalculatedValues() {
        Map<String, BigDecimal> values = new HashMap<>();
        values.put("totalInvestedAmount", BigDecimal.ZERO);
        values.put("totalProfit", BigDecimal.ZERO);
        values.put("totalCurrentAmount", BigDecimal.ZERO);
        values.put("totalWithdrawnAmount", BigDecimal.ZERO);
        values.put("averagePurchasePrice", BigDecimal.ZERO);
        values.put("totalGainLoss", BigDecimal.ZERO);
        values.put("totalDividendYield", BigDecimal.ZERO);
        return values;
    }

    private Map<String, Integer> initializeCalculatedCounts() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("numberOfWithdrawals", 0);
        counts.put("numberOfEntries", 0);
        counts.put("numberOfAssets", 0);
        return counts;
    }

    private InvestmentAccount findAccountOrThrow(String accountId) {
        return investmentAccountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));
    }
}

package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.exceptions.CategoryNotFoundException;
import br.com.numpax.API.V1.exceptions.TransactionNotFoundException;
import br.com.numpax.API.V1.mappers.TransactionMapper;

import br.com.numpax.application.services.TransactionService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.repositories.CheckingAccountRepository;
import br.com.numpax.infrastructure.repositories.SavingsAccountRepository;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;
import br.com.numpax.infrastructure.repositories.GoalAccountRepository;
import br.com.numpax.infrastructure.repositories.CategoryRepository;
import br.com.numpax.infrastructure.repositories.TransactionRepository;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CheckingAccountRepository checkingAccountRepository;
    private final SavingsAccountRepository savingsAccountRepository;
    private final InvestmentAccountRepository investmentAccountRepository;
    private final GoalAccountRepository goalAccountRepository;
    private final CategoryRepository categoryRepository;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            CheckingAccountRepository checkingAccountRepository,
            SavingsAccountRepository savingsAccountRepository,
            InvestmentAccountRepository investmentAccountRepository,
            GoalAccountRepository goalAccountRepository,
            CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
        this.investmentAccountRepository = investmentAccountRepository;
        this.goalAccountRepository = goalAccountRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {
        ValidatorUtil.validate(dto);

        Account account = findAccountById(dto.getAccountId());
        if (account == null) {
            throw new AccountNotFoundException("Conta não encontrada: " + dto.getAccountId());
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada: " + dto.getCategoryId()));

        Transaction transaction = TransactionMapper.toEntity(dto, account, category);
        transactionRepository.create(transaction);

        return TransactionMapper.toResponseDTO(transaction);
    }

    private Account findAccountById(String accountId) {
        return checkingAccountRepository.findById(accountId)
            .map(account -> (Account) account)
            .orElseGet(() -> savingsAccountRepository.findById(accountId)
                .map(account -> (Account) account)
                .orElseGet(() -> investmentAccountRepository.findById(accountId)
                    .map(account -> (Account) account)
                    .orElseGet(() -> goalAccountRepository.findById(accountId)
                        .map(account -> (Account) account)
                        .orElse(null))));
    }

    @Override
    public TransactionResponseDTO findById(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFoundException("Transação não encontrada: " + transactionId));
        return TransactionMapper.toResponseDTO(transaction);
    }

    @Override
    public List<TransactionResponseDTO> findAll() {
        return transactionRepository.findAll().stream()
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> findByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId).stream()
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TransactionResponseDTO update(String transactionId, TransactionRequestDTO dto) {
        ValidatorUtil.validate(dto);

        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFoundException("Transação não encontrada: " + transactionId));

        Account account = findAccountById(dto.getAccountId());
        if (account == null) {
            throw new AccountNotFoundException("Conta não encontrada: " + dto.getAccountId());
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada: " + dto.getCategoryId()));

        transaction.setCode(dto.getCode());
        transaction.setName(dto.getName());
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setNatureOfTransaction(dto.getNatureOfTransaction());
        transaction.setReceiver(dto.getReceiver());
        transaction.setSender(dto.getSender());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setRepeatable(dto.isRepeatable());
        transaction.setRepeatableType(dto.getRepeatableType());
        transaction.setNote(dto.getNote());
        transaction.setDate();
        transaction.setAccount(account);
        transaction.setCategory(category);

        transactionRepository.update(transaction);

        return TransactionMapper.toResponseDTO(transaction);
    }

    @Override
    public void delete(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFoundException("Transação não encontrada: " + transactionId));
        
        transaction.setActive(false);
        transaction.setDate();
        
        transactionRepository.update(transaction);
    }
} 
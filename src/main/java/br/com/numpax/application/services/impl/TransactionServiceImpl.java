package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.request.TransactionUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.exceptions.CategoryNotFoundException;
import br.com.numpax.API.V1.exceptions.TransactionNotFoundException;
import br.com.numpax.API.V1.mappers.TransactionMapper;
import br.com.numpax.application.services.TransactionService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.repositories.AccountRepository;
import br.com.numpax.infrastructure.repositories.CategoryRepository;
import br.com.numpax.infrastructure.repositories.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                AccountRepository accountRepository,
                                CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {
        ValidatorUtil.validate(dto);

        Account account = accountRepository.findById(dto.getAccountId())
            .orElseThrow(() -> new AccountNotFoundException("Account not found: " + dto.getAccountId()));

        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + dto.getCategoryId()));

        Transaction transaction = TransactionMapper.toEntity(dto, category, account);
        transactionRepository.create(transaction);

        return TransactionMapper.toResponseDTO(transaction);
    }

    @Override
    public TransactionResponseDTO getTransactionById(String transactionId) {
        Transaction transaction = findTransactionOrThrow(transactionId);
        return TransactionMapper.toResponseDTO(transaction);
    }

    @Override
    public TransactionResponseDTO updateTransaction(String transactionId, TransactionUpdateRequestDTO dto) {
        ValidatorUtil.validate(dto);

        Transaction transaction = findTransactionOrThrow(transactionId);

        if (dto.getName() != null) transaction.setName(dto.getName());
        if (dto.getDescription() != null) transaction.setDescription(dto.getDescription());
        if (dto.getAmount() != null) transaction.setAmount(dto.getAmount());
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + dto.getCategoryId()));
            transaction.setCategory(category);
        }
        if (dto.getAccountId() != null) {
            Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + dto.getAccountId()));
            transaction.setAccount(account);
        }
        if (dto.getNatureOfTransaction() != null) transaction.setNatureOfTransaction(dto.getNatureOfTransaction());
        if (dto.getReceiver() != null) transaction.setReceiver(dto.getReceiver());
        if (dto.getSender() != null) transaction.setSender(dto.getSender());
        if (dto.getTransactionDate() != null) transaction.setTransactionDate(dto.getTransactionDate());
        if (dto.getIsRepeatable() != null) transaction.setRepeatable(dto.getIsRepeatable());
        if (dto.getRepeatableType() != null) transaction.setRepeatableType(dto.getRepeatableType());
        if (dto.getNote() != null) transaction.setNote(dto.getNote());

        transaction.setUpdatedAt(LocalDateTime.now());
        transactionRepository.update(transaction);

        return TransactionMapper.toResponseDTO(transaction);
    }

    @Override
    public void markAsEffective(String transactionId) {
        findTransactionOrThrow(transactionId);
        transactionRepository.markAsEffective(transactionId);
    }

    @Override
    public void deactivateTransaction(String transactionId) {
        Transaction transaction = findTransactionOrThrow(transactionId);
        transaction.setActive(false);
        transaction.setUpdatedAt(LocalDateTime.now());
        transactionRepository.update(transaction);
    }

    @Override
    public void deleteTransaction(String transactionId) {
        findTransactionOrThrow(transactionId);
        transactionRepository.delete(transactionId);
    }

    @Override
    public List<TransactionResponseDTO> listAllTransactions() {
        return transactionRepository.findAll().stream()
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> listAllActiveTransactions() {
        return transactionRepository.findAllActive().stream()
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> listAllInactiveTransactions() {
        return transactionRepository.findAllInactive().stream()
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
    public List<TransactionResponseDTO> findByCategoryId(String categoryId) {
        return transactionRepository.findByCategoryId(categoryId).stream()
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateRange(startDate, endDate).stream()
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> findEffectiveTransactions() {
        return transactionRepository.findEffectiveTransactions().stream()
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDTO> findPendingTransactions() {
        return transactionRepository.findPendingTransactions().stream()
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    private Transaction findTransactionOrThrow(String transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found: " + transactionId);
        }
        return transaction.get();
    }
} 
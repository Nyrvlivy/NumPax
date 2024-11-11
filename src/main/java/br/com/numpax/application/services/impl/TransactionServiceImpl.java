package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.ActiveTransactionDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.services.TransactionService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CheckingAccountRepository checkingAccountRepository;
    private final SavingsAccountRepository savingsAccountRepository;
    private final GoalAccountRepository goalAccountRepository;
    private final InvestmentAccountRepository investmentAccountRepository;
    private final CategoryRepository categoryRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CheckingAccountRepository checkingAccountRepository,
                                  SavingsAccountRepository savingsAccountRepository,
                                  GoalAccountRepository goalAccountRepository,
                                  InvestmentAccountRepository investmentAccountRepository,
                                  CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
        this.goalAccountRepository = goalAccountRepository;
        this.investmentAccountRepository = investmentAccountRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto, String accountId, String categoryId) {
        ValidatorUtil.validate(dto);

        Account account = findAccountById(accountId)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + accountId));

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + categoryId));

        if (!isTransactionAllowedForAccountType(dto.getNatureOfTransaction(), account)) {
            throw new RuntimeException("Tipo de transação não permitido para esta conta");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
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
        transaction.setActive(true);
        transaction.setEffective(false);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setAccount(account);
        transaction.setCategory(category);

        transactionRepository.create(transaction);

        return mapToResponseDTO(transaction);
    }

    @Override
    public List<ActiveTransactionDTO> listActiveTransactionsByUserId(String userId) {
        return listActiveTransactionsByUserId(userId, null);
    }

    @Override
    public List<ActiveTransactionDTO> listActiveTransactionsByUserId(String userId, String nature) {
        if (nature == null || nature.isEmpty()) {
            return transactionRepository.findActiveTransactionsByUserId(userId);
        } else {
            return transactionRepository.findActiveTransactionsByUserIdAndNature(userId, nature);
        }
    }

    private boolean isTransactionAllowedForAccountType(NatureOfTransaction natureOfTransaction, Account account) {
        return switch (account.getAccountType()) {
            case CHECKING -> natureOfTransaction == NatureOfTransaction.INCOME ||
                natureOfTransaction == NatureOfTransaction.EXPENSE ||
                natureOfTransaction == NatureOfTransaction.TRANSFER;
            case GOAL -> natureOfTransaction == NatureOfTransaction.GOAL_INCOME ||
                natureOfTransaction == NatureOfTransaction.GOAL_EXPENSE;
            case SAVINGS -> natureOfTransaction == NatureOfTransaction.SAVINGS;
            case INVESTMENT -> natureOfTransaction == NatureOfTransaction.INVESTMENTS;
            default -> false;
        };
    }

    private Optional<Account> findAccountById(String accountId) {
        return checkingAccountRepository.findById(accountId).map(account -> (Account) account)
            .or(() -> savingsAccountRepository.findById(accountId).map(account -> (Account) account))
            .or(() -> goalAccountRepository.findById(accountId).map(account -> (Account) account))
            .or(() -> investmentAccountRepository.findById(accountId).map(account -> (Account) account));
    }

    private TransactionResponseDTO mapToResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setCode(transaction.getCode());
        dto.setName(transaction.getName());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setCategory(transaction.getCategory());
        dto.setAccount(transaction.getAccount());
        dto.setNatureOfTransaction(transaction.getNatureOfTransaction());
        dto.setReceiver(transaction.getReceiver());
        dto.setSender(transaction.getSender());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setRepeatable(transaction.isRepeatable());
        dto.setRepeatableType(transaction.getRepeatableType());
        dto.setNote(transaction.getNote());
        dto.setActive(transaction.isActive());
        dto.setEffective(transaction.isEffective());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());
        return dto;
    }
}

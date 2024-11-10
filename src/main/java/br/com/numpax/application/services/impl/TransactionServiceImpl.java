package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.API.V1.dto.response.ActiveTransactionDTO;
import br.com.numpax.application.services.TransactionService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.*;
import br.com.numpax.infrastructure.repositories.*;
import br.com.numpax.application.enums.NatureOfTransaction;

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
        // Validação do DTO
        ValidatorUtil.validate(dto);

        // Buscar a conta associada
        Account account = findAccountById(accountId)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada: " + accountId));

        // Buscar a categoria associada
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + categoryId));

        // Verificar se a natureza da transação é permitida para o tipo da conta
        if (!isTransactionAllowedForAccountType(dto.getNatureOfTransaction(), account)) {
            throw new RuntimeException("Tipo de transação não permitido para esta conta");
        }

        // Criar a entidade Transaction
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

        // Persistir a transação no banco de dados
        transactionRepository.create(transaction);

        // Mapear a entidade para o DTO de resposta
        return mapToResponseDTO(transaction);
    }

    @Override
    public List<ActiveTransactionDTO> listActiveTransactionsByUserId(String userId) {
        // (Opcional) Validar se o usuário existe e está ativo
        // Por exemplo, você pode injetar e utilizar um UserRepository para essa validação

        // Buscar as transações ativas com as naturezas especificadas
        return transactionRepository.findActiveTransactionsByUserId(userId);
    }

    /**
     * Verifica se a natureza da transação é permitida para o tipo da conta.
     *
     * @param natureOfTransaction A natureza da transação.
     * @param account             A conta associada.
     * @return true se permitido, false caso contrário.
     */
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

    /**
     * Busca uma conta pelo seu ID, verificando em todos os tipos de contas.
     *
     * @param accountId O ID da conta.
     * @return Um Optional contendo a conta se encontrada.
     */
    private Optional<Account> findAccountById(String accountId) {
        return checkingAccountRepository.findById(accountId).map(account -> (Account) account)
            .or(() -> savingsAccountRepository.findById(accountId).map(account -> (Account) account))
            .or(() -> goalAccountRepository.findById(accountId).map(account -> (Account) account))
            .or(() -> investmentAccountRepository.findById(accountId).map(account -> (Account) account));
    }

    /**
     * Mapeia a entidade Transaction para o DTO de resposta.
     *
     * @param transaction A entidade Transaction.
     * @return O DTO de resposta.
     */
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

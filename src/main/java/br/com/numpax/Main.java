package br.com.numpax;

import br.com.numpax.API.V1.dto.request.*;
import br.com.numpax.API.V1.dto.response.*;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;
import br.com.numpax.application.services.*;
import br.com.numpax.application.services.impl.*;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.repositories.*;
import br.com.numpax.infrastructure.repositories.impl.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando testes do sistema financeiro...");

        // Obter a conexão do ConnectionManager
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection();

        try {
            // Inicializar repositórios e serviços
            UserRepository userRepository = new UserRepositoryImpl(connection);
            CategoryRepository categoryRepository = new CategoryRepositoryImpl(connection);
            CheckingAccountRepository checkingAccountRepository = new CheckingAccountRepositoryImpl(connection);
            SavingsAccountRepository savingsAccountRepository = new SavingsAccountRepositoryImpl(connection);
            InvestmentAccountRepository investmentAccountRepository = new InvestmentAccountRepositoryImpl(connection);
            GoalAccountRepository goalAccountRepository = new GoalAccountRepositoryImpl(connection);
            TransactionRepository transactionRepository = new TransactionRepositoryImpl(connection);

            UserService userService = new UserServiceImpl(userRepository, checkingAccountRepository);
            CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
            CheckingAccountService checkingAccountService = new CheckingAccountServiceImpl(checkingAccountRepository, userService);
            SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl(savingsAccountRepository, userService);
            InvestmentAccountService investmentAccountService = new InvestmentAccountServiceImpl(investmentAccountRepository, userService);
            GoalAccountService goalAccountService = new GoalAccountServiceImpl(goalAccountRepository, userService, categoryService);
            TransactionService transactionService = new TransactionServiceImpl(transactionRepository, checkingAccountRepository, savingsAccountRepository, goalAccountRepository, investmentAccountRepository, categoryRepository);

            // Criar categorias padrão
            categoryService.createDefaultCategories();
            System.out.println("Categorias padrão criadas.");

            // Criar um novo usuário
            UserRequestDTO userRequest = new UserRequestDTO();
            userRequest.setName("Usuário Teste 1");
            userRequest.setEmail(generateRandomEmail());
            userRequest.setPassword("SenhaForte123!");
            userRequest.setBirthdate(LocalDate.of(1985, 8, 20));

            UserResponseDTO userResponse = userService.createUser(userRequest);
            String userId = userResponse.getUserId();
            System.out.println("Usuário criado: " + userResponse);

            // Criar uma conta corrente
            CheckingAccountRequestDTO checkingAccountRequest = new CheckingAccountRequestDTO();
            checkingAccountRequest.setName("Conta Corrente Principal");
            checkingAccountRequest.setDescription("Conta corrente para despesas diárias");
            checkingAccountRequest.setAccountType(AccountType.CHECKING);
            checkingAccountRequest.setBankCode("001");
            checkingAccountRequest.setAgency("1234");
            checkingAccountRequest.setAccountNumber("263748573627");
            CheckingAccountResponseDTO checkingAccountResponse = checkingAccountService.createAccount(checkingAccountRequest, userId);
            System.out.println("Conta corrente criada: " + checkingAccountResponse);

            // Criar uma conta poupança
            SavingsAccountRequestDTO savingsAccountRequest = new SavingsAccountRequestDTO();
            savingsAccountRequest.setName("Poupança");
            savingsAccountRequest.setDescription("Conta poupança para economias");
            SavingsAccountResponseDTO savingsAccountResponse = savingsAccountService.createAccount(savingsAccountRequest, userId);
            System.out.println("Conta poupança criada: " + savingsAccountResponse);

            // Criar uma conta de investimento
            InvestmentAccountRequestDTO investmentAccountRequest = new InvestmentAccountRequestDTO();
            investmentAccountRequest.setName("Investimentos");
            investmentAccountRequest.setDescription("Conta para investimentos em ações");
            InvestmentAccountResponseDTO investmentAccountResponse = investmentAccountService.createAccount(investmentAccountRequest, userId);
            System.out.println("Conta de investimento criada: " + investmentAccountResponse);

            // Criar uma conta de meta, se uma categoria estiver disponível
            CategoryResponseDTO categoryResponse = categoryService.listAllCategories().stream().findFirst().orElse(null);
            GoalAccountResponseDTO goalAccountResponse = null;
            if (categoryResponse != null) {
                GoalAccountRequestDTO goalAccountRequest = new GoalAccountRequestDTO();
                goalAccountRequest.setName("Viagem dos Sonhos");
                goalAccountRequest.setDescription("Economizar para uma viagem internacional");
                goalAccountRequest.setTargetValue(new BigDecimal("15000"));
                goalAccountRequest.setAmountValue(new BigDecimal("5000"));
                goalAccountRequest.setTargetTaxRate(new BigDecimal("0.03"));
                goalAccountRequest.setMonthlyTaxRate(new BigDecimal("0.0025"));
                goalAccountRequest.setMonthlyEstimate(new BigDecimal("1000"));
                goalAccountRequest.setMonthlyAchievement(new BigDecimal("800"));
                goalAccountRequest.setCategoryId(categoryResponse.getId());
                goalAccountRequest.setTargetDate(LocalDate.now().plusYears(2));
                goalAccountRequest.setStartDate(LocalDate.now());

                goalAccountResponse = goalAccountService.createAccount(goalAccountRequest, userId);
                System.out.println("Conta de meta criada: " + goalAccountResponse);
            } else {
                System.out.println("Nenhuma categoria disponível.");
            }

            // Testar transações se a conta de meta foi criada
            if (goalAccountResponse != null) {
                testTransactions(transactionService, checkingAccountResponse, goalAccountResponse, categoryService);
            } else {
                System.out.println("Conta de meta não foi criada. Transações de meta não serão testadas.");
            }

            // Listar transações ativas do usuário
            System.out.println("\nListando transações ativas para o usuário ID: " + userId);
            List<ActiveTransactionDTO> activeTransactions = transactionService.listActiveTransactionsByUserId(userId);
            for (ActiveTransactionDTO transaction : activeTransactions) {
                System.out.println(transaction);
            }

            // Deletar usuário
            userService.deleteUser(userId);
            System.out.println("Usuário deletado.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionManager.closeConnection();
        }
    }

    private static void testTransactions(TransactionService transactionService,
                                         CheckingAccountResponseDTO checkingAccountResponse,
                                         GoalAccountResponseDTO goalAccountResponse,
                                         CategoryService categoryService) {

        String checkingAccountId = checkingAccountResponse.getAccountId();
        String goalAccountId = goalAccountResponse.getAccountId();
        String categoryId = categoryService.listAllCategories().get(0).getId();

        // INCOME
        TransactionRequestDTO incomeTransaction = new TransactionRequestDTO();
        incomeTransaction.setCode("INC-001");
        incomeTransaction.setName("Salário");
        incomeTransaction.setDescription("Depósito de salário");
        incomeTransaction.setAmount(new BigDecimal("5000"));
        incomeTransaction.setNatureOfTransaction(NatureOfTransaction.INCOME);
        incomeTransaction.setTransactionDate(LocalDate.now());
        incomeTransaction.setAccountId(checkingAccountId);
        incomeTransaction.setCategoryId(categoryId);
        incomeTransaction.setRepeatable(false);
        incomeTransaction.setRepeatableType(RepeatableType.NEVER);
        TransactionResponseDTO incomeResponse = transactionService.createTransaction(incomeTransaction, checkingAccountId, categoryId);
        System.out.println("Transação INCOME criada: " + incomeResponse);

        // EXPENSE
        TransactionRequestDTO expenseTransaction = new TransactionRequestDTO();
        expenseTransaction.setCode("EXP-001");
        expenseTransaction.setName("Aluguel");
        expenseTransaction.setDescription("Pagamento do aluguel");
        expenseTransaction.setAmount(new BigDecimal("1200"));
        expenseTransaction.setNatureOfTransaction(NatureOfTransaction.EXPENSE);
        expenseTransaction.setTransactionDate(LocalDate.now());
        expenseTransaction.setAccountId(checkingAccountId);
        expenseTransaction.setCategoryId(categoryId);
        expenseTransaction.setRepeatable(false);
        expenseTransaction.setRepeatableType(RepeatableType.NEVER);
        TransactionResponseDTO expenseResponse = transactionService.createTransaction(expenseTransaction, checkingAccountId, categoryId);
        System.out.println("Transação EXPENSE criada: " + expenseResponse);

        // TRANSFER
        TransactionRequestDTO transferTransaction = new TransactionRequestDTO();
        transferTransaction.setCode("TRF-001");
        transferTransaction.setName("Transferência para Poupança");
        transferTransaction.setDescription("Transferência para conta poupança");
        transferTransaction.setAmount(new BigDecimal("500"));
        transferTransaction.setNatureOfTransaction(NatureOfTransaction.TRANSFER);
        transferTransaction.setTransactionDate(LocalDate.now());
        transferTransaction.setAccountId(checkingAccountId);
        transferTransaction.setCategoryId(categoryId);
        transferTransaction.setRepeatable(false);
        transferTransaction.setRepeatableType(RepeatableType.NEVER);
        TransactionResponseDTO transferResponse = transactionService.createTransaction(transferTransaction, checkingAccountId, categoryId);
        System.out.println("Transação TRANSFER criada: " + transferResponse);

        // GOAL_INCOME
        TransactionRequestDTO goalIncomeTransaction = new TransactionRequestDTO();
        goalIncomeTransaction.setCode("GOAL-INC-001");
        goalIncomeTransaction.setName("Depósito de Meta");
        goalIncomeTransaction.setDescription("Depósito para meta de viagem");
        goalIncomeTransaction.setAmount(new BigDecimal("1000"));
        goalIncomeTransaction.setNatureOfTransaction(NatureOfTransaction.GOAL_INCOME);
        goalIncomeTransaction.setTransactionDate(LocalDate.now());
        goalIncomeTransaction.setAccountId(goalAccountId);
        goalIncomeTransaction.setCategoryId(categoryId);
        goalIncomeTransaction.setRepeatable(true);
        goalIncomeTransaction.setRepeatableType(RepeatableType.NEVER);
        TransactionResponseDTO goalIncomeResponse = transactionService.createTransaction(goalIncomeTransaction, goalAccountId, categoryId);
        System.out.println("Transação GOAL_INCOME criada: " + goalIncomeResponse);

        // GOAL_EXPENSE
        TransactionRequestDTO goalExpenseTransaction = new TransactionRequestDTO();
        goalExpenseTransaction.setCode("GOAL-EXP-001");
        goalExpenseTransaction.setName("Despesas de Meta");
        goalExpenseTransaction.setDescription("Pagamento de despesas da meta de viagem");
        goalExpenseTransaction.setAmount(new BigDecimal("300"));
        goalExpenseTransaction.setNatureOfTransaction(NatureOfTransaction.GOAL_EXPENSE);
        goalExpenseTransaction.setTransactionDate(LocalDate.now());
        goalExpenseTransaction.setAccountId(goalAccountId);
        goalExpenseTransaction.setCategoryId(categoryId);
        goalExpenseTransaction.setRepeatable(true);
        goalExpenseTransaction.setRepeatableType(RepeatableType.NEVER);
        TransactionResponseDTO goalExpenseResponse = transactionService.createTransaction(goalExpenseTransaction, goalAccountId, categoryId);
        System.out.println("Transação GOAL_EXPENSE criada: " + goalExpenseResponse);
    }

    private static String generateRandomEmail() {
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        return "user_" + randomPart + "@example.com";
    }
}

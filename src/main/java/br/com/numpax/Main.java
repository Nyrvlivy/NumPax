package br.com.numpax;

import br.com.numpax.API.V1.dto.request.*;
import br.com.numpax.API.V1.dto.response.*;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.services.FixedInvestmentService;
import br.com.numpax.application.services.impl.*;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.repositories.impl.*;
import br.com.numpax.infrastructure.repositories.TransactionRepository;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;
import br.com.numpax.infrastructure.repositories.CategoryRepository;
import br.com.numpax.application.services.AuthService;
import br.com.numpax.application.services.TransactionService;
import br.com.numpax.application.services.impl.TransactionServiceImpl;
import br.com.numpax.application.enums.TransactionType;
import br.com.numpax.infrastructure.repositories.impl.TransactionRepositoryImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando testes do sistema financeiro...");

        // Obter a conexão do ConnectionManager
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection();

        try {
            // Inicializar repositórios
            UserRepositoryImpl userRepository = new UserRepositoryImpl(connection);
            CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl(connection);
            CheckingAccountRepositoryImpl checkingAccountRepository = new CheckingAccountRepositoryImpl(connection);
            SavingsAccountRepositoryImpl savingsAccountRepository = new SavingsAccountRepositoryImpl(connection);
            InvestmentAccountRepositoryImpl investmentAccountRepository = new InvestmentAccountRepositoryImpl(connection);
            GoalAccountRepositoryImpl goalAccountRepository = new GoalAccountRepositoryImpl(connection);
            TransactionRepositoryImpl transactionRepository = new TransactionRepositoryImpl(connection);

            // Inicializar serviços
            UserServiceImpl userService = new UserServiceImpl(userRepository, checkingAccountRepository);
            CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);
            CheckingAccountServiceImpl checkingAccountService = new CheckingAccountServiceImpl(checkingAccountRepository, userService);
            SavingsAccountServiceImpl savingsAccountService = new SavingsAccountServiceImpl(savingsAccountRepository, userService);
            InvestmentAccountServiceImpl investmentAccountService = new InvestmentAccountServiceImpl(investmentAccountRepository, userService);
            GoalAccountServiceImpl goalAccountService = new GoalAccountServiceImpl(goalAccountRepository, userService, categoryService);
            TransactionServiceImpl transactionService = new TransactionServiceImpl(
                transactionRepository,
                checkingAccountRepository,
                savingsAccountRepository,
                investmentAccountRepository,
                goalAccountRepository,
                categoryRepository
            );
            AuthService authService = new AuthServiceImpl(userRepository);

            // Criar categorias padrão
            categoryService.createDefaultCategories();
            System.out.println("Categorias padrão criadas.");

            // Criar um novo usuário
            UserRequestDTO userRequest = new UserRequestDTO();
            userRequest.setName("Frederico Adriel");
            userRequest.setEmail(generateRandomEmail());
            userRequest.setPassword("SenhaForte123!");
            userRequest.setBirthdate(LocalDate.of(1985, 8, 20));

            UserResponseDTO userResponse = userService.createUser(userRequest);
            System.out.println("Usuário criado: " + userResponse);

            String userId = userResponse.getUserId();

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
            savingsAccountRequest.setNearestDeadline(LocalDateTime.now().plusMonths(6));
            savingsAccountRequest.setFurthestDeadline(LocalDateTime.now().plusYears(5));
            savingsAccountRequest.setLatestDeadline(LocalDateTime.now().plusYears(10));
            savingsAccountRequest.setAverageTaxRate(new BigDecimal("0.05"));
            savingsAccountRequest.setNumberOfFixedInvestments(2);
            savingsAccountRequest.setTotalMaturityAmount(new BigDecimal("10000"));
            savingsAccountRequest.setTotalDepositAmount(new BigDecimal("5000"));

            SavingsAccountResponseDTO savingsAccountResponse = savingsAccountService.createAccount(savingsAccountRequest, userId);
            System.out.println("Conta poupança criada: " + savingsAccountResponse);

            // Criar uma conta de investimento
            InvestmentAccountRequestDTO investmentAccountRequest = new InvestmentAccountRequestDTO();
            investmentAccountRequest.setName("Investimentos");
            investmentAccountRequest.setDescription("Conta para investimentos em ações");

            InvestmentAccountResponseDTO investmentAccountResponse = investmentAccountService.createAccount(investmentAccountRequest, userId);
            System.out.println("Conta de investimento criada: " + investmentAccountResponse);

            // Obter uma categoria de investimento
            List<CategoryResponseDTO> categories = categoryService.listAllCategories();
            CategoryResponseDTO investmentCategory = categories.stream()
                .filter(cat -> cat.getName().toLowerCase().contains("investimento"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Categoria de investimento não encontrada"));

            // Criar um investimento fixo
            TransactionRequestDTO fixedInvestmentRequest = new TransactionRequestDTO();
            fixedInvestmentRequest.setCode("CDB-001");
            fixedInvestmentRequest.setName("CDB Banco XYZ");
            fixedInvestmentRequest.setDescription("CDB pré-fixado 12% a.a.");
            fixedInvestmentRequest.setAmount(new BigDecimal("10000.00"));
            fixedInvestmentRequest.setAccountId(investmentAccountResponse.getAccountId());
            fixedInvestmentRequest.setCategoryId(investmentCategory.getId());
            fixedInvestmentRequest.setTransactionDate(LocalDate.now());
            fixedInvestmentRequest.setNatureOfTransaction(NatureOfTransaction.INVESTMENT);
            
            FixedInvestmentService fixedInvestmentService = new FixedInvestmentServiceImpl(
                transactionRepository, 
                investmentAccountRepository
            );
            
            TransactionResponseDTO fixedInvestmentResponse = fixedInvestmentService.createInvestment(fixedInvestmentRequest);
            System.out.println("Investimento fixo criado: " + fixedInvestmentResponse);

            // Simular resgate após 30 dias
            Thread.sleep(2000); // Simula passagem de tempo
            fixedInvestmentService.redeem(fixedInvestmentResponse.getTransactionId());
            System.out.println("Investimento resgatado com sucesso!");

            // Criar uma conta de meta

            // Obter uma categoria
            CategoryResponseDTO categoryResponse = categoryService.listAllCategories().stream().findFirst().orElse(null);
            if (categoryResponse == null) {
                System.out.println("Nenhuma categoria disponível.");
            } else {
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

                GoalAccountResponseDTO goalAccountResponse = goalAccountService.createAccount(goalAccountRequest, userId);
                System.out.println("Conta de meta criada: " + goalAccountResponse);
            }

            // Atualizar usuário
            UserUpdateRequestDTO userUpdateRequest = new UserUpdateRequestDTO();
            userUpdateRequest.setName("Maria Silva Atualizada");
            userUpdateRequest.setEmail(userResponse.getEmail()); // Mantém o mesmo email
            userUpdateRequest.setPassword("NovaSenhaForte123!");
            userUpdateRequest.setBirthdate(LocalDate.of(1985, 8, 20));

            UserResponseDTO updatedUser = userService.updateUser(userId, userUpdateRequest);
            System.out.println("Usuário atualizado: " + updatedUser);

            // Listar todos os usuários
            System.out.println("Lista de todos os usuários:");
            userService.listAllUsers().forEach(System.out::println);

            // Deletar usuário
            userService.deleteUser(userId);
            System.out.println("Usuário deletado.");

            // Teste de Login
            System.out.println("\n=== Testando Autenticação ===");
            
            // Criar usuário para teste
            UserRequestDTO loginTestUser = new UserRequestDTO();
            loginTestUser.setName("Usuário Teste");
            loginTestUser.setEmail("teste@email.com");
            loginTestUser.setPassword("Senha@123");
            loginTestUser.setBirthdate(LocalDate.of(1990, 1, 1));
            
            UserResponseDTO loginUserResponse = userService.createUser(loginTestUser);
            System.out.println("Usuário de teste criado: " + loginUserResponse);

            // Teste de login
            LoginRequestDTO loginRequest = new LoginRequestDTO();
            loginRequest.setEmail("teste@email.com");
            loginRequest.setPassword("Senha@123");

            AuthResponseDTO authResponse = authService.login(loginRequest);
            System.out.println("Login realizado com sucesso!");
            System.out.println("Access Token: " + authResponse.getAccessToken());
            System.out.println("Refresh Token: " + authResponse.getRefreshToken());

            // Teste de refresh token
            AuthResponseDTO refreshResponse = authService.refreshToken(authResponse.getRefreshToken());
            System.out.println("Tokens renovados com sucesso!");

            // Teste de Transações
            System.out.println("\n=== Testando Transações ===");

            // Criar conta para teste
            CheckingAccountRequestDTO testAccountRequest = new CheckingAccountRequestDTO();
            testAccountRequest.setName("Conta Teste");
            testAccountRequest.setDescription("Conta para teste de transação");
            testAccountRequest.setAccountType(AccountType.CHECKING);
            testAccountRequest.setBankCode("001");
            testAccountRequest.setAgency("1234");
            testAccountRequest.setAccountNumber("11111111");

            CheckingAccountResponseDTO testAccount = checkingAccountService.createAccount(
                testAccountRequest, 
                loginUserResponse.getUserId()
            );

            // Criar transação
            TransactionRequestDTO transactionRequest = new TransactionRequestDTO();
            transactionRequest.setCode(UUID.randomUUID().toString());
            transactionRequest.setName("Depósito Inicial");
            transactionRequest.setDescription("Depósito inicial");
            transactionRequest.setAmount(new BigDecimal("100.00"));
            transactionRequest.setNatureOfTransaction(NatureOfTransaction.INCOME);
            transactionRequest.setTransactionDate(LocalDate.now());
            transactionRequest.setType(TransactionType.DEPOSIT);
            transactionRequest.setAccountId(testAccount.getAccountId());
            transactionRequest.setCategoryId(categoryService.findByName("Receitas Diversas").getId());

            TransactionResponseDTO transactionResponse = transactionService.createTransaction(
                transactionRequest
            );
            System.out.println("Transação criada: " + transactionResponse);

            // Verificar saldo após transação
            CheckingAccountResponseDTO updatedAccount = checkingAccountService.getAccountById(
                testAccount.getAccountId()
            );
            System.out.println("Saldo atual: " + updatedAccount.getBalance());

            // Limpar dados de teste
            transactionService.delete(transactionResponse.getTransactionId());
            checkingAccountService.deleteAccount(testAccount.getAccountId());
            userService.deleteUser(loginUserResponse.getUserId());
            System.out.println("Dados de teste removidos com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fechar a conexão
            connectionManager.closeConnection();
        }
    }

    private static String generateRandomEmail() {
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        return "user_" + randomPart + "@example.com";
    }
}

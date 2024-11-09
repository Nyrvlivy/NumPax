package br.com.numpax;

import br.com.numpax.API.V1.dto.request.*;
import br.com.numpax.API.V1.dto.response.*;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.services.impl.*;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.repositories.impl.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Main {
    private static UserServiceImpl userService;
    private static CategoryServiceImpl categoryService;
    private static CheckingAccountServiceImpl checkingAccountService;
    private static SavingsAccountServiceImpl savingsAccountService;
    private static InvestmentAccountServiceImpl investmentAccountService;
    private static GoalAccountServiceImpl goalAccountService;
    private static FixedInvestmentServiceImpl fixedInvestmentService;
    private static VariableInvestmentServiceImpl variableInvestmentService;
    private static TransactionServiceImpl transactionService;

    public static void main(String[] args) {
        System.out.println("Iniciando testes do sistema financeiro...");
        setupServices();

        try {
            // 1. Teste do fluxo de usuário
            String userId = testUserFlow();

            // 2. Teste de categorias
            String categoryId = testCategoryFlow();

            // 3. Teste de contas
            testAccountsFlow(userId, categoryId);

            // 4. Teste de investimentos
            testInvestmentsFlow(userId);

            // 5. Teste de transações
            testTransactionsFlow(userId);

            // 6. Teste de metas financeiras
            testGoalsFlow(userId);

            // 7. Teste de relatórios
            testReportsFlow(userId);

            // 8. Teste de segurança
            testSecurityFlow();

            // 9. Teste de integração
            testSystemIntegration();

            System.out.println("Todos os testes concluídos com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.getInstance().closeConnection();
        }
    }

    private static void setupServices() {
        Connection connection = ConnectionManager.getInstance().getConnection();
        
        // Inicialização dos repositórios
        UserRepositoryImpl userRepo = new UserRepositoryImpl(connection);
        CategoryRepositoryImpl categoryRepo = new CategoryRepositoryImpl(connection);
        CheckingAccountRepositoryImpl checkingRepo = new CheckingAccountRepositoryImpl(connection);
        SavingsAccountRepositoryImpl savingsRepo = new SavingsAccountRepositoryImpl(connection);
        InvestmentAccountRepositoryImpl investmentRepo = new InvestmentAccountRepositoryImpl(connection);
        GoalAccountRepositoryImpl goalRepo = new GoalAccountRepositoryImpl(connection);
        FixedInvestmentRepositoryImpl fixedInvestRepo = new FixedInvestmentRepositoryImpl(connection);
        VariableInvestmentRepositoryImpl varInvestRepo = new VariableInvestmentRepositoryImpl(connection);
        TransactionRepositoryImpl transactionRepo = new TransactionRepositoryImpl(connection);

        // Inicialização dos serviços
        userService = new UserServiceImpl(userRepo, checkingRepo);
        categoryService = new CategoryServiceImpl(categoryRepo);
        checkingAccountService = new CheckingAccountServiceImpl(checkingRepo, userService);
        savingsAccountService = new SavingsAccountServiceImpl(savingsRepo, userService);
        investmentAccountService = new InvestmentAccountServiceImpl(investmentRepo, userService);
        goalAccountService = new GoalAccountServiceImpl(goalRepo, userService, categoryService);
        fixedInvestmentService = new FixedInvestmentServiceImpl(
            fixedInvestRepo, 
            transactionManager,
            userService
        );
        variableInvestmentService = new VariableInvestmentServiceImpl(varInvestRepo, userService);
        transactionService = new TransactionServiceImpl(transactionRepo, userService);
    }

    private static String testUserFlow() {
        System.out.println("\n=== Testando fluxo de usuário ===");
        
        // Criar usuário
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setName("Teste Completo");
        userRequest.setEmail(generateRandomEmail());
        userRequest.setPassword("Senha@123");
        userRequest.setBirthdate(LocalDate.of(1990, 1, 1));

        UserResponseDTO userResponse = userService.createUser(userRequest);
        System.out.println("Usuário criado: " + userResponse.getName());

        // Login
        try {
            User authenticatedUser = userService.authenticateUser(userRequest.getEmail(), userRequest.getPassword());
            System.out.println("Login realizado com sucesso: " + authenticatedUser.getName());
        } catch (AuthenticationException e) {
            System.err.println("Erro no login: " + e.getMessage());
        }

        return userResponse.getUserId();
    }

    private static String testCategoryFlow() {
        System.out.println("\n=== Testando fluxo de categorias ===");
        
        categoryService.createDefaultCategories();
        List<CategoryResponseDTO> categories = categoryService.listAllCategories();
        System.out.println("Categorias criadas: " + categories.size());

        return categories.get(0).getId();
    }

    private static void testAccountsFlow(String userId, String categoryId) {
        System.out.println("\n=== Testando fluxo de contas ===");

        try {
            // 1. Teste de Conta Corrente
            String checkingAccountId = testCheckingAccount(userId);
            testCheckingAccountOperations(checkingAccountId);
            
            // 2. Teste de Conta Poupança
            String savingsAccountId = testSavingsAccount(userId);
            testSavingsAccountOperations(savingsAccountId);
            
            // 3. Teste de Conta Investimento
            String investmentAccountId = testInvestmentAccount(userId);
            testInvestmentAccountOperations(investmentAccountId);
            
            // 4. Teste de Conta Meta
            String goalAccountId = testGoalAccount(userId, categoryId);
            testGoalAccountOperations(goalAccountId);

            // 5. Teste de validações de erro
            testAccountValidations(userId);
            
            System.out.println("Testes de contas concluídos com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de contas: " + e.getMessage());
            throw e;
        }
    }

    private static String testCheckingAccount(String userId) {
        System.out.println("\nTestando Conta Corrente:");
        
        // Criar conta corrente
        CheckingAccountRequestDTO createRequest = new CheckingAccountRequestDTO();
        createRequest.setName("Conta Corrente Principal");
        createRequest.setDescription("Minha conta corrente principal");
        createRequest.setBankCode("001");
        createRequest.setAgency("1234");
        createRequest.setAccountNumber("123456");
        createRequest.setBalance(BigDecimal.ZERO);
        
        CheckingAccountResponseDTO createResponse = 
            checkingAccountService.createAccount(createRequest, userId);
        System.out.println("Conta corrente criada: " + createResponse.getName());
        
        // Atualizar conta corrente
        CheckingAccountUpdateRequestDTO updateRequest = new CheckingAccountUpdateRequestDTO();
        updateRequest.setName("Conta Corrente Atualizada");
        updateRequest.setDescription("Descrição atualizada");
        
        CheckingAccountResponseDTO updateResponse = 
            checkingAccountService.updateAccount(createResponse.getAccountId(), updateRequest);
        System.out.println("Conta corrente atualizada: " + updateResponse.getName());
        
        // Listar contas correntes do usuário
        List<CheckingAccountResponseDTO> accounts = checkingAccountService.listAccountsByUserId(userId);
        System.out.println("Total de contas correntes: " + accounts.size());
        
        return createResponse.getAccountId();
    }

    private static String testSavingsAccount(String userId) {
        System.out.println("\nTestando Conta Poupança:");
        
        // Criar conta poupança
        SavingsAccountRequestDTO createRequest = new SavingsAccountRequestDTO();
        createRequest.setName("Poupança Principal");
        createRequest.setDescription("Minha conta poupança");
        createRequest.setBankCode("001");
        createRequest.setAgency("1234");
        createRequest.setAccountNumber("654321");
        createRequest.setBalance(BigDecimal.ZERO);
        createRequest.setInterestRate(new BigDecimal("0.005")); // 0.5% ao mês
        
        SavingsAccountResponseDTO createResponse = 
            savingsAccountService.createAccount(createRequest, userId);
        System.out.println("Conta poupança criada: " + createResponse.getName());
        
        // Atualizar conta poupança
        SavingsAccountUpdateRequestDTO updateRequest = new SavingsAccountUpdateRequestDTO();
        updateRequest.setName("Poupança Atualizada");
        updateRequest.setInterestRate(new BigDecimal("0.006")); // 0.6% ao mês
        
        SavingsAccountResponseDTO updateResponse = 
            savingsAccountService.updateAccount(createResponse.getAccountId(), updateRequest);
        System.out.println("Conta poupança atualizada: " + updateResponse.getName());
        
        return createResponse.getAccountId();
    }

    private static String testInvestmentAccount(String userId) {
        System.out.println("\nTestando Conta Investimento:");
        
        // Criar conta investimento
        InvestmentAccountRequestDTO createRequest = new InvestmentAccountRequestDTO();
        createRequest.setName("Conta Investimento");
        createRequest.setDescription("Conta para investimentos");
        createRequest.setBankCode("001");
        createRequest.setAgency("1234");
        createRequest.setAccountNumber("789012");
        createRequest.setBalance(BigDecimal.ZERO);
        createRequest.setBrokerName("XP Investimentos");
        
        InvestmentAccountResponseDTO createResponse = 
            investmentAccountService.createAccount(createRequest, userId);
        System.out.println("Conta investimento criada: " + createResponse.getName());
        
        // Atualizar conta investimento
        InvestmentAccountUpdateRequestDTO updateRequest = new InvestmentAccountUpdateRequestDTO();
        updateRequest.setName("Conta Investimento Atualizada");
        updateRequest.setBrokerName("BTG Pactual");
        
        InvestmentAccountResponseDTO updateResponse = 
            investmentAccountService.updateAccount(createResponse.getAccountId(), updateRequest);
        System.out.println("Conta investimento atualizada: " + updateResponse.getName());
        
        return createResponse.getAccountId();
    }

    private static String testGoalAccount(String userId, String categoryId) {
        System.out.println("\nTestando Conta Meta:");
        
        // Criar conta meta
        GoalAccountRequestDTO createRequest = new GoalAccountRequestDTO();
        createRequest.setName("Meta Viagem");
        createRequest.setDescription("Meta para viagem de férias");
        createRequest.setTargetAmount(new BigDecimal("10000.00"));
        createRequest.setTargetDate(LocalDate.now().plusMonths(12));
        createRequest.setCategoryId(categoryId);
        
        GoalAccountResponseDTO createResponse = 
            goalAccountService.createAccount(createRequest, userId);
        System.out.println("Conta meta criada: " + createResponse.getName());
        
        // Atualizar conta meta
        GoalAccountUpdateRequestDTO updateRequest = new GoalAccountUpdateRequestDTO();
        updateRequest.setName("Meta Viagem Atualizada");
        updateRequest.setTargetAmount(new BigDecimal("12000.00"));
        updateRequest.setTargetDate(LocalDate.now().plusMonths(15));
        
        GoalAccountResponseDTO updateResponse = 
            goalAccountService.updateAccount(createResponse.getAccountId(), updateRequest);
        System.out.println("Conta meta atualizada: " + updateResponse.getName());
        
        // Testar progresso da meta
        BigDecimal progress = goalAccountService.calculateProgress(createResponse.getAccountId());
        System.out.println("Progresso da meta: " + progress + "%");
        
        return createResponse.getAccountId();
    }

    private static void testInvestmentsFlow(String userId) {
        System.out.println("\n=== Testando fluxo de investimentos ===");

        try {
            // 1. Investimentos Fixos
            String fixedInvestmentId = testFixedInvestment(userId);
            testFixedInvestmentOperations(fixedInvestmentId);

            // 2. Investimentos Variáveis
            String variableInvestmentId = testVariableInvestment(userId);
            testVariableInvestmentOperations(variableInvestmentId);

            // 3. Teste de validações
            testInvestmentValidations(userId);

            System.out.println("Testes de investimentos concluídos com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de investimentos: " + e.getMessage());
            throw e;
        }
    }

    private static String testFixedInvestment(String userId) {
        System.out.println("\nTestando Investimento Fixo:");
        
        // Criar investimento fixo
        FixedInvestmentRequestDTO createRequest = new FixedInvestmentRequestDTO();
        createRequest.setCode("CDB001");
        createRequest.setName("CDB Banco XYZ");
        createRequest.setDescription("CDB pré-fixado 12 meses");
        createRequest.setAmount(new BigDecimal("10000.00"));
        createRequest.setCategoryId("CAT001");
        createRequest.setAccountId("ACC001");
        createRequest.setInvestmentDate(LocalDate.now());
        createRequest.setFixedInvestmentType("CDB");
        createRequest.setExpirationDate(LocalDate.now().plusMonths(12));
        createRequest.setInstitution("Banco XYZ");
        createRequest.setInterestRate(new BigDecimal("12.5"));
        createRequest.setLiquidityPeriod(90);
        
        FixedInvestmentResponseDTO createResponse = 
            fixedInvestmentService.createInvestment(createRequest, userId);
        System.out.println("Investimento fixo criado: " + createResponse.getName());
        
        // Atualizar investimento
        FixedInvestmentUpdateRequestDTO updateRequest = new FixedInvestmentUpdateRequestDTO();
        updateRequest.setName("CDB Banco XYZ - Atualizado");
        updateRequest.setDescription("CDB pré-fixado 12 meses - Taxa atualizada");
        
        FixedInvestmentResponseDTO updateResponse = 
            fixedInvestmentService.updateInvestment(createResponse.getInvestmentId(), updateRequest);
        System.out.println("Investimento fixo atualizado: " + updateResponse.getName());
        
        return createResponse.getInvestmentId();
    }

    private static void testFixedInvestmentOperations(String investmentId) {
        System.out.println("\nTestando operações de Investimento Fixo:");
        
        // Calcular rendimento atual
        BigDecimal currentEarnings = fixedInvestmentService.calculateCurrentEarnings(investmentId);
        System.out.println("Rendimento atual: " + currentEarnings);
        
        // Calcular valor projetado no vencimento
        BigDecimal projectedValue = fixedInvestmentService.calculateProjectedValue(investmentId);
        System.out.println("Valor projetado no vencimento: " + projectedValue);
        
        // Simular resgate antecipado
        LocalDate redeemDate = LocalDate.now().plusMonths(6);
        BigDecimal redeemValue = fixedInvestmentService.simulateRedemption(investmentId, redeemDate);
        System.out.println("Valor de resgate simulado para " + redeemDate + ": " + redeemValue);
        
        // Realizar resgate parcial
        FixedInvestmentRedeemRequestDTO redeemRequest = new FixedInvestmentRedeemRequestDTO();
        redeemRequest.setRedeemDate(LocalDate.now());
        redeemRequest.setRedeemValue(new BigDecimal("5000.00"));
        redeemRequest.setNote("Resgate parcial para teste");
        
        fixedInvestmentService.redeemInvestment(investmentId, redeemRequest);
        System.out.println("Resgate parcial realizado");
    }

    private static String testVariableInvestment(String userId) {
        System.out.println("\nTestando Investimento Variável:");
        
        // Criar investimento variável
        VariableInvestmentRequestDTO createRequest = new VariableInvestmentRequestDTO();
        createRequest.setName("Ações XPTO3");
        createRequest.setDescription("Ações da empresa XPTO");
        createRequest.setPurchaseDate(LocalDate.now());
        createRequest.setQuantity(100);
        createRequest.setPurchasePrice(new BigDecimal("25.50"));
        createRequest.setCurrentPrice(new BigDecimal("25.50"));
        createRequest.setTickerSymbol("XPTO3");
        
        VariableInvestmentResponseDTO createResponse = 
            variableInvestmentService.createInvestment(createRequest, userId);
        System.out.println("Investimento variável criado: " + createResponse.getName());
        
        // Atualizar cotação
        VariableInvestmentUpdateRequestDTO updateRequest = new VariableInvestmentUpdateRequestDTO();
        updateRequest.setCurrentPrice(new BigDecimal("27.80"));
        
        VariableInvestmentResponseDTO updateResponse = 
            variableInvestmentService.updateInvestment(createResponse.getInvestmentId(), updateRequest);
        System.out.println("Cotaão atualizada: " + updateResponse.getCurrentPrice());
        
        return createResponse.getInvestmentId();
    }

    private static void testVariableInvestmentOperations(String investmentId) {
        System.out.println("\nTestando operações de Investimento Variável:");
        
        // Calcular rentabilidade
        BigDecimal returns = variableInvestmentService.calculateReturns(investmentId);
        System.out.println("Rentabilidade atual: " + returns + "%");
        
        // Calcular valor atual
        BigDecimal currentValue = variableInvestmentService.calculateCurrentValue(investmentId);
        System.out.println("Valor atual: " + currentValue);
        
        // Realizar venda parcial
        VariableInvestmentSellRequestDTO sellRequest = new VariableInvestmentSellRequestDTO();
        sellRequest.setSellDate(LocalDate.now());
        sellRequest.setQuantity(50);
        sellRequest.setSellPrice(new BigDecimal("27.80"));
        sellRequest.setNote("Venda parcial para teste");
        
        variableInvestmentService.sellInvestment(investmentId, sellRequest);
        System.out.println("Venda parcial realizada");
    }

    private static void testInvestmentValidations(String userId) {
        System.out.println("\nTestando validações de investimentos:");
        
        try {
            // Tentar criar investimento fixo com data de vencimento inválida
            FixedInvestmentRequestDTO invalidRequest = new FixedInvestmentRequestDTO();
            invalidRequest.setName("Investimento Inválido");
            invalidRequest.setMaturityDate(LocalDate.now().minusDays(1)); // Data passada
            fixedInvestmentService.createInvestment(invalidRequest, userId);
            System.err.println("Erro: deveria ter falhado com data inválida");
        } catch (IllegalArgumentException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
        
        try {
            // Tentar criar investimento variável com quantidade negativa
            VariableInvestmentRequestDTO invalidRequest = new VariableInvestmentRequestDTO();
            invalidRequest.setName("Investimento Inválido");
            invalidRequest.setQuantity(-10);
            variableInvestmentService.createInvestment(invalidRequest, userId);
            System.err.println("Erro: deveria ter falhado com quantidade negativa");
        } catch (IllegalArgumentException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
    }

    private static void testTransactionsFlow(String userId) {
        System.out.println("\n=== Testando fluxo de transações ===");

        try {
            // 1. Criar contas para teste
            String sourceAccountId = createTestCheckingAccount(userId, "Conta Origem");
            String destinationAccountId = createTestCheckingAccount(userId, "Conta Destino");

            // 2. Testar diferentes tipos de transações
            testDeposit(sourceAccountId);
            testWithdrawal(sourceAccountId);
            testTransfer(sourceAccountId, destinationAccountId);
            
            // 3. Testar consultas e relatórios
            testTransactionQueries(sourceAccountId);
            
            // 4. Testar validações
            testTransactionValidations(sourceAccountId, destinationAccountId);

            System.out.println("Testes de transações concluídos com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de transações: " + e.getMessage());
            throw e;
        }
    }

    private static String createTestCheckingAccount(String userId, String accountName) {
        CheckingAccountRequestDTO request = new CheckingAccountRequestDTO();
        request.setName(accountName);
        request.setDescription("Conta para teste de transações");
        request.setBankCode("001");
        request.setAgency("1234");
        request.setAccountNumber(UUID.randomUUID().toString().substring(0, 6));
        request.setBalance(BigDecimal.ZERO);
        
        CheckingAccountResponseDTO response = checkingAccountService.createAccount(request, userId);
        System.out.println("Conta criada para teste: " + response.getName());
        
        return response.getAccountId();
    }

    private static void testDeposit(String accountId) {
        System.out.println("\nTestando Depósito:");
        
        // Criar depósito
        TransactionRequestDTO depositRequest = new TransactionRequestDTO();
        depositRequest.setType("DEPOSIT");
        depositRequest.setAmount(new BigDecimal("1000.00"));
        depositRequest.setDescription("Depósito inicial");
        depositRequest.setDestinationAccountId(accountId);
        depositRequest.setTransactionDate(LocalDateTime.now());
        
        TransactionResponseDTO response = transactionService.createTransaction(depositRequest);
        System.out.println("Depósito realizado: R$ " + response.getAmount());
        
        // Verificar saldo após depósito
        BigDecimal balance = checkingAccountService.getBalance(accountId);
        System.out.println("Saldo após depósito: R$ " + balance);
    }

    private static void testWithdrawal(String accountId) {
        System.out.println("\nTestando Saque:");
        
        // Criar saque
        TransactionRequestDTO withdrawalRequest = new TransactionRequestDTO();
        withdrawalRequest.setType("WITHDRAWAL");
        withdrawalRequest.setAmount(new BigDecimal("300.00"));
        withdrawalRequest.setDescription("Saque teste");
        withdrawalRequest.setSourceAccountId(accountId);
        withdrawalRequest.setTransactionDate(LocalDateTime.now());
        
        TransactionResponseDTO response = transactionService.createTransaction(withdrawalRequest);
        System.out.println("Saque realizado: R$ " + response.getAmount());
        
        // Verificar saldo após saque
        BigDecimal balance = checkingAccountService.getBalance(accountId);
        System.out.println("Saldo após saque: R$ " + balance);
    }

    private static void testTransfer(String sourceAccountId, String destinationAccountId) {
        System.out.println("\nTestando Transferência:");
        
        // Criar transferência
        TransactionRequestDTO transferRequest = new TransactionRequestDTO();
        transferRequest.setType("TRANSFER");
        transferRequest.setAmount(new BigDecimal("500.00"));
        transferRequest.setDescription("Transferência teste");
        transferRequest.setSourceAccountId(sourceAccountId);
        transferRequest.setDestinationAccountId(destinationAccountId);
        transferRequest.setTransactionDate(LocalDateTime.now());
        
        TransactionResponseDTO response = transactionService.createTransaction(transferRequest);
        System.out.println("Transferência realizada: R$ " + response.getAmount());
        
        // Verificar saldos após transferência
        BigDecimal sourceBalance = checkingAccountService.getBalance(sourceAccountId);
        BigDecimal destBalance = checkingAccountService.getBalance(destinationAccountId);
        System.out.println("Saldo conta origem: R$ " + sourceBalance);
        System.out.println("Saldo conta destino: R$ " + destBalance);
    }

    private static void testTransactionQueries(String accountId) {
        System.out.println("\nTestando Consultas de Transações:");
        
        // Consultar extrato por período
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        
        List<TransactionResponseDTO> transactions = 
            transactionService.getTransactionsByPeriod(accountId, startDate, endDate);
        System.out.println("Transações no período: " + transactions.size());
        
        // Consultar por tipo
        List<TransactionResponseDTO> deposits = 
            transactionService.getTransactionsByType(accountId, "DEPOSIT");
        System.out.println("Total de depósitos: " + deposits.size());
        
        // Calcular totais
        BigDecimal totalDeposits = transactionService.calculateTotalByType(accountId, "DEPOSIT");
        BigDecimal totalWithdrawals = transactionService.calculateTotalByType(accountId, "WITHDRAWAL");
        System.out.println("Total depositado: R$ " + totalDeposits);
        System.out.println("Total sacado: R$ " + totalWithdrawals);
    }

    private static void testTransactionValidations(String sourceAccountId, String destinationAccountId) {
        System.out.println("\nTestando Validações de Transações:");
        
        try {
            // Tentar transferir valor maior que o saldo
            TransactionRequestDTO invalidRequest = new TransactionRequestDTO();
            invalidRequest.setType("TRANSFER");
            invalidRequest.setAmount(new BigDecimal("1000000.00")); // Valor muito alto
            invalidRequest.setSourceAccountId(sourceAccountId);
            invalidRequest.setDestinationAccountId(destinationAccountId);
            
            transactionService.createTransaction(invalidRequest);
            System.err.println("Erro: deveria ter falhado por saldo insuficiente");
        } catch (InsufficientFundsException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
        
        try {
            // Tentar criar transação com valor negativo
            TransactionRequestDTO invalidRequest = new TransactionRequestDTO();
            invalidRequest.setType("DEPOSIT");
            invalidRequest.setAmount(new BigDecimal("-100.00"));
            invalidRequest.setDestinationAccountId(destinationAccountId);
            
            transactionService.createTransaction(invalidRequest);
            System.err.println("Erro: deveria ter falhado por valor negativo");
        } catch (IllegalArgumentException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
    }

    private static String generateRandomEmail() {
        return "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    private static void testCheckingAccountOperations(String accountId) {
        System.out.println("\nTestando operações da Conta Corrente:");
        
        // Consultar saldo
        BigDecimal balance = checkingAccountService.getBalance(accountId);
        System.out.println("Saldo atual: " + balance);
        
        // Consultar extrato
        LocalDateTime startDate = LocalDateTime.now().minusMonths(1);
        LocalDateTime endDate = LocalDateTime.now();
        List<TransactionResponseDTO> transactions = 
            checkingAccountService.getTransactions(accountId, startDate, endDate);
        System.out.println("Número de transações no período: " + transactions.size());
        
        // Desativar conta
        checkingAccountService.deactivateAccount(accountId);
        System.out.println("Conta corrente desativada");
        
        // Verificar status
        CheckingAccountResponseDTO account = checkingAccountService.getAccountById(accountId);
        System.out.println("Status da conta: " + (account.isActive() ? "Ativa" : "Inativa"));
    }

    private static void testSavingsAccountOperations(String accountId) {
        System.out.println("\nTestando operações da Conta Poupança:");
        
        // Consultar rendimentos
        BigDecimal earnings = savingsAccountService.calculateEarnings(accountId);
        System.out.println("Rendimentos acumulados: " + earnings);
        
        // Desativar conta
        savingsAccountService.deactivateAccount(accountId);
        System.out.println("Conta poupança desativada");
    }

    private static void testInvestmentAccountOperations(String accountId) {
        System.out.println("\nTestando operações da Conta Investimento:");
        
        // Consultar rentabilidade
        BigDecimal returns = investmentAccountService.calculateReturns(accountId);
        System.out.println("Rentabilidade total: " + returns);
        
        // Desativar conta
        investmentAccountService.deactivateAccount(accountId);
        System.out.println("Conta investimento desativada");
    }

    private static void testGoalAccountOperations(String accountId) {
        System.out.println("\nTestando operações da Conta Meta:");
        
        // Consultar progresso
        BigDecimal progress = goalAccountService.calculateProgress(accountId);
        System.out.println("Progresso atual: " + progress + "%");
        
        // Verificar tempo restante
        long remainingDays = goalAccountService.calculateRemainingDays(accountId);
        System.out.println("Dias restantes para a meta: " + remainingDays);
        
        // Desativar conta
        goalAccountService.deactivateAccount(accountId);
        System.out.println("Conta meta desativada");
    }

    private static void testAccountValidations(String userId) {
        System.out.println("\nTestando validações de contas:");
        
        try {
            // Tentar criar conta com número inválido
            CheckingAccountRequestDTO invalidRequest = new CheckingAccountRequestDTO();
            invalidRequest.setName("Conta Inválida");
            invalidRequest.setAccountNumber("123"); // número muito curto
            checkingAccountService.createAccount(invalidRequest, userId);
            System.err.println("Erro: deveria ter falhado com número de conta inválido");
        } catch (IllegalArgumentException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
        
        try {
            // Tentar criar conta com saldo inicial negativo
            SavingsAccountRequestDTO invalidRequest = new SavingsAccountRequestDTO();
            invalidRequest.setName("Conta Inválida");
            invalidRequest.setBalance(new BigDecimal("-100"));
            savingsAccountService.createAccount(invalidRequest, userId);
            System.err.println("Erro: deveria ter falhado com saldo negativo");
        } catch (IllegalArgumentException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
    }

    private static void testGoalsFlow(String userId) {
        System.out.println("\n=== Testando fluxo de metas financeiras ===");

        try {
            // 1. Criar categoria para as metas
            String categoryId = createTestCategory();

            // 2. Testar diferentes tipos de metas
            String shortTermGoalId = testShortTermGoal(userId, categoryId);
            String longTermGoalId = testLongTermGoal(userId, categoryId);
            
            // 3. Testar acompanhamento de metas
            testGoalProgress(shortTermGoalId);
            testGoalProgress(longTermGoalId);
            
            // 4. Testar atualizações de metas
            testGoalUpdates(shortTermGoalId);
            
            // 5. Testar validações
            testGoalValidations(userId, categoryId);

            System.out.println("Testes de metas concluídos com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de metas: " + e.getMessage());
            throw e;
        }
    }

    private static String createTestCategory() {
        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Objetivos Financeiros");
        request.setDescription("Categoria para teste de metas");
        request.setType("GOAL");
        
        CategoryResponseDTO response = categoryService.createCategory(request);
        System.out.println("Categoria criada para teste: " + response.getName());
        
        return response.getId();
    }

    private static String testShortTermGoal(String userId, String categoryId) {
        System.out.println("\nTestando Meta de Curto Prazo:");
        
        // Criar meta de curto prazo
        GoalAccountRequestDTO request = new GoalAccountRequestDTO();
        request.setName("Reserva de Emergência");
        request.setDescription("Meta para reserva de emergência");
        request.setTargetAmount(new BigDecimal("10000.00"));
        request.setTargetDate(LocalDate.now().plusMonths(6));
        request.setCategoryId(categoryId);
        request.setMonthlyContribution(new BigDecimal("1666.67")); // Para atingir em 6 meses
        
        GoalAccountResponseDTO response = goalAccountService.createAccount(request, userId);
        System.out.println("Meta de curto prazo criada: " + response.getName());
        
        return response.getAccountId();
    }

    private static String testLongTermGoal(String userId, String categoryId) {
        System.out.println("\nTestando Meta de Longo Prazo:");
        
        // Criar meta de longo prazo
        GoalAccountRequestDTO request = new GoalAccountRequestDTO();
        request.setName("Aposentadoria");
        request.setDescription("Meta para aposentadoria");
        request.setTargetAmount(new BigDecimal("1000000.00"));
        request.setTargetDate(LocalDate.now().plusYears(30));
        request.setCategoryId(categoryId);
        request.setMonthlyContribution(new BigDecimal("1000.00"));
        request.setExpectedReturn(new BigDecimal("0.08")); // 8% ao ano
        
        GoalAccountResponseDTO response = goalAccountService.createAccount(request, userId);
        System.out.println("Meta de longo prazo criada: " + response.getName());
        
        return response.getAccountId();
    }

    private static void testGoalProgress(String goalId) {
        System.out.println("\nTestando Progresso da Meta " + goalId + ":");
        
        // Simular contribuição mensal
        TransactionRequestDTO contributionRequest = new TransactionRequestDTO();
        contributionRequest.setType("DEPOSIT");
        contributionRequest.setAmount(new BigDecimal("1000.00"));
        contributionRequest.setDescription("Contribuição mensal para meta");
        contributionRequest.setDestinationAccountId(goalId);
        
        transactionService.createTransaction(contributionRequest);
        
        // Verificar progresso
        GoalProgressDTO progress = goalAccountService.checkProgress(goalId);
        System.out.println("Progresso atual: " + progress.getPercentageComplete() + "%");
        System.out.println("Valor atual: R$ " + progress.getCurrentAmount());
        System.out.println("Valor faltante: R$ " + progress.getRemainingAmount());
        System.out.println("Dias restantes: " + progress.getRemainingDays());
        System.out.println("Projeção de conclusão: " + progress.getProjectedCompletionDate());
    }

    private static void testGoalUpdates(String goalId) {
        System.out.println("\nTestando Atualizações da Meta:");
        
        // Atualizar meta
        GoalAccountUpdateRequestDTO updateRequest = new GoalAccountUpdateRequestDTO();
        updateRequest.setTargetAmount(new BigDecimal("12000.00")); // Aumentar valor da meta
        updateRequest.setMonthlyContribution(new BigDecimal("2000.00")); // Aumentar contribuição
        
        GoalAccountResponseDTO response = goalAccountService.updateAccount(goalId, updateRequest);
        System.out.println("Meta atualizada: " + response.getName());
        System.out.println("Novo valor alvo: R$ " + response.getTargetAmount());
        
        // Verificar novo prazo projetado
        GoalProgressDTO updatedProgress = goalAccountService.checkProgress(goalId);
        System.out.println("Nova projeção de conclusão: " + updatedProgress.getProjectedCompletionDate());
    }

    private static void testGoalValidations(String userId, String categoryId) {
        System.out.println("\nTestando Validações de Metas:");
        
        try {
            // Tentar criar meta com data passada
            GoalAccountRequestDTO invalidRequest = new GoalAccountRequestDTO();
            invalidRequest.setName("Meta Inválida");
            invalidRequest.setTargetDate(LocalDate.now().minusDays(1));
            invalidRequest.setTargetAmount(new BigDecimal("1000.00"));
            invalidRequest.setCategoryId(categoryId);
            
            goalAccountService.createAccount(invalidRequest, userId);
            System.err.println("Erro: deveria ter falhado com data no passado");
        } catch (IllegalArgumentException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
        
        try {
            // Tentar criar meta com valor negativo
            GoalAccountRequestDTO invalidRequest = new GoalAccountRequestDTO();
            invalidRequest.setName("Meta Inválida");
            invalidRequest.setTargetAmount(new BigDecimal("-1000.00"));
            invalidRequest.setTargetDate(LocalDate.now().plusMonths(6));
            invalidRequest.setCategoryId(categoryId);
            
            goalAccountService.createAccount(invalidRequest, userId);
            System.err.println("Erro: deveria ter falhado com valor negativo");
        } catch (IllegalArgumentException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
    }

    private static void testReportsFlow(String userId) {
        System.out.println("\n=== Testando fluxo de relatórios ===");

        try {
            // 1. Relatórios de Balanço
            testBalanceReports(userId);

            // 2. Relatórios de Transações
            testTransactionReports(userId);

            // 3. Relatórios de Investimentos
            testInvestmentReports(userId);

            // 4. Relatórios de Metas
            testGoalReports(userId);

            // 5. Relatórios Consolidados
            testConsolidatedReports(userId);

            System.out.println("Testes de relatórios concluídos com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de relatórios: " + e.getMessage());
            throw e;
        }
    }

    private static void testBalanceReports(String userId) {
        System.out.println("\nTestando Relatórios de Balanço:");
        
        // Balanço geral
        BalanceReportDTO balanceReport = reportService.generateBalanceReport(userId);
        System.out.println("Saldo total: R$ " + balanceReport.getTotalBalance());
        System.out.println("Total em conta corrente: R$ " + balanceReport.getCheckingBalance());
        System.out.println("Total em poupança: R$ " + balanceReport.getSavingsBalance());
        System.out.println("Total em investimentos: R$ " + balanceReport.getInvestmentBalance());
        
        // Evolução patrimonial
        LocalDate startDate = LocalDate.now().minusMonths(12);
        LocalDate endDate = LocalDate.now();
        List<PatrimonialEvolutionDTO> evolution = 
            reportService.getPatrimonialEvolution(userId, startDate, endDate);
        
        System.out.println("\nEvolução Patrimonial dos últimos 12 meses:");
        evolution.forEach(e -> 
            System.out.println(e.getMonth() + ": R$ " + e.getTotalBalance())
        );
    }

    private static void testTransactionReports(String userId) {
        System.out.println("\nTestando Relatórios de Transações:");
        
        // Fluxo de caixa mensal
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        CashFlowReportDTO cashFlow = reportService.generateCashFlowReport(userId, currentMonth);
        
        System.out.println("\nFluxo de Caixa do Mês:");
        System.out.println("Total de entradas: R$ " + cashFlow.getTotalIncome());
        System.out.println("Total de saídas: R$ " + cashFlow.getTotalExpenses());
        System.out.println("Saldo: R$ " + cashFlow.getBalance());
        
        // Gastos por categoria
        List<CategoryExpenseDTO> expenses = 
            reportService.getExpensesByCategory(userId, currentMonth);
        
        System.out.println("\nGastos por Categoria:");
        expenses.forEach(e -> 
            System.out.println(e.getCategoryName() + ": R$ " + e.getTotalAmount())
        );
    }

    private static void testInvestmentReports(String userId) {
        System.out.println("\nTestando Relatórios de Investimentos:");
        
        // Carteira de investimentos
        InvestmentPortfolioDTO portfolio = reportService.generateInvestmentPortfolio(userId);
        
        System.out.println("\nComposição da Carteira:");
        System.out.println("Renda Fixa: " + portfolio.getFixedIncomePercentage() + "%");
        System.out.println("Renda Variável: " + portfolio.getVariableIncomePercentage() + "%");
        
        // Rentabilidade por tipo de investimento
        List<InvestmentReturnDTO> returns = 
            reportService.getInvestmentReturns(userId, LocalDate.now().minusMonths(12));
        
        System.out.println("\nRentabilidade dos Investimentos:");
        returns.forEach(r -> 
            System.out.println(r.getInvestmentType() + ": " + r.getReturnPercentage() + "%")
        );
    }

    private static void testGoalReports(String userId) {
        System.out.println("\nTestando Relatórios de Metas:");
        
        // Status das metas
        List<GoalStatusDTO> goals = reportService.getGoalsStatus(userId);
        
        System.out.println("\nStatus das Metas:");
        goals.forEach(g -> {
            System.out.println("\nMeta: " + g.getGoalName());
            System.out.println("Progresso: " + g.getProgressPercentage() + "%");
            System.out.println("Valor atual: R$ " + g.getCurrentAmount());
            System.out.println("Valor alvo: R$ " + g.getTargetAmount());
            System.out.println("Previsão de conclusão: " + g.getProjectedCompletionDate());
        });
    }

    private static void testConsolidatedReports(String userId) {
        System.out.println("\nTestando Relatórios Consolidados:");
        
        // Relatório financeiro completo
        FinancialReportDTO report = reportService.generateFinancialReport(userId);
        
        System.out.println("\nRelatório Financeiro Consolidado:");
        System.out.println("Patrimônio Total: R$ " + report.getTotalPatrimony());
        System.out.println("Rendimento Mensal: R$ " + report.getMonthlyIncome());
        System.out.println("Despesas Mensais: R$ " + report.getMonthlyExpenses());
        System.out.println("Taxa de Poupança: " + report.getSavingsRate() + "%");
        System.out.println("Retorno sobre Investimento: " + report.getROI() + "%");
        
        // Indicadores financeiros
        FinancialIndicatorsDTO indicators = reportService.calculateFinancialIndicators(userId);
        
        System.out.println("\nIndicadores Financeiros:");
        System.out.println("Liquidez: " + indicators.getLiquidityRatio());
        System.out.println("Endividamento: " + indicators.getDebtRatio() + "%");
        System.out.println("Independência Financeira: " + indicators.getFinancialIndependenceRatio() + "%");
    }

    private static void testSecurityFlow() {
        System.out.println("\n=== Testando fluxo de segurança ===");

        try {
            // 1. Teste de Autenticação
            testAuthentication();

            // 2. Teste de Autorização
            testAuthorization();

            // 3. Teste de Tokens
            testTokenManagement();

            // 4. Teste de Validações de Segurança
            testSecurityValidations();

            System.out.println("Testes de segurança concluídos com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de segurança: " + e.getMessage());
            throw e;
        }
    }

    private static void testAuthentication() {
        System.out.println("\nTestando Autenticação:");
        
        // Criar usuário para teste
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setName("Usuário Teste");
        userRequest.setEmail("teste_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com");
        userRequest.setPassword("Senha@123");
        userRequest.setBirthdate(LocalDate.of(1990, 1, 1));
        
        UserResponseDTO newUser = userService.createUser(userRequest);
        System.out.println("Usuário criado para teste: " + newUser.getName());
        
        // Teste de login com sucesso
        try {
            UserLoginRequestDTO loginRequest = new UserLoginRequestDTO();
            loginRequest.setEmail(userRequest.getEmail());
            loginRequest.setPassword(userRequest.getPassword());
            
            LoginResponseDTO loginResponse = authService.login(loginRequest);
            System.out.println("Login realizado com sucesso");
            System.out.println("Token gerado: " + loginResponse.getToken());
            
        } catch (AuthenticationException e) {
            System.err.println("Erro no login: " + e.getMessage());
            throw e;
        }
        
        // Teste de login com credenciais inválidas
        try {
            UserLoginRequestDTO invalidLogin = new UserLoginRequestDTO();
            invalidLogin.setEmail(userRequest.getEmail());
            invalidLogin.setPassword("senha_errada");
            
            authService.login(invalidLogin);
            System.err.println("Erro: deveria ter falhado com senha incorreta");
        } catch (AuthenticationException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
    }

    private static void testAuthorization() {
        System.out.println("\nTestando Autorização:");
        
        // Criar usuário comum
        UserRequestDTO regularUser = new UserRequestDTO();
        regularUser.setName("Usuário Comum");
        regularUser.setEmail("comum_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com");
        regularUser.setPassword("Senha@123");
        
        UserResponseDTO regularUserResponse = userService.createUser(regularUser);
        
        // Criar usuário admin
        UserRequestDTO adminUser = new UserRequestDTO();
        adminUser.setName("Usuário Admin");
        adminUser.setEmail("admin_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com");
        adminUser.setPassword("Senha@123");
        // Definir como admin via serviço específico
        
        UserResponseDTO adminUserResponse = userService.createUser(adminUser);
        userService.setUserAsAdmin(adminUserResponse.getUserId());
        
        // Testar acesso a recursos protegidos
        try {
            // Tentar acessar recurso admin com usuário comum
            String regularUserToken = authService.login(new UserLoginRequestDTO(regularUser.getEmail(), regularUser.getPassword())).getToken();
            
            adminService.someProtectedAction(regularUserToken);
            System.err.println("Erro: deveria ter falhado por falta de permissão");
        } catch (UnauthorizedException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
    }

    private static void testTokenManagement() {
        System.out.println("\nTestando Gerenciamento de Tokens:");
        
        // Gerar token
        String token = authService.generateToken("user123");
        System.out.println("Token gerado");
        
        // Validar token
        try {
            String userId = authService.validateToken(token);
            System.out.println("Token válido para usuário: " + userId);
        } catch (InvalidTokenException e) {
            System.err.println("Erro na validação do token: " + e.getMessage());
            throw e;
        }
        
        // Testar expiração
        try {
            String expiredToken = authService.generateExpiredToken("user123");
            authService.validateToken(expiredToken);
            System.err.println("Erro: deveria ter falhado com token expirado");
        } catch (InvalidTokenException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
    }

    private static void testSecurityValidations() {
        System.out.println("\nTestando Validações de Segurança:");
        
        // Teste de força da senha
        try {
            UserRequestDTO weakPasswordUser = new UserRequestDTO();
            weakPasswordUser.setName("Teste Senha Fraca");
            weakPasswordUser.setEmail("teste@example.com");
            weakPasswordUser.setPassword("123"); // senha muito fraca
            
            userService.createUser(weakPasswordUser);
            System.err.println("Erro: deveria ter falhado com senha fraca");
        } catch (InvalidPasswordException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
        
        // Teste de tentativas de login
        try {
            String email = "teste@example.com";
            String wrongPassword = "senha_errada";
            
            for (int i = 0; i < 5; i++) {
                try {
                    authService.login(new UserLoginRequestDTO(email, wrongPassword));
                } catch (AuthenticationException e) {
                    System.out.println("Tentativa " + (i + 1) + " falhou como esperado");
                }
            }
            
            // Tentar novamente após muitas tentativas
            authService.login(new UserLoginRequestDTO(email, wrongPassword));
            System.err.println("Erro: deveria ter bloqueado após múltiplas tentativas");
        } catch (AccountLockedException e) {
            System.out.println("Validação ok: " + e.getMessage());
        }
    }

    private static void testSystemIntegration() {
        System.out.println("\n=== Testando integração do sistema ===");

        try {
            // 1. Teste de Fluxo Completo
            testCompleteUserJourney();

            // 2. Teste de Consistência de Dados
            testDataConsistency();

            // 3. Teste de Performance
            testSystemPerformance();

            // 4. Teste de Recuperação de Erros
            testErrorRecovery();

            System.out.println("Testes de integração concluídos com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de integração: " + e.getMessage());
            throw e;
        }
    }

    private static void testCompleteUserJourney() {
        System.out.println("\nTestando Fluxo Completo do Usuário:");
        
        try {
            // 1. Criar usuário
            UserRequestDTO userRequest = new UserRequestDTO();
            userRequest.setName("Usuário Integração");
            userRequest.setEmail("integracao_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com");
            userRequest.setPassword("Senha@123");
            userRequest.setBirthdate(LocalDate.of(1990, 1, 1));
            
            UserResponseDTO user = userService.createUser(userRequest);
            String userId = user.getUserId();
            
            // 2. Login
            LoginResponseDTO loginResponse = authService.login(new UserLoginRequestDTO(
                userRequest.getEmail(), 
                userRequest.getPassword()
            ));
            String token = loginResponse.getToken();
            
            // 3. Criar contas
            String checkingAccountId = createAndVerifyCheckingAccount(userId);
            String savingsAccountId = createAndVerifySavingsAccount(userId);
            String investmentAccountId = createAndVerifyInvestmentAccount(userId);
            
            // 4. Realizar transações
            performAndVerifyTransactions(checkingAccountId, savingsAccountId);
            
            // 5. Criar e acompanhar investimentos
            createAndTrackInvestments(userId, investmentAccountId);
            
            // 6. Definir e acompanhar metas
            createAndTrackGoals(userId);
            
            // 7. Verificar relatórios
            verifyReports(userId);
            
            System.out.println("Fluxo completo executado com sucesso!");
            
        } catch (Exception e) {
            System.err.println("Erro no fluxo completo: " + e.getMessage());
            throw e;
        }
    }

    private static void testDataConsistency() {
        System.out.println("\nTestando Consistência de Dados:");
        
        try {
            // 1. Verificar saldos
            verifyAccountBalances();
            
            // 2. Verificar totais de transações
            verifyTransactionTotals();
            
            // 3. Verificar integridade de investimentos
            verifyInvestmentIntegrity();
            
            // 4. Verificar relacionamentos entre entidades
            verifyEntityRelationships();
            
            System.out.println("Verificações de consistência concluídas!");
            
        } catch (Exception e) {
            System.err.println("Erro na verificação de consistência: " + e.getMessage());
            throw e;
        }
    }

    private static void testSystemPerformance() {
        System.out.println("\nTestando Performance do Sistema:");
        
        try {
            // 1. Teste de carga em transações
            long startTime = System.currentTimeMillis();
            performLoadTest(100); // 100 transações simultâneas
            long endTime = System.currentTimeMillis();
            
            System.out.println("Tempo de processamento: " + (endTime - startTime) + "ms");
            
            // 2. Teste de consultas complexas
            testComplexQueries();
            
            // 3. Teste de concorrência
            testConcurrentAccess();
            
            System.out.println("Testes de performance concluídos!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de performance: " + e.getMessage());
            throw e;
        }
    }

    private static void testErrorRecovery() {
        System.out.println("\nTestando Recuperação de Erros:");
        
        try {
            // 1. Teste de rollback em transações
            testTransactionRollback();
            
            // 2. Teste de recuperação de conexão
            testConnectionRecovery();
            
            // 3. Teste de consistência após erro
            testPostErrorConsistency();
            
            System.out.println("Testes de recuperação concluídos!");
            
        } catch (Exception e) {
            System.err.println("Erro nos testes de recuperação: " + e.getMessage());
            throw e;
        }
    }

    // Métodos auxiliares para os testes de integração
    private static String createAndVerifyCheckingAccount(String userId) {
        CheckingAccountRequestDTO request = new CheckingAccountRequestDTO();
        // ... configurar conta
        CheckingAccountResponseDTO response = checkingAccountService.createAccount(request, userId);
        
        // Verificar criação
        CheckingAccountResponseDTO verified = checkingAccountService.getAccountById(response.getAccountId());
        assert verified != null : "Conta não foi criada corretamente";
        
        return response.getAccountId();
    }

    private static void performAndVerifyTransactions(String sourceId, String destinationId) {
        BigDecimal initialSourceBalance = checkingAccountService.getBalance(sourceId);
        BigDecimal initialDestBalance = savingsAccountService.getBalance(destinationId);
        
        // Realizar transferência
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setAmount(new BigDecimal("100.00"));
        request.setType("TRANSFER");
        request.setSourceAccountId(sourceId);
        request.setDestinationAccountId(destinationId);
        
        transactionService.createTransaction(request);
        
        // Verificar saldos
        BigDecimal finalSourceBalance = checkingAccountService.getBalance(sourceId);
        BigDecimal finalDestBalance = savingsAccountService.getBalance(destinationId);
        
        assert finalSourceBalance.equals(initialSourceBalance.subtract(new BigDecimal("100.00"))) : "Saldo origem incorreto";
        assert finalDestBalance.equals(initialDestBalance.add(new BigDecimal("100.00"))) : "Saldo destino incorreto";
    }

    private static void verifyAccountBalances() {
        // Implementar verificação de consistência de saldos
        // Comparar somas de transações com saldos atuais
    }

    private static void testTransactionRollback() {
        // Implementar teste de rollback
        // Forçar erro durante transação e verificar reversão
    }
}

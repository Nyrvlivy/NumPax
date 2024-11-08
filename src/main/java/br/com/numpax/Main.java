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

            // Inicializar serviços
            UserServiceImpl userService = new UserServiceImpl(userRepository, checkingAccountRepository);
            CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);
            CheckingAccountServiceImpl checkingAccountService = new CheckingAccountServiceImpl(checkingAccountRepository, userService);
            SavingsAccountServiceImpl savingsAccountService = new SavingsAccountServiceImpl(savingsAccountRepository, userService);
            InvestmentAccountServiceImpl investmentAccountService = new InvestmentAccountServiceImpl(investmentAccountRepository, userService);
            GoalAccountServiceImpl goalAccountService = new GoalAccountServiceImpl(goalAccountRepository, userService, categoryService);

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
            checkingAccountRequest.setAccountNumber("567876790-17");

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
            // Defina outros campos necessários

            InvestmentAccountResponseDTO investmentAccountResponse = investmentAccountService.createAccount(investmentAccountRequest, userId);
            System.out.println("Conta de investimento criada: " + investmentAccountResponse);

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
                goalAccountRequest.setEndDate(LocalDate.now().plusYears(2));

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

        } catch (Exception e) {
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

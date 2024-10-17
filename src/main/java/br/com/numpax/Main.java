package br.com.numpax;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.dto.DetailedUserDTO;
import br.com.numpax.API.V1.dto.UserDTO;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.services.impl.AccountServiceImpl;
import br.com.numpax.application.services.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        System.out.println("Testando as funcionalidades_____");

        // OBSERVAÇÕES PARA O AVALIADOR:
        // - Descomente apenas a função que deseja testar para o console não ficar poluído.
        // - Tivemos que fazer uma pequena alteração em nossa criação no banco de dados, então atualizamos o script e poderá ser encontrado em src/main/resources/sql/create_tables.sql
        // - Infelizmente, tivemos que implementar usuários, uma vez que as contas são associadas a eles.
        // - Quanto a versão, utilizamos o Java 21, validando com o professor e ele nos autorizou a utilizar essa versão.

        /* 1. Testes de Contas (Accounts)
         *
         * - Toda vez que um novo usuário é criado, ele recebe uma conta corrente associada a ele
         * - Nos testes, além da inicial, criamos mais 4 contas (4 entidades diferentes) para o mesmo usuário:
         *      - Conta Corrente -> Checking
         *      - Conta Poupança -> Savings
         *      - Conta Investimento -> Investment
         *      - Conta Metas - Goal
         *
         * - Listamos todas as contas por usuário;
         * - Listamos todas as contas existentes;
         *
         */

        testAccountFunctions();

        /* 2. Testes de Usuários (Users)
         *
         * - Criamos dois usuários para testar as funcionalidades de criação, atualização e desativação de usuários;
         * - Atualizamos o nome e a senha do segundo usuário;
         * - Recuperamos o segundo usuário por ID;
         * - Recuperamos os detalhes do segundo usuário por ID;
         * - Desabilitamos o segundo usuário;
         * - Listamos todos os usuários;
         * - Listamos todos os usuários ativos;
         * - Listamos todos os usuários inativos;
         *
         */

        // testUserFunctions();

        System.out.println("\nTeste Completo!");
    }

    private static void testAccountFunctions() {
        UserServiceImpl userService = new UserServiceImpl();
        AccountServiceImpl accountService = new AccountServiceImpl();

        // 1. Criar um novo User
        System.out.println("\n1. Criar um novo User para Testar Criação de Contas_____");
        UserDTO userDTO = new UserDTO(
            null,
            "Test Accounts User",
            generateRandomEmail(),
            LocalDate.of(1990, 1, 1)
        );
        String password = "p@ssw0rd123";
        UserDTO createdUser = userService.createOrUpdateUser(userDTO, password);
        System.out.println("User criado: " + createdUser);

        // 2. Criar uma conta Checking
        System.out.println("\n2. Criar uma conta Checking_____");
        AccountDTO checkingAccountDTO = new AccountDTO(
            null,
            "Conta Corrente",
            "Conta corrente do banco XYZ",
            BigDecimal.valueOf(5000.00),
            AccountType.CHECKING,
            true,
            LocalDateTime.now(),
            LocalDateTime.now(),
            createdUser.getId()
        );
        AccountDTO createdCheckingAccount = accountService.createAccount(checkingAccountDTO, createdUser.getId());
        System.out.println("Conta Checking criada: " + createdCheckingAccount);

        // 3. Criar uma conta Savings
        System.out.println("\n3. Criar uma conta Savings_____");
        AccountDTO savingsAccountDTO = new AccountDTO(
            null,
            "Poupança",
            "Conta poupança",
            BigDecimal.valueOf(3000.00),
            AccountType.SAVINGS,
            true,
            LocalDateTime.now(),
            LocalDateTime.now(),
            createdUser.getId()
        );
        AccountDTO createdSavingsAccount = accountService.createAccount(savingsAccountDTO, createdUser.getId());
        System.out.println("Conta Savings criada: " + createdSavingsAccount);

        // 4. Criar uma conta Goal
        System.out.println("\n4. Criar uma conta Goal_____");
        AccountDTO goalAccountDTO = new AccountDTO(
            null,
            "Meta de Viagem",
            "Conta para economizar para a viagem dos sonhos",
            BigDecimal.valueOf(0.00),
            AccountType.GOAL,
            true,
            LocalDateTime.now(),
            LocalDateTime.now(),
            createdUser.getId()
        );
        AccountDTO createdGoalAccount = accountService.createAccount(goalAccountDTO, createdUser.getId());
        System.out.println("Conta Goal criada: " + createdGoalAccount);

        // 5. Criar uma conta Investment
        System.out.println("\n5. Criar uma conta Investment_____");
        AccountDTO investmentAccountDTO = new AccountDTO(
            null,
            "Investimentos",
            "Conta de investimentos em ações",
            BigDecimal.valueOf(10000.00),
            AccountType.INVESTMENT,
            true,
            LocalDateTime.now(),
            LocalDateTime.now(),
            createdUser.getId()
        );
        AccountDTO createdInvestmentAccount = accountService.createAccount(investmentAccountDTO, createdUser.getId());
        System.out.println("Conta Investment criada: " + createdInvestmentAccount);

        // 6. Listar as contas do User
        System.out.println("\n6. Listar as contas do usuário_____");
        accountService.getAccountsByUserId(createdUser.getId())
            .forEach(System.out::println);

        // 7. Listar todas as contas existentes
        System.out.println("\n7. Listar todas as contas existentes_____");
        accountService.getAllAccounts().forEach(System.out::println);
    }

//    private static void testUserFunctions() {
//        UserServiceImpl userService = new UserServiceImpl();
//
//        // 1. Criar o primeiro User
//        System.out.println("\n1. Criar um novo User_____");
//        UserDTO firstUserDTO = new UserDTO(
//            null, "User Test 1", generateRandomEmail(), LocalDate.of(1999, 11, 10)
//        );
//        String firstPassword = "p@ssw0rd123";
//        UserDTO firstCreatedUser = userService.createOrUpdateUser(firstUserDTO, firstPassword);
//        System.out.println("User Test 1 criado: " + firstCreatedUser);
//
//        // 2. Criar um segundo User
//        System.out.println("\n2. Criar um segundo User_____");
//        UserDTO secondUserDTO = new UserDTO(
//            null, "User Test 2", generateRandomEmail(), LocalDate.of(2002, 2, 28)
//        );
//        String secondPassword = "p@ssw0rd123";
//        UserDTO secondCreatedUser = userService.createOrUpdateUser(secondUserDTO, secondPassword);
//        System.out.println("User Test 2 criado: " + secondCreatedUser);
//
//        // 3. Atualizar o nome e a senha do segundo User
//        System.out.println("\n3. Atualizar o nome e a senha do segundo User_____");
//        String newSecondPassword = "newP@ssw0rd456";
//        UserDTO updatedSecondUserDTO = new UserDTO(
//            secondCreatedUser.getId(),
//            "New User Test 2",
//            secondCreatedUser.getEmail(),
//            secondCreatedUser.getBirthdate(),
//            newSecondPassword
//        );
//
//        UserDTO updatedUser = userService.createOrUpdateUser(updatedSecondUserDTO, newSecondPassword);
//        System.out.println("User Test 2 atualizado: " + updatedUser);
//
//        // 4. Recuperar o segundo User por ID
//        System.out.println("\n4. Recuperar o segundo User atualizado por ID_____");
//        UserDTO simpleUser = userService.getUserById(secondCreatedUser.getId());
//        System.out.println("User simples recuperado: " + simpleUser);
//
//        // 5. Recuperar detalhes do segundo User por ID
//        System.out.println("\n5. Detalhar o segundo User por ID_____");
//        DetailedUserDTO detailedUser = userService.getDetailedUserById(secondCreatedUser.getId());
//        System.out.println("User detalhado recuperado: " + detailedUser);
//
//        // 6. Desabilitar o segundo User
//        System.out.println("\n6. Desabilitar o segundo User pelo ID_____");
//        userService.disableUserById(secondCreatedUser.getId());
//        UserDTO disabledUser = userService.getUserById(secondCreatedUser.getId());
//        System.out.println("User após desabilitar: " + disabledUser);
//
//        // 7. Recuperar todos os Users
//        System.out.println("\n7. Lista de todos os Users:");
//        userService.getAllUsers().forEach(System.out::println);
//
//        // 8. Recuperar todos os Users ativos
//        System.out.println("\n8. Lista de todos os Users ativos:");
//        userService.getAllActiveUsers().forEach(System.out::println);
//
//        // 9. Recuperar todos os Users inativos
//        System.out.println("\n9. Lista de todos os Users inativos:");
//        userService.getAllInactiveUsers().forEach(System.out::println);
//    }


    private static String generateRandomEmail() {
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        return "user_test_" + randomPart + "@gmail.com";
    }
}

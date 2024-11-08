package br.com.numpax;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.dto.UserDTO;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.services.impl.AccountServiceImpl;
import br.com.numpax.application.services.impl.TransactionServiceImpl;
import br.com.numpax.application.services.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando testes do sistema financeiro...");

        testScenario();
    }

    private static void testScenario() {
        UserServiceImpl userService = new UserServiceImpl();
        AccountServiceImpl accountService = new AccountServiceImpl();
        TransactionServiceImpl transactionService = new TransactionServiceImpl();

        // 1. Criar usuário Jardineiro
        System.out.println("\n1. Criando usuário Jardineiro...");
        UserDTO jardineiro = new UserDTO(
            null,
            "Jardineiro Silva",
            generateRandomEmail(),
            LocalDate.of(1990, 5, 15)
        );
        UserDTO jardineiroCreated = userService.createOrUpdateUser(jardineiro, "senha123");
        System.out.println("Jardineiro criado: " + jardineiroCreated);

        // 2. Criar usuário que será desativado
        System.out.println("\n2. Criando usuário para desativação...");
        UserDTO inactiveUser = new UserDTO(
            null,
            "Usuario Inativo",
            generateRandomEmail(),
            LocalDate.of(1985, 3, 20)
        );
        UserDTO inactiveUserCreated = userService.createOrUpdateUser(inactiveUser, "senha456");
        System.out.println("Usuário para desativação criado: " + inactiveUserCreated);

        // 3. Criar contas para o Jardineiro
        System.out.println("\n3. Criando contas para o Jardineiro...");

        // Conta Poupança
        SavingsAccountDTO savingsAccount = SavingsAccountDTO.builder()
            .name("Poupança do Jardim")
            .description("Conta poupança para emergências")
            .balance(BigDecimal.valueOf(5000.00))
            .accountType(AccountType.SAVINGS)
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .userId(jardineiroCreated.getId())
            .build();
        AccountDTO createdSavings = accountService.createAccount(savingsAccount, jardineiroCreated.getId());

        // Conta Investimento
        InvestmentAccountDTO investmentAccount = InvestmentAccountDTO.builder()
            .name("Investimentos Jardim")
            .description("Conta para investimentos diversos")
            .balance(BigDecimal.valueOf(10000.00))
            .accountType(AccountType.INVESTMENT)
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .userId(jardineiroCreated.getId())
            .build();
        AccountDTO createdInvestment = accountService.createAccount(investmentAccount, jardineiroCreated.getId());

        // Conta Meta
        GoalAccountDTO goalAccount = GoalAccountDTO.builder()
            .name("Meta Trator Novo")
            .description("Economia para comprar um trator")
            .balance(BigDecimal.valueOf(2000.00))
            .accountType(AccountType.GOAL)
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .userId(jardineiroCreated.getId())
            .build();
        AccountDTO createdGoal = accountService.createAccount(goalAccount, jardineiroCreated.getId());

        // 4. Criar transações
        System.out.println("\n4. Criando transações...");

        // TODO: Implement transaction creation for each account type
        // Note: Transaction creation requires additional implementation of
        // transaction types and related services

        // 5. Atualizar Jardineiro para João Cleber
        System.out.println("\n5. Atualizando Jardineiro para João Cleber...");
        UserDTO updateJardineiro = new UserDTO(
            jardineiroCreated.getId(),
            "João Cleber",
            jardineiroCreated.getEmail(),
            LocalDate.of(1998, 1, 17)
        );
        UserDTO updatedUser = userService.createOrUpdateUser(updateJardineiro, "senha123");
        System.out.println("Usuário atualizado: " + updatedUser);

        // 6. Desativar usuário inativo
        System.out.println("\n6. Desativando usuário...");
        userService.disableUserById(inactiveUserCreated.getId());
        System.out.println("Usuário desativado com sucesso!");

        // 7. Listar todas as contas do João Cleber
        System.out.println("\n7. Listando contas do João Cleber:");
        accountService.getAccountsByUserId(jardineiroCreated.getId())
            .forEach(System.out::println);
    }

    private static String generateRandomEmail() {
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        return "user_" + randomPart + "@example.com";
    }
}
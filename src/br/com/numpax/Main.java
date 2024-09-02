package br.com.numpax;

import java.time.LocalDate;
import java.util.Scanner;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.GoalAccount;
import br.com.numpax.domain.entities.InvestmentAccount;
import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.RiskLevelType;
import br.com.numpax.usecases.Account.CreateGoalAccount;
import br.com.numpax.usecases.Account.CreateInvestmentAccount;
import br.com.numpax.usecases.Account.CreateRegularAccount;
import br.com.numpax.usecases.Account.DepositUseCase;
import br.com.numpax.usecases.Account.MakeInvestmentUseCase;
import br.com.numpax.usecases.Account.RealizeGoalUseCase;
import br.com.numpax.usecases.Account.RegisterExpenseUseCase;
import br.com.numpax.usecases.Account.RegisterIncomeUseCase;
import br.com.numpax.usecases.Account.ShowBalanceUseCase;
import br.com.numpax.usecases.Account.ViewGoalProgressUseCase;
import br.com.numpax.usecases.Account.ViewInvestmentStatementUseCase;
import br.com.numpax.usecases.Account.WithdrawUseCase;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static User user;
    private static RegularAccount regularAccount;
    private static InvestmentAccount investmentAccount;
    private static GoalAccount goalAccount;

    public static void main(String[] args) {
        createUser();
        mainMenu();
    }

    private static void createUser() {
        System.out.println("Digite seu nome completo:");
        String name = scanner.nextLine();

        System.out.println("Digite seu email:");
        String email = scanner.nextLine();

        System.out.println("Digite sua senha:");
        String password = scanner.nextLine();

        System.out.println("Digite sua data de nascimento (YYYY-MM-DD):");
        String birthdateStr = scanner.nextLine();
        LocalDate birthdate = LocalDate.parse(birthdateStr);

        user = new User(name, email, password, birthdate);
    }  

    private static void mainMenu() {
        int option = -1;

        while (option != 0) {
            clearScreen();
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Criar Conta");
            System.out.println("2. Acessar Conta Existente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (option) {
                case 1:
                    clearScreen();
                    createAccountMenu();
                    break;
                case 2:
                    clearScreen();
                    accessAccountMenu();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    clearScreen();
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }

    private static void createAccountMenu() {
        int option = -1;

        while (option != 0) {
            clearScreen();
            System.out.println("\n--- Criar Conta ---");
            if (regularAccount == null) System.out.println("1. Conta Corrente");
            if (investmentAccount == null) System.out.println("2. Conta de Investimento");
            if (goalAccount == null) System.out.println("3. Conta de Objetivo");
            System.out.println("0. Voltar");
            System.out.print("Escolha o tipo de conta para criar: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (option) {
                case 1:
                    if (regularAccount == null) {
                        clearScreen();
                        createRegularAccount();
                    } else {
                        System.out.println("Você já tem uma Conta Corrente.");
                        pauseForUserInput();
                    }
                    break;
                case 2:
                    if (investmentAccount == null) {
                        clearScreen();
                        createInvestmentAccount();
                    } else {
                        System.out.println("Você já tem uma Conta de Investimento.");
                        pauseForUserInput();
                    }
                    break;
                case 3:
                    if (goalAccount == null) {
                        clearScreen();
                        createGoalAccount();
                    } else {
                        System.out.println("Você já tem uma Conta de Objetivo.");
                        pauseForUserInput();
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    return;
                default:
                    clearScreen();
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void createRegularAccount() {
        System.out.println("Digite o nome da conta:");
        String accountName = scanner.nextLine();

        System.out.println("Digite a descrição da conta:");
        String accountDescription = scanner.nextLine();

        regularAccount = new CreateRegularAccount().execute(accountName, accountDescription, user);

        System.out.println("Digite o valor do depósito inicial:");
        double depositAmount = scanner.nextDouble();
        regularAccount.deposit(depositAmount);

        System.out.println("Conta criada com sucesso! Saldo inicial: " + regularAccount.getBalance());
        scanner.nextLine(); // Consumir a nova linha deixada pelo nextDouble()
        pauseForUserInput();
    }

    private static void createInvestmentAccount() {
        System.out.println("Digite o nome da conta:");
        String accountName = scanner.nextLine();

        System.out.println("Digite a descrição da conta:");
        String accountDescription = scanner.nextLine();

        System.out.println("Digite o nível de risco (1- Baixo, 2- Médio, 3- Alto): ");
        int riskLevelOption = scanner.nextInt();
        RiskLevelType riskLevel;
        switch (riskLevelOption) {
            case 1:
                riskLevel = RiskLevelType.LOW;
                break;
            case 2:
                riskLevel = RiskLevelType.MEDIUM;
                break;
            case 3:
                riskLevel = RiskLevelType.HIGH;
                break;
            default:
                System.out.println("Opção inválida. Criando com nível de risco baixo por padrão.");
                riskLevel = RiskLevelType.LOW;
                break;
        }

        System.out.println("Digite o tipo de investimento (1- Fixo, 2- Variável): ");
        int investmentTypeOption = scanner.nextInt();
        AccountType accountType = AccountType.INVESTMENT;
        AccountType.InvestmentSubtypeAccount investmentSubtype;

        switch (investmentTypeOption) {
            case 1:
                investmentSubtype = AccountType.InvestmentSubtypeAccount.FIXED_INVESTMENT;
                break;
            case 2:
                investmentSubtype = AccountType.InvestmentSubtypeAccount.VARIABLE_INVESTMENT;
                break;
            default:
                System.out.println("Opção inválida. Criando com investimento fixo por padrão.");
                investmentSubtype = AccountType.InvestmentSubtypeAccount.FIXED_INVESTMENT;
                break;
        }

        investmentAccount = new CreateInvestmentAccount().execute(
                accountName,
                accountDescription,
                user,
                accountType,
                riskLevel,
                investmentSubtype // Adiciona o subtipo aqui
        );

        System.out.println("Digite o valor do depósito inicial:");
        double depositAmount = scanner.nextDouble();
        investmentAccount.deposit(depositAmount);

        System.out.println("Conta criada com sucesso! Saldo inicial: " + investmentAccount.getBalance());
        scanner.nextLine(); // Consumir a nova linha deixada pelo nextDouble()
        pauseForUserInput();
    }

    private static void createGoalAccount() {
        System.out.println("Digite o nome da conta:");
        String accountName = scanner.nextLine();

        System.out.println("Digite a descrição da conta:");
        String accountDescription = scanner.nextLine();

        System.out.println("Digite o valor alvo do objetivo:");
        double targetValue = scanner.nextDouble();

        System.out.println("Digite a taxa alvo:");
        double targetTaxRate = scanner.nextDouble();

        System.out.println("Digite a taxa mensal:");
        double monthlyTaxRate = scanner.nextDouble();

        System.out.println("Digite a data de início (YYYY-MM-DD):");
        scanner.nextLine(); // Consumir nova linha
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Digite a data alvo (YYYY-MM-DD):");
        LocalDate targetDate = LocalDate.parse(scanner.nextLine());

        // Definindo categoria fictícia para o objetivo
        Category category = new Category("Objetivo", "Economia para um objetivo específico", "icon_goal", null, true);

        goalAccount = new CreateGoalAccount().execute(
                accountName,
                accountDescription,
                user,
                targetValue, //verificar se tem que ser o valor inicial
                targetTaxRate,
                monthlyTaxRate,
                0.0, // Estimativa inicial
                0.0, // Realização inicial
                category,
                targetDate,
                startDate,
                null // Data de término inicialmente nula
        );

        System.out.println("Digite o valor do depósito inicial:");
        double depositAmount = scanner.nextDouble();
        goalAccount.deposit(depositAmount);

        System.out.println("Conta criada com sucesso! Saldo inicial: " + goalAccount.getBalance());
        scanner.nextLine(); // Consumir a nova linha deixada pelo nextDouble()
        pauseForUserInput();
    }

    private static void accessAccountMenu() {
        int option = -1;

        while (option != 0) {
            clearScreen();
            System.out.println("\n--- Acessar Conta ---");
            if (regularAccount != null) System.out.println("1. Conta Corrente");
            if (investmentAccount != null) System.out.println("2. Conta de Investimento");
            if (goalAccount != null) System.out.println("3. Conta de Objetivo");
            System.out.println("0. Voltar");
            System.out.print("Escolha a conta para acessar: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (option) {
                case 1:
                    if (regularAccount != null) {
                        accessAccount(regularAccount);
                    } else {
                        System.out.println("Você não tem uma Conta Corrente.");
                        pauseForUserInput();
                    }
                    break;
                case 2:
                    if (investmentAccount != null) {
                        accessAccount(investmentAccount);
                    } else {
                        System.out.println("Você não tem uma Conta de Investimento.");
                        pauseForUserInput();
                    }
                    break;
                case 3:
                    if (goalAccount != null) {
                        accessAccount(goalAccount);
                    } else {
                        System.out.println("Você não tem uma Conta de Objetivo.");
                        pauseForUserInput();
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    return;
                default:
                    clearScreen();
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void accessAccount(RegularAccount account) {
        int option = -1;
        while (option != 0) {
            clearScreen();
            System.out.println("\n--- Menu da Conta ---");
            System.out.println("1. Ver saldo da conta");
            System.out.println("2. Realizar saque");
            System.out.println("3. Realizar depósito");
            System.out.println("4. Registrar despesa");
            System.out.println("5. Registrar receita");

            // Adiciona opções específicas para cada tipo de conta
            if (account instanceof InvestmentAccount) {
                System.out.println("6. Realizar investimento");
                System.out.println("7. Ver extrato de investimentos");
            } else if (account instanceof GoalAccount) {
                System.out.println("6. Realizar objetivo");
                System.out.println("7. Ver progresso do objetivo");
            }

            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (option) {
                case 1:
                    new ShowBalanceUseCase().execute(account);
                    pauseForUserInput();
                    break;
                case 2:
                    new WithdrawUseCase(scanner).execute(account);
                    pauseForUserInput();
                    break;
                case 3:
                    new DepositUseCase(scanner).execute(account);
                    pauseForUserInput();
                    break;
                case 4:
                    new RegisterExpenseUseCase(scanner, user.getName()).execute(account);
                    pauseForUserInput();
                    break;
                case 5:
                    new RegisterIncomeUseCase(scanner, user.getName()).execute(account);
                    pauseForUserInput();
                    break;
                case 6:
                    if (account instanceof InvestmentAccount) {
                        new MakeInvestmentUseCase(scanner, user.getName()).execute((InvestmentAccount) account);
                    } else if (account instanceof GoalAccount) {
                        new RealizeGoalUseCase(scanner, user.getName()).execute((GoalAccount) account);
                    }
                    pauseForUserInput();
                    break;
                case 7:
                    if (account instanceof InvestmentAccount) {
                        new ViewInvestmentStatementUseCase().execute((InvestmentAccount) account);
                    } else if (account instanceof GoalAccount) {
                        new ViewGoalProgressUseCase().execute((GoalAccount) account);
                    }
                    pauseForUserInput();
                    break;
                case 0:
                    System.out.println("Voltando ao menu anterior...");
                    return;
                default:
                    clearScreen();
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pauseForUserInput() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
}

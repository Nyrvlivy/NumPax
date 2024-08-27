package br.com.numpax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.Transaction;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu nome completo:");
        String name = scanner.nextLine();

        System.out.println("Digite seu email:");
        String email = scanner.nextLine();

        System.out.println("Digite sua senha:");
        String password = scanner.nextLine();

        System.out.println("Digite sua data de nascimento (YYYY-MM-DD):");
        String birthdateStr = scanner.nextLine();
        LocalDate birthdate = LocalDate.parse(birthdateStr);

        User user = new User(name, email, password, birthdate);

        RegularAccount account = new RegularAccount("Conta Corrente", "Conta corrente do usuário", user, AccountType.CHECKING);

        System.out.println("Digite o valor do depósito inicial:");
        double depositAmount = scanner.nextDouble();
        account.deposit(depositAmount);
        System.out.println("Saldo após depósito: " + account.getBalance());

        System.out.println("Digite o valor para saque:");
        double withdrawAmount = scanner.nextDouble();
        account.withdraw(withdrawAmount);
        System.out.println("Saldo após saque: " + account.getBalance());

        scanner.nextLine();

        System.out.println("Digite a descrição da categoria:");
        String categoryDescription = scanner.nextLine();
        Category category = new Category("Alimentação", categoryDescription, "icon_food", null, true);

        System.out.println("Digite o valor da despesa:");
        BigDecimal expenseAmount = scanner.nextBigDecimal();
        scanner.nextLine();

        Transaction expenseTransaction = new Transaction(
            "EXP001",
            "Compra de Supermercado",
            "Compra de alimentos no supermercado",
            expenseAmount,
            category,
            account,
            NatureOfTransaction.EXPENSE,
            null,
            user.getName(),
            LocalDate.now(),
            RepeatableType.NEVER,
            "Compra realizada em " + LocalDate.now()
        );

        expenseTransaction.apply();
        System.out.println("Saldo após aplicação de transação de despesa: " + account.getBalance());

        System.out.println("Digite o valor da receita:");
        BigDecimal incomeAmount = scanner.nextBigDecimal();
        scanner.nextLine(); 

        Transaction incomeTransaction = new Transaction(
            "INC001",
            "Salário",
            "Depósito do salário mensal",
            incomeAmount,
            category,
            account,
            NatureOfTransaction.INCOME,
            user.getName(),
            null,
            LocalDate.now(),
            RepeatableType.MONTHLY,
            "Salário de " + LocalDate.now().getMonth()
        );

        incomeTransaction.apply();
        System.out.println("Saldo após aplicação de transação de receita: " + account.getBalance());

        System.out.println("Resumo Final:");
        System.out.println("Nome do usuário: " + user.getName());
        System.out.println("Saldo final da conta: " + account.getBalance());

        scanner.close();
    }
}

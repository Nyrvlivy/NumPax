package br.com.numpax.usecases.Account;

import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.RegularAccount;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.application.enums.RepeatableType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class RegisterIncomeUseCase {
    private final Scanner scanner;
    private final String userName;

    public RegisterIncomeUseCase(Scanner scanner, String userName) {
        this.scanner = scanner;
        this.userName = userName;
    }

    public void execute(Account account) {
        double incomeAmount = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Digite o valor da receita: ");
                incomeAmount = Double.parseDouble(scanner.nextLine());
                if (incomeAmount <= 0) {
                    System.out.println("O valor da receita deve ser positivo. Tente novamente.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
            }
        }

        Category category = new Category("Salário", "Depósito do salário mensal", "icon_salary", null, true);


        BigDecimal incomeAmountBigDecimal = BigDecimal.valueOf(incomeAmount);

        RegularAccount regularAccount = (RegularAccount) account;

        Transaction incomeTransaction = new Transaction(
            "INC" + System.currentTimeMillis(),
            "Salário",
            "Depósito do salário mensal",
            incomeAmountBigDecimal,
            category,
            regularAccount,
            NatureOfTransaction.INCOME,
            userName, // receiver
            null, // sender
            LocalDate.now(),
            RepeatableType.MONTHLY,
            "Salário de " + LocalDate.now().getMonth()
        );

        incomeTransaction.apply();
        System.out.printf("Receita registrada com sucesso. Saldo após aplicação de receita: R$ %.2f%n", account.getBalance());
    }
}

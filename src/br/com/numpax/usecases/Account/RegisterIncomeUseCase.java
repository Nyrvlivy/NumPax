package br.com.numpax.usecases.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.Transaction;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

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
                System.out.println("Digite o valor da receita:");
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

  
    // Convertendo incomeAmount para BigDecimal
    BigDecimal incomeAmountBigDecimal = BigDecimal.valueOf(incomeAmount);

    // Convertendo Account para RegularAccount, se necessário
    RegularAccount regularAccount = (RegularAccount) account;

    Transaction incomeTransaction = new Transaction(
        "INC" + System.currentTimeMillis(), // code
        "Salário", // name
        "Depósito do salário mensal", // description
        incomeAmountBigDecimal, // amount
        category, // category
        regularAccount, // regularAccount
        NatureOfTransaction.INCOME, // natureOfTransaction
        userName, // receiver
        null, // sender
        LocalDate.now(), // transactionDate
        RepeatableType.MONTHLY, // repeatableType
        "Salário de " + LocalDate.now().getMonth() // note
    );

        incomeTransaction.apply();
        System.out.printf("Receita registrada com sucesso. Saldo após aplicação de receita: R$ %.2f%n", account.getBalance());
    }
}

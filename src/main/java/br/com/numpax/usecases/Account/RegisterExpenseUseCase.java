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

public class RegisterExpenseUseCase {
    private final Scanner scanner;
    private final String userName;

    public RegisterExpenseUseCase(Scanner scanner, String userName) {
        this.scanner = scanner;
        this.userName = userName;
    }

    public void execute(Account account) {
        System.out.println("Digite a descrição da categoria:");
        String categoryDescription = scanner.nextLine();
        Category category = new Category("Alimentação", categoryDescription, "icon_food", null, true);

        System.out.println("Digite o valor da despesa:");
        double expenseAmount = scanner.nextDouble();
        scanner.nextLine();

        Transaction expenseTransaction = new Transaction(
            "EXP" + System.currentTimeMillis(),
            "Despesa",
            categoryDescription,
            BigDecimal.valueOf(expenseAmount),
            category,
            (RegularAccount) account,
            NatureOfTransaction.EXPENSE,
            null,
            userName,
            LocalDate.now(),
            RepeatableType.NEVER,
            "Despesa realizada em " + LocalDate.now()
        );

        expenseTransaction.apply();
        System.out.println("Despesa registrada com sucesso. Saldo após aplicação de despesa: " + account.getBalance());
    }
}

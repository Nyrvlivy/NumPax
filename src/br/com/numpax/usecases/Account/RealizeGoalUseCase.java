package br.com.numpax.usecases.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import br.com.numpax.domain.entities.GoalAccount;
import br.com.numpax.domain.entities.Transaction;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

public class RealizeGoalUseCase {
    private final Scanner scanner;
    private final String userName;

    public RealizeGoalUseCase(Scanner scanner, String userName) {
        this.scanner = scanner;
        this.userName = userName;
    }

    public void execute(GoalAccount account) {
        System.out.println("Digite o valor para realizar o objetivo:");
        double goalAmount = scanner.nextDouble();
        scanner.nextLine();

    Transaction goalTransaction = new Transaction(
    "GOAL" + System.currentTimeMillis(),
    "Realização de Objetivo",
    "Valor destinado à realização de um objetivo",
    BigDecimal.valueOf(goalAmount),
    null, 
    account,
    NatureOfTransaction.GOAL,
    null, 
    userName, 
    LocalDate.now(),
    RepeatableType.NEVER,
    "Objetivo realizado em " + LocalDate.now()
);

        goalTransaction.apply();
        System.out.println("Objetivo realizado com sucesso. Saldo após realização de objetivo: " + account.getBalance());
    }
}

package br.com.numpax.usecases.Account;

import br.com.numpax.domain.entities.InvestmentAccount;
import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.Transaction;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class MakeInvestmentUseCase {
    private final Scanner scanner;
    private final String userName;

    public MakeInvestmentUseCase(Scanner scanner, String userName) {
        this.scanner = scanner;
        this.userName = userName;
    }

    public void execute(InvestmentAccount account) {
        System.out.print("Digite o valor do investimento: ");
        BigDecimal investmentAmount = new BigDecimal(scanner.nextLine());

        scanner.nextLine();

        Transaction investmentTransaction = new Transaction(
            "INV" + System.currentTimeMillis(),
            "Investimento",
            "Aplicação em investimento",
            investmentAmount,
            null,
            (RegularAccount) account,
            NatureOfTransaction.INVESTMENT,
            null,
            userName,
            LocalDate.now(),
            RepeatableType.NEVER,
            "Investimento realizado em " + LocalDate.now()
        );

        investmentTransaction.apply();
        System.out.println("Investimento realizado com sucesso. Saldo após aplicação de investimento: " + account.getBalance());
    }
}

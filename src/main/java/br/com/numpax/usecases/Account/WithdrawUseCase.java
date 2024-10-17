package br.com.numpax.usecases.Account;

import br.com.numpax.infrastructure.entities.RegularAccount;
import br.com.numpax.API.V1.exceptions.InsufficientFundsException;

import java.util.Scanner;

public class WithdrawUseCase {
    private final Scanner scanner;

    public WithdrawUseCase(Scanner scanner) {
        this.scanner = scanner;
    }


    public void execute(RegularAccount account) {
        System.out.print("Digite o valor para saque: ");
        double withdrawAmount = scanner.nextDouble();
        scanner.nextLine();
        try {
            account.withdraw(withdrawAmount);
            System.out.println("Saque realizado com sucesso. Saldo após saque: " + account.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Saque falhou. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

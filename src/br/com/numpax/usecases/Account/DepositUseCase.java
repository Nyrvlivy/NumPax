package br.com.numpax.usecases.Account;

import java.util.Scanner;

import br.com.numpax.domain.entities.RegularAccount;

public class DepositUseCase {
    private final Scanner scanner;

    public DepositUseCase(Scanner scanner) {
        this.scanner = scanner;
    }

    public void execute(RegularAccount account) {
        System.out.println("Digite o valor para depósito:");
        double depositAmount = scanner.nextDouble();
        scanner.nextLine();
        account.deposit(depositAmount);
        System.out.println("Depósito realizado com sucesso. Saldo após depósito: " + account.getBalance());
    }
}
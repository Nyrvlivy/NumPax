package br.com.numpax.usecases.Account;

import br.com.numpax.domain.entities.Account;

public class ShowBalanceUseCase {
    public void execute(Account account) {
        System.out.println("Saldo atual da conta: " + account.getBalance());
    }
}

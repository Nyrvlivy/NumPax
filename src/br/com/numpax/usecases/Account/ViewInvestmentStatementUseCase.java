package br.com.numpax.usecases.Account;

import br.com.numpax.domain.entities.InvestmentAccount;

public class ViewInvestmentStatementUseCase {
    public void execute(InvestmentAccount account) {
        account.viewInvestmentStatement();
    }
}

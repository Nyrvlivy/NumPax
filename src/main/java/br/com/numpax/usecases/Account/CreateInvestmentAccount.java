package br.com.numpax.usecases.Account;

import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.enums.RiskLevelType;

public class CreateInvestmentAccount {
    public InvestmentAccount execute(
        String name,
        String description,
        User user,
        AccountType accountType,
        RiskLevelType riskLevelType,
        AccountType.InvestmentSubtypeAccount investmentSubtypeAccount
    ) {
        return new InvestmentAccount(
            name,
            description,
            user,
            accountType,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            riskLevelType,
            investmentSubtypeAccount
        );
    }
}

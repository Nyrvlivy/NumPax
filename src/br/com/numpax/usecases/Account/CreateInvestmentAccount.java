package br.com.numpax.usecases.Account;

import br.com.numpax.domain.entities.InvestmentAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.RiskLevelType;

public class CreateInvestmentAccount {
    public InvestmentAccount execute(
        String name,
        String description,
        User user,
        AccountType accountType, // Novo parâmetro
        RiskLevelType riskLevelType,
        AccountType.InvestmentSubtypeAccount investmentSubtypeAccount
    ){
        return new InvestmentAccount(
            name, 
            description, 
            user, 
            accountType, // Novo parâmetro no construtor
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
            riskLevelType,
            investmentSubtypeAccount
        );
    };
}
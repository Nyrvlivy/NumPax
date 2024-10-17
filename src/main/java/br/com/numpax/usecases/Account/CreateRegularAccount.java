package br.com.numpax.usecases.Account;

import br.com.numpax.infrastructure.entities.RegularAccount;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.application.enums.AccountType;

public class CreateRegularAccount {
    public RegularAccount execute(String name, String description, User user) {
        return new RegularAccount(name, description, user, AccountType.CHECKING);
    }
}

package br.com.numpax.usecases.Account;

import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;

public class CreateRegularAccount {
    public RegularAccount execute(String name, String description, User user) {
        return new RegularAccount(name, description, user, AccountType.CHECKING);
    }
}

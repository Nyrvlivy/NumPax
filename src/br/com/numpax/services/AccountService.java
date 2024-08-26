package br.com.numpax.services;

import java.math.BigDecimal;
import java.util.List;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.User;

public interface AccountService {
    Account createAccount(User user, BigDecimal balance);
    Account updateAccount(String id, Account account);
    void deleteAccount(String id);
    Account getAccountById(String id);
    List<Account> getAccountsByUser(User user);
}

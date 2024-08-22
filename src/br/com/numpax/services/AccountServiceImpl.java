package br.com.numpax.services;

import java.math.BigDecimal;
import java.util.List;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.repositories.AccountRepository;
import br.com.numpax.exceptions.ResourceNotFoundException;

public class AccountServiceImpl implements AccountService{
        private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(User user, BigDecimal balance){
        Account account = new Account(user.getName(), "Pensar em descrição", user);
        account.setUser(user);
        account.setBalance(balance);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(String id, Account account) {
        Account existingAccount = getAccountById(id);
        existingAccount.setBalance(account.getBalance());
        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.delete(id);
    }

    @Override
    public Account getAccountById(String id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    @Override
    public List<Account> getAccountsByUser(User user) {
        return accountRepository.findByUser(user);
    }
}
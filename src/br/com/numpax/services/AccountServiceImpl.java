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
        accountRepository.deleteById(id);
    }

    @Override
    public Account getAccountById(String id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    @Override
    public List<Account> getAccountsByUser(User user) {
        return accountRepository.findByUser(user);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void deposit(String id, BigDecimal amount){
        Account account = getAccountById(id);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
    
    public void withdraw(String id, BigDecimal amount){
        Account account =  getAccountById(id);
        if(account.getBalance().compareTo(amount) >= 0){
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
        }
    }

    public void tranferFunds(String fromAccountId, String toAccountId, BigDecimal amount){
        withdraw(fromAccountId, amount);
        deposit(toAccountId, amount);
    }

    public BigDecimal calculateTotalFunds(){
        List<Account> accounts = getAllAccounts();
        return accounts.stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    
}

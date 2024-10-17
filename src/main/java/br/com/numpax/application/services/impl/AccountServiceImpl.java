package br.com.numpax.application.services.impl;

import br.com.numpax.application.services.AccountService;
import br.com.numpax.infrastructure.dao.AccountDAO;
import br.com.numpax.infrastructure.dao.impl.AccountDAOImpl;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.API.V1.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;

    public AccountServiceImpl() {
        this.accountDAO = new AccountDAOImpl();
    }

    @Override
    public Account createAccount(User user, BigDecimal balance) {
        Account account = new Account(user.getName(), "Descrição da conta", user);
        account.setBalance(balance);
        accountDAO.save(account);
        return account;
    }

    @Override
    public Account updateAccount(String id, Account account) {
        Account existingAccount = getAccountById(id);
        existingAccount.setName(account.getName());
        existingAccount.setDescription(account.getDescription());
        existingAccount.setBalance(account.getBalance());
        existingAccount.setIsActive(account.getIsActive());
        existingAccount.setUpdatedAt(account.getUpdatedAt());
        accountDAO.update(existingAccount);
        return existingAccount;
    }

    @Override
    public void deleteAccount(String id) {
        accountDAO.deleteById(id);
    }

    @Override
    public Account getAccountById(String id) {
        return accountDAO.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada com ID: " + id));
    }

    @Override
    public List<Account> getAccountsByUser(User user) {
        return accountDAO.findByUser(user);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDAO.findAll();
    }
}

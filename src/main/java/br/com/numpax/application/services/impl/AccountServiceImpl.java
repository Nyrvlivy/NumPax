package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.mappers.AccountMapper;
import br.com.numpax.application.services.AccountService;
import br.com.numpax.infrastructure.dao.AccountDAO;
import br.com.numpax.infrastructure.dao.impl.AccountDAOImpl;
import br.com.numpax.infrastructure.entities.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO, String userId) {
        Account account = AccountMapper.toEntity(accountDTO);
        account.setUserId(userId);
        accountDAO.saveOrUpdate(account);

        return AccountMapper.toDTO(account);
    }

    @Override
    public AccountDTO getAccountById(String id) {
        Account account = accountDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada com o ID: " + id));

        return AccountMapper.toDTO(account);
    }

    @Override
    public List<AccountDTO> getAccountsByUserId(String userId) {
        List<Account> accounts = accountDAO.findByUserId(userId);
        return accounts.stream()
            .map(AccountMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountDAO.findAll();
        return accounts.stream()
            .map(AccountMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void disableAccountById(String id) {
        accountDAO.disableById(id);
    }

    @Override
    public List<AccountDTO> getAllActiveAccounts() {
        List<Account> accounts = accountDAO.findAllActive();
        return accounts.stream()
            .map(AccountMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAllInactiveAccounts() {
        List<Account> accounts = accountDAO.findAllInactive();
        return accounts.stream()
            .map(AccountMapper::toDTO)
            .collect(Collectors.toList());
    }
}

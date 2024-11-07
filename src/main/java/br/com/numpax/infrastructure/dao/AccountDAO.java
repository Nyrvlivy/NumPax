package br.com.numpax.infrastructure.dao;

import br.com.numpax.infrastructure.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {
    void saveOrUpdate(Account account);
    void deleteById(String id);
    void disableById(String id);
    Optional<Account> findById(String id);
    List<Account> findByUserId(String userId);
    List<Account> findAll();
    List<Account> findAllActive();
    List<Account> findAllInactive();
}

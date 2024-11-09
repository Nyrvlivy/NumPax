package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.Account;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(String accountId);
    void create(Account account);
    void update(Account account);
    void delete(String accountId);
    boolean existsById(String accountId);
} 
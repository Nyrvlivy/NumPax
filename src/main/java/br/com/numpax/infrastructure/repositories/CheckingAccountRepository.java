package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.CheckingAccount;

import java.util.List;
import java.util.Optional;

public interface CheckingAccountRepository {

    void create(CheckingAccount account);

    Optional<CheckingAccount> findById(String accountId);

    void update(CheckingAccount account);

    void delete(String accountId);

    List<CheckingAccount> findAll();

    List<CheckingAccount> findAllActive();

    List<CheckingAccount> findAllInactive();
}

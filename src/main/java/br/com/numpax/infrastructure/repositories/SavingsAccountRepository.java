package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.SavingsAccount;

import java.util.List;
import java.util.Optional;

public interface SavingsAccountRepository {

    void create(SavingsAccount account);

    Optional<SavingsAccount> findById(String accountId);

    void update(SavingsAccount account);

    void delete(String accountId);

    List<SavingsAccount> findAll();

    List<SavingsAccount> findAllActive();

    List<SavingsAccount> findAllInactive();
}

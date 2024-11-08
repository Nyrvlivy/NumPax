package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.GoalAccount;

import java.util.List;
import java.util.Optional;

public interface GoalAccountRepository {

    void create(GoalAccount account);

    Optional<GoalAccount> findById(String accountId);

    void update(GoalAccount account);

    void delete(String accountId);

    List<GoalAccount> findAll();

    List<GoalAccount> findAllActive();

    List<GoalAccount> findAllInactive();
}

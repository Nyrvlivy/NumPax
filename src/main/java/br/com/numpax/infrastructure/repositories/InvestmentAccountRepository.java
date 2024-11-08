package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.InvestmentAccount;

import java.util.List;
import java.util.Optional;

public interface InvestmentAccountRepository {

    void create(InvestmentAccount account);

    Optional<InvestmentAccount> findById(String accountId);

    void update(InvestmentAccount account);

    void delete(String accountId);

    List<InvestmentAccount> findAll();

    List<InvestmentAccount> findAllActive();

    List<InvestmentAccount> findAllInactive();
}

package br.com.numpax.domain.repositories;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.User;

import java.util.List;

public interface AccountRepository extends GenericRepository<Account, String> {
    List<Account> findByUser(User user);
}

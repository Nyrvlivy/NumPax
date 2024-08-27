package br.com.numpax.domain.repositories;

import java.util.List;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.User;

public interface AccountRepository extends GenericRepository<Account, String>{
    List<Account> findByUser(User user);
}

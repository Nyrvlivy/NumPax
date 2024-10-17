package br.com.numpax.infrastructure.dao;

import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.RegularAccount;
import br.com.numpax.infrastructure.entities.User;
import java.util.List;
import java.util.Optional;

public interface AccountDAO {
    void save(Account account);
    void update(Account account);
    void deleteById(String id);
    Optional<Account> findById(String id);
    List<Account> findByUser(User user);
    List<Account> findAll();
    boolean existsById(String id);
    Optional<RegularAccount> findRegularAccountById(String id);

}

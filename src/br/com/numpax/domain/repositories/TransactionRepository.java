package br.com.numpax.domain.repositories;

import java.util.List;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.Transaction;

public interface TransactionRepository extends GenericRepository<Transaction, String>{
    List<Transaction> findByAccount(Account account);
}

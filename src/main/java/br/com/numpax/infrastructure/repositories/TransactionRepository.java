package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.Transaction;

//public interface TransactionRepository extends BaseRepository<Transaction, String> {
public interface TransactionRepository {
    void create(Transaction transaction);

}

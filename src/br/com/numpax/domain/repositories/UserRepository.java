package br.com.numpax.domain.repositories;

import java.util.Optional;

import br.com.numpax.domain.entities.User;

public interface UserRepository  extends GenericRepository<User, String>{
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}

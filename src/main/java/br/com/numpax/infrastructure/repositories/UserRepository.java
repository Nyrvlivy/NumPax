package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, String> {

    Optional<User> findByEmail(String email);

}

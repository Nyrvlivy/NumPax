package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void create(User user);

    Optional<User> findById(String userId);

    Optional<User> findByEmail(String email);

    void update(User user);

    void delete(String userId);

    List<User> findAll();

    List<User> findAllActive();

    List<User> findAllInactive();
}

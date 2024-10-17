package br.com.numpax.infrastructure.dao;

import br.com.numpax.infrastructure.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void saveOrUpdate(User user);
    void disableById(String id);
    Optional<User> findSimpleById(String id);
    Optional<User> findDetailedById(String id);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findAllActive();
    List<User> findAllInactive();
}

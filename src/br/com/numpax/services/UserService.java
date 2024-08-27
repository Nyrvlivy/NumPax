package br.com.numpax.services;

import br.com.numpax.domain.entities.User;

public interface UserService {
    User createUser(User user);
    User updateUser(String id, User user);
    void deleteUser(String id);
    User getUserById(String id);
    User getUserByEmail(String email);
}

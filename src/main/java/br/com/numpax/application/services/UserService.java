package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.UserDTO;
import br.com.numpax.API.V1.dto.DetailedUserDTO;

import java.util.List;

public interface UserService {
    UserDTO createOrUpdateUser(UserDTO userDTO, String password);
    UserDTO getUserById(String id);
    DetailedUserDTO getDetailedUserById(String id);
    List<UserDTO> getAllUsers();
    List<UserDTO> getAllActiveUsers();
    List<UserDTO> getAllInactiveUsers();
    void disableUserById(String id);
}

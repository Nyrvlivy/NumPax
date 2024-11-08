package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.UserRequestDTO;
import br.com.numpax.API.V1.dto.request.UserUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.DetailedUserResponseDTO;
import br.com.numpax.API.V1.dto.response.UserResponseDTO;
import br.com.numpax.infrastructure.entities.User;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO dto);

    DetailedUserResponseDTO getUserById(String userId);

    User findUserById(String userId);

    UserResponseDTO updateUser(String userId, UserUpdateRequestDTO dto);

    void deleteUser(String userId);

    List<UserResponseDTO> listAllUsers();

    List<UserResponseDTO> listAllActiveUsers();

    List<UserResponseDTO> listAllInactiveUsers();

    User authenticateUser(String email, String password);
}

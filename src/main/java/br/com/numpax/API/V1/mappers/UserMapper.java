package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.UserRequestDTO;
import br.com.numpax.API.V1.dto.request.UserUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.DetailedUserResponseDTO;
import br.com.numpax.API.V1.dto.response.UserResponseDTO;
import br.com.numpax.infrastructure.entities.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUserId(java.util.UUID.randomUUID().toString());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(hashPassword(dto.getPassword()));
        user.setBirthdate(dto.getBirthdate());
        user.setIsActive(true);  // Cannot resolve method 'setIsActive' in 'User'
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBirthdate(user.getBirthdate());
        return dto;
    }

    public static DetailedUserResponseDTO toDetailedResponseDTO(User user) {
        DetailedUserResponseDTO dto = new DetailedUserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBirthdate(user.getBirthdate());
        dto.setActive(true);
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    public static void updateEntity(User user, UserUpdateRequestDTO dto) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(hashPassword(dto.getPassword()));
        }
        user.setBirthdate(dto.getBirthdate());
        user.setUpdatedAt(java.time.LocalDateTime.now());
    }

    private static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}

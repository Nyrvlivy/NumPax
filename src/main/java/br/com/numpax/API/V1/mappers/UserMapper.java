package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.DetailedUserDTO;
import br.com.numpax.API.V1.dto.UserDTO;
import br.com.numpax.infrastructure.entities.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static User toEntity(UserDTO dto, String password) {
        return new User(
            dto.getId(),
            dto.getName(),
            dto.getEmail(),
            password,
            dto.getBirthdate(),
            true,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }


    public static UserDTO toSimpleDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getBirthdate()
        );
    }

    public static DetailedUserDTO toDetailedDTO(User user) {
        return new DetailedUserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getBirthdate(),
            user.isActive(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}

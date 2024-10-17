package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.DetailedUserDTO;
import br.com.numpax.API.V1.dto.UserDTO;
import br.com.numpax.API.V1.mappers.UserMapper;
import br.com.numpax.infrastructure.dao.UserDAO;
import br.com.numpax.infrastructure.dao.impl.UserDAOImpl;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.application.services.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public UserDTO createOrUpdateUser(UserDTO userDTO, String password) {
        String hashedPassword = hashPassword(password);

        User user = UserMapper.toEntity(userDTO, hashedPassword);
        userDAO.saveOrUpdate(user);
        return UserMapper.toSimpleDTO(user);
    }

    @Override
    public UserDTO getSimpleUserById(String id) {
        User user = userDAO.findSimpleById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
        return UserMapper.toSimpleDTO(user);
    }

    @Override
    public DetailedUserDTO getDetailedUserById(String id) {
        User user = userDAO.findDetailedById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
        return UserMapper.toDetailedDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.findAll().stream()
            .map(UserMapper::toSimpleDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllActiveUsers() {
        return userDAO.findAllActive().stream()
            .map(UserMapper::toSimpleDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllInactiveUsers() {
        return userDAO.findAllInactive().stream()
            .map(UserMapper::toSimpleDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void disableUserById(String id) {
        userDAO.disableById(id);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar a senha", e);
        }
    }
}


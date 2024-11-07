package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.dto.DetailedUserDTO;
import br.com.numpax.API.V1.dto.UserDTO;
import br.com.numpax.API.V1.mappers.UserMapper;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.services.AccountService;
import br.com.numpax.infrastructure.dao.UserDAO;
import br.com.numpax.infrastructure.dao.impl.UserDAOImpl;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.application.services.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public UserDTO createOrUpdateUser(UserDTO userDTO, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = UserMapper.toEntity(userDTO, hashedPassword);
        userDAO.saveOrUpdate(user);

        if (userDTO.getId() == null) {
            createDefaultAccount(user);
        }

        return UserMapper.toSimpleDTO(user);
    }

    private void createDefaultAccount(User user) {
        AccountService accountService = new AccountServiceImpl();
        AccountDTO defaultAccountDTO = new AccountDTO(
            null,
            "Conta Corrente",
            "Conta corrente padrão",
            BigDecimal.ZERO,
            AccountType.CHECKING,
            true,
            LocalDateTime.now(),
            LocalDateTime.now(),
            user.getId()
        );
        accountService.createAccount(defaultAccountDTO, user.getId());
    }

    @Override
    public UserDTO getUserById(String id) {
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
}


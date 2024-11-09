package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.UserRequestDTO;
import br.com.numpax.API.V1.dto.request.UserUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.DetailedUserResponseDTO;
import br.com.numpax.API.V1.dto.response.UserResponseDTO;
import br.com.numpax.API.V1.exceptions.EmailAlreadyUsedException;
import br.com.numpax.API.V1.exceptions.UserNotFoundException;
import br.com.numpax.API.V1.mappers.UserMapper;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.application.services.UserService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.CheckingAccount;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.CheckingAccountRepository;
import br.com.numpax.infrastructure.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CheckingAccountRepository checkingAccountRepository;

    public UserServiceImpl(UserRepository userRepository, CheckingAccountRepository checkingAccountRepository) {
        this.userRepository = userRepository;
        this.checkingAccountRepository = checkingAccountRepository;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        // Verificar se o email já está em uso
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException("O email já está em uso.");
        }

        // Mapear DTO para Entidade
        User user = UserMapper.toEntity(dto);

        // Criar Usuário
        userRepository.create(user);

        // Criar Conta Corrente Padrão
        createDefaultCheckingAccount(user);

        // Retornar UserResponseDTO
        return UserMapper.toResponseDTO(user);
    }

    private void createDefaultCheckingAccount(User user) {
        CheckingAccount defaultAccount = new CheckingAccount();
        defaultAccount.setAccountId(UUID.randomUUID().toString());
        defaultAccount.setName("Default Personal Checking Account");
        defaultAccount.setDescription("Conta corrente pessoal padrão");
        defaultAccount.setBalance(java.math.BigDecimal.ZERO);
        defaultAccount.setAccountType(AccountType.CHECKING);
        defaultAccount.setIsActive(true);
        defaultAccount.setUserId(user);
        defaultAccount.setCreatedAt(LocalDateTime.now());
        defaultAccount.setUpdatedAt(LocalDateTime.now());
        defaultAccount.setBankCode("0000");
        defaultAccount.setAgency("0000");
        defaultAccount.setAccountNumber("0000000000");

        // Salvar a conta
        checkingAccountRepository.create(defaultAccount);
    }

    @Override
    public DetailedUserResponseDTO getUserById(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        return UserMapper.toDetailedResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(String userId, UserUpdateRequestDTO dto) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        // Buscar usuário existente
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        // Verificar se o email está sendo alterado e se já está em uso
        if (!user.getEmail().equals(dto.getEmail())) {
            if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new EmailAlreadyUsedException("O email já está em uso.");
            }
        }

        // Atualizar os dados do usuário
        UserMapper.updateEntity(user, dto);

        // Atualizar o usuário no repositório
        userRepository.update(user);

        return UserMapper.toResponseDTO(user);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        user.setIsActive(false);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.update(user);
    }

    @Override
    public List<UserResponseDTO> listAllUsers() {
        return userRepository.findAll().stream()
            .map(UserMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> listAllActiveUsers() {
        return userRepository.findAllActive().stream()
            .map(UserMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> listAllInactiveUsers() {
        return userRepository.findAllInactive().stream()
            .map(UserMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public User findUserById(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public User authenticateUser(String email, String password) {
        // Buscar usuário pelo email
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        // Verificar se a conta está ativa
        if (!user.isActive()) {
            throw new AuthenticationException("User account is inactive");
        }

        // Verificar a senha
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        return user;
    }

}

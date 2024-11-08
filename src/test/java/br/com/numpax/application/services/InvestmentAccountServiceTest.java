package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.InvestmentAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.InvestmentAccountResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.exceptions.DuplicateAccountException;
import br.com.numpax.application.services.impl.InvestmentAccountServiceImpl;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestmentAccountServiceTest {

    @Mock
    private InvestmentAccountRepository repository;

    private InvestmentAccountService service;

    @BeforeEach
    void setUp() {
        service = new InvestmentAccountServiceImpl(repository);
    }

    @Test
    void createAccount_Success() {
        // Arrange
        InvestmentAccountRequestDTO requestDTO = new InvestmentAccountRequestDTO();
        requestDTO.setName("Test Account");
        requestDTO.setInstitution("Test Bank");

        when(repository.existsByAccountNumber(any())).thenReturn(false);
        when(repository.existsByAgencyAndAccountNumber(any(), any())).thenReturn(false);

        // Act
        InvestmentAccountResponseDTO response = service.createAccount(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("Test Account", response.getName());
        assertEquals("Test Bank", response.getInstitution());
        verify(repository).create(any(InvestmentAccount.class));
    }

    @Test
    void createAccount_DuplicateAccount() {
        // Arrange
        InvestmentAccountRequestDTO requestDTO = new InvestmentAccountRequestDTO();
        requestDTO.setAccountNumber("123");
        when(repository.existsByAccountNumber("123")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateAccountException.class, () -> service.createAccount(requestDTO));
        verify(repository, never()).create(any());
    }

    @Test
    void getAccountById_Success() {
        // Arrange
        String accountId = "test-id";
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId(accountId);
        account.setName("Test Account");

        when(repository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        InvestmentAccountResponseDTO response = service.getAccountById(accountId);

        // Assert
        assertNotNull(response);
        assertEquals(accountId, response.getAccountId());
        assertEquals("Test Account", response.getName());
    }

    @Test
    void getAccountById_NotFound() {
        // Arrange
        String accountId = "non-existent-id";
        when(repository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> service.getAccountById(accountId));
    }

    @Test
    void listAllAccounts_Success() {
        // Arrange
        InvestmentAccount account1 = new InvestmentAccount();
        account1.setName("Account 1");
        InvestmentAccount account2 = new InvestmentAccount();
        account2.setName("Account 2");

        when(repository.findAll()).thenReturn(Arrays.asList(account1, account2));

        // Act
        List<InvestmentAccountResponseDTO> accounts = service.listAllAccounts();

        // Assert
        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals("Account 1", accounts.get(0).getName());
        assertEquals("Account 2", accounts.get(1).getName());
    }
} 
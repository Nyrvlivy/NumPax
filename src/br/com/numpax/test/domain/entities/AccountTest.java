package br.com.numpax.test.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.User;

public class AccountTest {
    public static void main(String[] args) {
        testAccountCreation();
        testSetName();
        testSetDescription();
        testSetBalance();
        testSetIsActive();
        testSetUser();
    }

    private static void testAccountCreation() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        Account account = new Account("Main Account", "Primary account", user);

        assert account.getId() != null;
        assert "Main Account".equals(account.getName());
        assert "Primary account".equals(account.getDescription());
        assert BigDecimal.valueOf(0.0).equals(account.getBalance());
        assert account.getIsActive();
        assert user.equals(account.getUser());
        assert account.getCreatedAt() != null;
        assert account.getUpdatedAt() != null;
    }

    private static void testSetName() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        Account account = new Account("Main Account", "Primary account", user);
        account.setName("New Name");

        assert "New Name".equals(account.getName());
    }

    private static void testSetDescription() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        Account account = new Account("Main Account", "Primary account", user);
        account.setDescription("New Description");

        assert "New Description".equals(account.getDescription());
    }

    private static void testSetBalance() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        Account account = new Account("Main Account", "Primary account", user);
        account.setBalance(BigDecimal.valueOf(100.0));

        assert BigDecimal.valueOf(100.0).equals(account.getBalance());
    }

    private static void testSetIsActive() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        Account account = new Account("Main Account", "Primary account", user);
        account.setIsActive(false);

        assert !account.getIsActive();
    }

    private static void testSetUser() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        Account account = new Account("Main Account", "Primary account", user);
        User newUser = new User("Jane Doe", "jane.doe@example.com", "password", LocalDate.of(1992, 2, 2));
        account.setUser(newUser);

        assert newUser.equals(account.getUser());
    }
}

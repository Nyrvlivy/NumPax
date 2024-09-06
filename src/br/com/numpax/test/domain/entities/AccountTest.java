package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;

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
        User user = new User("Agatha Isabela", "agatha.isabela@example.com", "password123@", LocalDate.of(1990, 7, 19));
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
        User user = new User("André Kevin Antonio Santos", "andre.kevin@example.com", "password123@", LocalDate.of(1995, 12, 14));
        Account account = new Account("Main Account", "Primary account", user);
        account.setName("New Name");

        assert "New Name".equals(account.getName());
    }

    private static void testSetDescription() {
        User user = new User("Carlos Eduardo", "carlos.eduardo@example.com", "password123@", LocalDate.of(1988, 5, 23));
        Account account = new Account("Main Account", "Primary account", user);
        account.setDescription("New Description");

        assert "New Description".equals(account.getDescription());
    }

    private static void testSetBalance() {
        User user = new User("Daniela Maria", "daniela.maria@example.com", "password123@", LocalDate.of(1993, 8, 11));
        Account account = new Account("Main Account", "Primary account", user);
        account.setBalance(BigDecimal.valueOf(100.0));

        assert BigDecimal.valueOf(100.0).equals(account.getBalance());
    }

    private static void testSetIsActive() {
        User user = new User("Beatriz Helena", "beatriz.helena@example.com", "password123@", LocalDate.of(2000, 1, 9));
        Account account = new Account("Main Account", "Primary account", user);
        account.setIsActive(false);

        assert !account.getIsActive();
    }

    private static void testSetUser() {
        User user = new User("Fernanda Aparecida", "fernanda.aparecida@example.com", "password123@", LocalDate.of(1992, 3, 30));
        Account account = new Account("Main Account", "Primary account", user);
        User newUser = new User("Roberto Carlos", "roberto.carlos@example.com", "password123@", LocalDate.of(1985, 11, 5));
        account.setUser(newUser);

        assert newUser.equals(account.getUser());
    }
}

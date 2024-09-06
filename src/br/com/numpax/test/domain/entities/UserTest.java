package br.com.numpax.test.domain.entities;

import java.time.LocalDate;

import br.com.numpax.domain.entities.User;

public class UserTest {
    public static void main(String[] args) {
        testUserCreation();
        testSetName();
        testSetEmail();
        testSetPassword();
        testSetBirthdate();
        testSetIsActive();
        testDeactivate();
        testCheckPassword();
    }

    private static void testUserCreation() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));

        assert user.getId() != null;
        assert "John Doe".equals(user.getName());
        assert "john.doe@example.com".equals(user.getEmail());
        assert "password".equals(user.getPassword());
        assert LocalDate.of(1990, 1, 1).equals(user.getBirthdate());
        assert user.getIsActive();
        assert user.getCreatedAt() != null;
        assert user.getUpdatedAt() != null;
    }

    private static void testSetName() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        user.setName("Jane Doe");

        assert "Jane Doe".equals(user.getName());
    }

    private static void testSetEmail() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        user.setEmail("jane.doe@example.com");

        assert "jane.doe@example.com".equals(user.getEmail());
    }

    private static void testSetPassword() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        user.setPassword("newpassword");

        assert "newpassword".equals(user.getPassword());
    }

    private static void testSetBirthdate() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        LocalDate newBirthdate = LocalDate.of(1992, 2, 2);
        user.setBirthdate(newBirthdate);

        assert newBirthdate.equals(user.getBirthdate());
    }

    private static void testSetIsActive() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        user.setIsActive(false);

        assert !user.getIsActive();
    }

    private static void testDeactivate() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        user.deactivate();

        assert !user.getIsActive();
    }

    private static void testCheckPassword() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        assert user.checkPassword("password");
        assert !user.checkPassword("wrongpassword");
    }
}

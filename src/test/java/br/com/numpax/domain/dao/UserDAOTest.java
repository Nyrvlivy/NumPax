//package br.com.numpax.domain.dao;
//
//import br.com.numpax.infrastructure.entities.User;
//import br.com.numpax.exceptions.UniqueConstraintViolationException;
//import org.junit.jupiter.api.*;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserDAOTest {
//
//    private UserDAO userDAO;
//
//    @BeforeEach
//    void setUp() {
//        userDAO = new UserDAO();
//    }
//
//    @AfterEach
//    void tearDown() {
//        userDAO.deleteByEmail("user_test@example.com");
//        userDAO.deleteByEmail("user_test_updated@example.com");
//        userDAO.deleteByEmail("duplicate@example.com");
//    }
//
//    @Test
//    void testSaveAndFindById() {
//        User user = new User("User Test", "user_test@example.com", "password123", LocalDate.of(1990, 1, 1));
//        try {
//            userDAO.save(user);
//        } catch (UniqueConstraintViolationException e) {
//            fail("Deve salvar o usuário sem exceção.");
//        }
//
//        Optional<User> retrievedUser = userDAO.findById(user.getId());
//        assertTrue(retrievedUser.isPresent(), "Usuário deve ser encontrado.");
//        assertEquals(user.getEmail(), retrievedUser.get().getEmail(), "Emails devem ser iguais.");
//    }
//
//    @Test
//    void testUpdateUser() {
//        User user = new User("User Test", "user_test@example.com", "password123", LocalDate.of(1990, 1, 1));
//        try {
//            userDAO.save(user);
//        } catch (UniqueConstraintViolationException e) {
//            fail("Deve salvar o usuário sem exceção.");
//        }
//
//        user.setEmail("user_test_updated@example.com");
//        try {
//            userDAO.save(user);
//        } catch (UniqueConstraintViolationException e) {
//            fail("Deve atualizar o usuário sem exceção.");
//        }
//
//        Optional<User> retrievedUser = userDAO.findById(user.getId());
//        assertTrue(retrievedUser.isPresent(), "Usuário deve ser encontrado.");
//        assertEquals("user_test_updated@example.com", retrievedUser.get().getEmail(), "Email deve ser atualizado.");
//    }
//
//    @Test
//    void testDeleteById() {
//        User user = new User("User Test", "user_test@example.com", "password123", LocalDate.of(1990, 1, 1));
//        try {
//            userDAO.save(user);
//        } catch (UniqueConstraintViolationException e) {
//            fail("Deve salvar o usuário sem exceção.");
//        }
//
//        userDAO.deleteById(user.getId());
//
//        Optional<User> retrievedUser = userDAO.findById(user.getId());
//        assertFalse(retrievedUser.isPresent(), "Usuário não deve ser encontrado após exclusão.");
//    }
//
//    @Test
//    void testExistsById() {
//        User user = new User("User Test", "user_test@example.com", "password123", LocalDate.of(1990, 1, 1));
//        try {
//            userDAO.save(user);
//        } catch (UniqueConstraintViolationException e) {
//            fail("Deve salvar o usuário sem exceção.");
//        }
//
//        assertTrue(userDAO.existsById(user.getId()), "Usuário deve existir.");
//
//        userDAO.deleteById(user.getId());
//
//        assertFalse(userDAO.existsById(user.getId()), "Usuário não deve existir após exclusão.");
//    }
//
//    @Test
//    void testFindByEmail() {
//        User user = new User("User Test", "user_test@example.com", "password123", LocalDate.of(1990, 1, 1));
//        try {
//            userDAO.save(user);
//        } catch (UniqueConstraintViolationException e) {
//            fail("Deve salvar o usuário sem exceção.");
//        }
//
//        Optional<User> retrievedUser = userDAO.findByEmail("user_test@example.com");
//        assertTrue(retrievedUser.isPresent(), "Usuário deve ser encontrado pelo email.");
//        assertEquals(user.getId(), retrievedUser.get().getId(), "IDs devem ser iguais.");
//    }
//
//    @Test
//    void testUniqueConstraintViolation() {
//        User user1 = new User("User Duplicate", "duplicate@example.com", "password123", LocalDate.of(1990, 1, 1));
//        User user2 = new User("User Duplicate", "duplicate@example.com", "password123", LocalDate.of(1990, 1, 1));
//
//        try {
//            userDAO.save(user1);
//        } catch (UniqueConstraintViolationException e) {
//            fail("User1 deve ser salvo sem exceção.");
//        }
//
//        assertThrows(UniqueConstraintViolationException.class, () -> userDAO.save(user2), "Deve lançar exceção de violação de restrição única.");
//    }
//
//
//}

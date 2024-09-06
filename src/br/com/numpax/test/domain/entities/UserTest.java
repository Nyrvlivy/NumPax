package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.User;

import java.time.LocalDate;

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
        User user = new User("Sarah Regina Santos", "sarah.regina.santos@ci.com.br", "72qLyjOJHf", LocalDate.of(2000, 9, 1));

        assert user.getId() != null;
        assert "Sarah Regina Santos".equals(user.getName());
        assert "sarah.regina.santos@ci.com.br".equals(user.getEmail());
        assert "72qLyjOJHf".equals(user.getPassword());
        assert LocalDate.of(2000, 9, 1).equals(user.getBirthdate());
        assert user.getIsActive();
        assert user.getCreatedAt() != null;
        assert user.getUpdatedAt() != null;
    }

    private static void testSetName() {
        User user = new User("Daiane Mirella Nunes", "daiane_nunes@iname.com", "yFzFMeWNCg", LocalDate.of(2000, 5, 17));
        user.setName("New Name");

        assert "New Name".equals(user.getName());
    }

    private static void testSetEmail() {
        User user = new User("Olivia Melissa Sandra Aparício", "olivia-aparicio85@adherminer.com.br", "5toCgr0uRF", LocalDate.of(2000, 9, 3));
        user.setEmail("new.email@example.com");

        assert "new.email@example.com".equals(user.getEmail());
    }

    private static void testSetPassword() {
        User user = new User("Priscila Priscila Rita da Conceição", "priscila-daconceicao90@eguia.com.br", "xlkx4iiYwC", LocalDate.of(2000, 3, 6));
        user.setPassword("newpassword");

        assert "newpassword".equals(user.getPassword());
    }

    private static void testSetBirthdate() {
        User user = new User("Márcia Kamilly Pietra Lima", "marcia.kamilly.lima@solviagens.com", "qdDsgPDZtO", LocalDate.of(2000, 2, 2));
        LocalDate newBirthdate = LocalDate.of(1992, 2, 2);
        user.setBirthdate(newBirthdate);

        assert newBirthdate.equals(user.getBirthdate());
    }

    private static void testSetIsActive() {
        User user = new User("Maitê Louise Sabrina da Rosa", "maite-darosa73@l3ambiental.com.br", "ggUBtebm0e", LocalDate.of(2000, 4, 6));
        user.setIsActive(false);

        assert !user.getIsActive();
    }

    private static void testDeactivate() {
        User user = new User("Giovana Tatiane Fabiana Corte Real", "giovanatatianecortereal@poli.ufrj.br", "tnBptH1u2B", LocalDate.of(2000, 5, 15));
        user.deactivate();

        assert !user.getIsActive();
    }

    private static void testCheckPassword() {
        User user = new User("Larissa Emilly Caldeira", "larissa.emilly.caldeira@selaz.com.br", "gZXDA6pyPT", LocalDate.of(2000, 9, 6));
        assert user.checkPassword("gZXDA6pyPT");
        assert !user.checkPassword("wrongpassword");
    }
}

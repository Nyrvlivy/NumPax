package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RegularAccountTest {
    public static void main(String[] args) {
        testRegularAccountCreation();
        testSetType();
        testWithdraw();
        testWithdrawInsufficientFunds();
        testDeposit();
        testDepositNegativeAmount();
        testApplyDeposit();
        testApplyWithdraw();
    }

    private static void testRegularAccountCreation() {
        User user = new User("Sarah Regina Santos", "sarah.regina.santos@ci.com.br", "72qLyjOJHf", LocalDate.of(2000, 9, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);

        assert AccountType.CHECKING.equals(regularAccount.getType());
    }

    private static void testSetType() {
        User user = new User("Daiane Mirella Nunes", "daiane_nunes@iname.com", "yFzFMeWNCg", LocalDate.of(2000, 5, 17));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.setType(AccountType.SAVINGS);

        assert AccountType.SAVINGS.equals(regularAccount.getType());
    }

    private static void testWithdraw() {
        User user = new User("Olivia Melissa Sandra Aparício", "olivia-aparicio85@adherminer.com.br", "5toCgr0uRF", LocalDate.of(2000, 9, 3));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.setBalance(BigDecimal.valueOf(1000.0));
        regularAccount.withdraw(500.0);

        assert BigDecimal.valueOf(500.0).equals(regularAccount.getBalance());
    }

    private static void testWithdrawInsufficientFunds() {
        User user = new User("Priscila Priscila Rita da Conceição", "priscila-daconceicao90@eguia.com.br", "xlkx4iiYwC", LocalDate.of(2000, 3, 6));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.setBalance(BigDecimal.valueOf(1000.0));
        regularAccount.withdraw(1500.0);

        assert BigDecimal.valueOf(1000.0).equals(regularAccount.getBalance());
    }

    private static void testDeposit() {
        User user = new User("Márcia Kamilly Pietra Lima", "marcia.kamilly.lima@solviagens.com", "qdDsgPDZtO", LocalDate.of(2000, 2, 2));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.deposit(500.0);

        assert BigDecimal.valueOf(500.0).equals(regularAccount.getBalance());
    }

    private static void testDepositNegativeAmount() {
        User user = new User("Maitê Louise Sabrina da Rosa", "maite-darosa73@l3ambiental.com.br", "ggUBtebm0e", LocalDate.of(2000, 4, 6));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);

        try {
            regularAccount.deposit(-500.0);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    private static void testApplyDeposit() {
        User user = new User("Giovana Tatiane Fabiana Corte Real", "giovanatatianecortereal@poli.ufrj.br", "tnBptH1u2B", LocalDate.of(2000, 5, 15));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.apply(500.0);

        assert BigDecimal.valueOf(500.0).equals(regularAccount.getBalance());
    }

    private static void testApplyWithdraw() {
        User user = new User("Larissa Emilly Caldeira", "larissa.emilly.caldeira@selaz.com.br", "gZXDA6pyPT", LocalDate.of(2000, 9, 6));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.setBalance(BigDecimal.valueOf(1000.0));
        regularAccount.apply(-500.0);

        assert BigDecimal.valueOf(500.0).equals(regularAccount.getBalance());
    }
}

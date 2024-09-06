package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.CheckingAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;

import java.time.LocalDate;

public class CheckingAccountTest {
    public static void main(String[] args) {
        testCheckingAccountCreation();
        testSetBankName();
        testSetAgency();
        testSetAccountNumber();
    }

    private static void testCheckingAccountCreation() {
        User user = new User("Sarah Regina Santos", "sarah.regina.santos@ci.com.br", "72qLyjOJHf", LocalDate.of(2000, 9, 1));
        CheckingAccount checkingAccount = new CheckingAccount("Main Account", "Primary account", user, AccountType.CHECKING, "Banco do Brasil", "1234", "56789");

        assert "Banco do Brasil".equals(checkingAccount.getBankName());
        assert "1234".equals(checkingAccount.getAgency());
        assert "56789".equals(checkingAccount.getAccountNumber());
    }

    private static void testSetBankName() {
        User user = new User("Daiane Mirella Nunes", "daiane_nunes@iname.com", "yFzFMeWNCg", LocalDate.of(2000, 5, 17));
        CheckingAccount checkingAccount = new CheckingAccount("Main Account", "Primary account", user, AccountType.CHECKING, "Banco do Brasil", "1234", "56789");
        checkingAccount.setBankName("Itaú");

        assert "Itaú".equals(checkingAccount.getBankName());
    }

    private static void testSetAgency() {
        User user = new User("Olivia Melissa Sandra Aparício", "olivia-aparicio85@adherminer.com.br", "5toCgr0uRF", LocalDate.of(2000, 9, 3));
        CheckingAccount checkingAccount = new CheckingAccount("Main Account", "Primary account", user, AccountType.CHECKING, "Banco do Brasil", "1234", "56789");
        checkingAccount.setAgency("5678");

        assert "5678".equals(checkingAccount.getAgency());
    }

    private static void testSetAccountNumber() {
        User user = new User("Priscila Priscila Rita da Conceição", "priscila-daconceicao90@eguia.com.br", "xlkx4iiYwC", LocalDate.of(2000, 3, 6));
        CheckingAccount checkingAccount = new CheckingAccount("Main Account", "Primary account", user, AccountType.CHECKING, "Banco do Brasil", "1234", "56789");
        checkingAccount.setAccountNumber("98765");

        assert "98765".equals(checkingAccount.getAccountNumber());
    }
}

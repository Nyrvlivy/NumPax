package br.com.numpax.test.domain.entities;

import java.time.LocalDate;

import br.com.numpax.domain.entities.CheckingAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;

public class CheckingAccountTest {
    public static void main(String[] args) {
        testCheckingAccountCreation();
        testSetBankName();
        testSetAgency();
        testSetAccountNumber();
    }

    private static void testCheckingAccountCreation() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        CheckingAccount checkingAccount = new CheckingAccount("Main Account", "Primary account", user, AccountType.CHECKING, "Bank XYZ", "1234", "56789");

        assert "Bank XYZ".equals(checkingAccount.getBankName());
        assert "1234".equals(checkingAccount.getAgency());
        assert "56789".equals(checkingAccount.getAccountNumber());
    }

    private static void testSetBankName() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        CheckingAccount checkingAccount = new CheckingAccount("Main Account", "Primary account", user, AccountType.CHECKING, "Bank XYZ", "1234", "56789");
        checkingAccount.setBankName("New Bank");

        assert "New Bank".equals(checkingAccount.getBankName());
    }

    private static void testSetAgency() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        CheckingAccount checkingAccount = new CheckingAccount("Main Account", "Primary account", user, AccountType.CHECKING, "Bank XYZ", "1234", "56789");
        checkingAccount.setAgency("5678");

        assert "5678".equals(checkingAccount.getAgency());
    }

    private static void testSetAccountNumber() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        CheckingAccount checkingAccount = new CheckingAccount("Main Account", "Primary account", user, AccountType.CHECKING, "Bank XYZ", "1234", "56789");
        checkingAccount.setAccountNumber("98765");

        assert "98765".equals(checkingAccount.getAccountNumber());
    }
}

package br.com.numpax.test.domain.entities;


import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;

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
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);

        assert AccountType.CHECKING.equals(regularAccount.getType());
    }

    private static void testSetType() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.setType(AccountType.SAVINGS);

        assert AccountType.SAVINGS.equals(regularAccount.getType());
    }

    private static void testWithdraw() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.setBalance(BigDecimal.valueOf(1000.0));
        regularAccount.withdraw(500.0);

        assert BigDecimal.valueOf(500.0).equals(regularAccount.getBalance());
    }

    private static void testWithdrawInsufficientFunds() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.setBalance(BigDecimal.valueOf(1000.0));
        regularAccount.withdraw(1500.0);

        assert BigDecimal.valueOf(1000.0).equals(regularAccount.getBalance());
    }
    private static void testDeposit() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.deposit(500.0);

        assert BigDecimal.valueOf(500.0).equals(regularAccount.getBalance());
    }

    private static void testDepositNegativeAmount() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);

        try {
            regularAccount.deposit(-500.0);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    private static void testApplyDeposit() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.apply(500.0);

        assert BigDecimal.valueOf(500.0).equals(regularAccount.getBalance());
    }

    private static void testApplyWithdraw() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        regularAccount.setBalance(BigDecimal.valueOf(1000.0));
        regularAccount.apply(-500.0);

        assert BigDecimal.valueOf(500.0).equals(regularAccount.getBalance());
    }
}
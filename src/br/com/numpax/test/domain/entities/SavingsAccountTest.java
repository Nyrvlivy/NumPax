package br.com.numpax.test.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.numpax.domain.entities.SavingsAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;

public class SavingsAccountTest {
    public static void main(String[] args) {
        testSavingsAccountCreation();
    }

    private static void testSavingsAccountCreation() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        SavingsAccount savingsAccount = new SavingsAccount("Savings Account", "Account for savings", user, AccountType.SAVINGS, LocalDateTime.now(), LocalDateTime.now().plusYears(1), LocalDateTime.now().plusMonths(6), 0.05, 10.0, 10000.0, 5000.0);

        assert savingsAccount.getNearestDeadline() != null;
        assert savingsAccount.getFurthestDeadline() != null;
        assert savingsAccount.getLastestDeadline() != null;
        assert 0.05 == savingsAccount.getAverageTaxRate();
        assert 10.0 == savingsAccount.getNumberOfFixedInvestments();
        assert 10000.0 == savingsAccount.getTotalMaturityAmount();
        assert 5000.0 == savingsAccount.getTotalDepositAmount();
    }
}

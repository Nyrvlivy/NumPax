package br.com.numpax.test.domain.entities;

import java.time.LocalDate;

import br.com.numpax.domain.entities.InvestmentAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.RiskLevelType;

public class InvestmentAccountTest {
    public static void main(String[] args) {
        testInvestmentAccountCreation();
        testSetRiskLevelType();
        testSetInvestmentSubtypeAccount();
        testViewInvestmentStatement();
    }

    private static void testInvestmentAccountCreation() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        InvestmentAccount investmentAccount = new InvestmentAccount("Investment Account", "Account for investments", user, AccountType.INVESTMENT, 10000.0, 2000.0, 12000.0, 1000.0, 5.0, 10.0, 15.0, 50.0, 2000.0, 0.05, RiskLevelType.HIGH, AccountType.InvestmentSubtypeAccount.STOCKS);

        assert 10000.0 == investmentAccount.getTotalInvestedAmount();
        assert 2000.0 == investmentAccount.getTotalProfit();
        assert 12000.0 == investmentAccount.getTotalCurrentAmount();
        assert 1000.0 == investmentAccount.getTotalWithdrawnAmount();
        assert 5.0 == investmentAccount.getNumberOfWithdrawals();
        assert 10.0 == investmentAccount.getNumberOfEntries();
        assert 15.0 == investmentAccount.getNumberOfAssets();
        assert 50.0 == investmentAccount.getAveragePurchasePrice();
        assert 2000.0 == investmentAccount.getTotalGainLoss();
        assert 0.05 == investmentAccount.getTotalDividendYield();
        assert RiskLevelType.HIGH.equals(investmentAccount.getRiskLevelType());
        assert AccountType.InvestmentSubtypeAccount.STOCKS.equals(investmentAccount.getInvestmentSubtypeAccount());
    }

    private static void testSetRiskLevelType() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        InvestmentAccount investmentAccount = new InvestmentAccount("Investment Account", "Account for investments", user, AccountType.INVESTMENT, 10000.0, 2000.0, 12000.0, 1000.0, 5.0, 10.0, 15.0, 50.0, 2000.0, 0.05, RiskLevelType.HIGH, AccountType.InvestmentSubtypeAccount.STOCKS);
        investmentAccount.setRiskLevelType(RiskLevelType.LOW);

        assert RiskLevelType.LOW.equals(investmentAccount.getRiskLevelType());
    }

    private static void testSetInvestmentSubtypeAccount() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        InvestmentAccount investmentAccount = new InvestmentAccount("Investment Account", "Account for investments", user, AccountType.INVESTMENT, 10000.0, 2000.0, 12000.0, 1000.0, 5.0, 10.0, 15.0, 50.0, 2000.0, 0.05, RiskLevelType.HIGH, AccountType.InvestmentSubtypeAccount.STOCKS);
        investmentAccount.setInvestmentSubtypeAccount(AccountType.InvestmentSubtypeAccount.BONDS);

        assert AccountType.InvestmentSubtypeAccount.BONDS.equals(investmentAccount.getInvestmentSubtypeAccount());
    }

    private static void testViewInvestmentStatement() {
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        InvestmentAccount investmentAccount = new InvestmentAccount("Investment Account", "Account for investments", user, AccountType.INVESTMENT, 10000.0, 2000.0, 12000.0, 1000.0, 5.0, 10.0, 15.0, 50.0, 2000.0, 0.05, RiskLevelType.HIGH, AccountType.InvestmentSubtypeAccount.STOCKS);
        investmentAccount.viewInvestmentStatement();
    }
}

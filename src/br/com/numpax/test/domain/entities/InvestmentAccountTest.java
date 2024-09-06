package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.InvestmentAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.RiskLevelType;

import java.time.LocalDate;

public class InvestmentAccountTest {
    public static void main(String[] args) {
        testInvestmentAccountCreation();
        testSetRiskLevelType();
        testSetInvestmentSubtypeAccount();
        testViewInvestmentStatement();
    }

    private static void testInvestmentAccountCreation() {
        User user = new User("Sarah Regina Santos", "sarah.regina.santos@ci.com.br", "72qLyjOJHf", LocalDate.of(2000, 9, 1));
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
        User user = new User("Daiane Mirella Nunes", "daiane_nunes@iname.com", "yFzFMeWNCg", LocalDate.of(2000, 5, 17));
        InvestmentAccount investmentAccount = new InvestmentAccount("Investment Account", "Account for investments", user, AccountType.INVESTMENT, 10000.0, 2000.0, 12000.0, 1000.0, 5.0, 10.0, 15.0, 50.0, 2000.0, 0.05, RiskLevelType.HIGH, AccountType.InvestmentSubtypeAccount.STOCKS);
        investmentAccount.setRiskLevelType(RiskLevelType.LOW);

        assert RiskLevelType.LOW.equals(investmentAccount.getRiskLevelType());
    }

    private static void testSetInvestmentSubtypeAccount() {
        User user = new User("Olivia Melissa Sandra Aparício", "olivia-aparicio85@adherminer.com.br", "5toCgr0uRF", LocalDate.of(2000, 9, 3));
        InvestmentAccount investmentAccount = new InvestmentAccount("Investment Account", "Account for investments", user, AccountType.INVESTMENT, 10000.0, 2000.0, 12000.0, 1000.0, 5.0, 10.0, 15.0, 50.0, 2000.0, 0.05, RiskLevelType.HIGH, AccountType.InvestmentSubtypeAccount.STOCKS);
        investmentAccount.setInvestmentSubtypeAccount(AccountType.InvestmentSubtypeAccount.BONDS);

        assert AccountType.InvestmentSubtypeAccount.BONDS.equals(investmentAccount.getInvestmentSubtypeAccount());
    }

    private static void testViewInvestmentStatement() {
        User user = new User("Priscila Priscila Rita da Conceição", "priscila-daconceicao90@eguia.com.br", "xlkx4iiYwC", LocalDate.of(2000, 3, 6));
        InvestmentAccount investmentAccount = new InvestmentAccount("Investment Account", "Account for investments", user, AccountType.INVESTMENT, 10000.0, 2000.0, 12000.0, 1000.0, 5.0, 10.0, 15.0, 50.0, 2000.0, 0.05, RiskLevelType.HIGH, AccountType.InvestmentSubtypeAccount.STOCKS);
        investmentAccount.viewInvestmentStatement();
    }
}

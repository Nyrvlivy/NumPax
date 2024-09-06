package br.com.numpax.test.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.FixedInvestment;
import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.CategoryType;
import br.com.numpax.domain.enums.FixedInvestmentType;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

public class FixedInvestmentTest {
    public static void main(String[] args) {
        testFixedInvestmentCreation();
        testSetFixedInvestmentType();
        testSetInvestmentDate();
        testSetExpirationDate();
        testSetInstitution();
        testSetTaxRates();
        testSetRedeemValue();
        testSetRedeemDate();
        testSetLiquidityPeriod();
        testSetNetGainLoss();
    }

    private static void testFixedInvestmentCreation() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);

        assert FixedInvestmentType.CDB.equals(fixedInvestment.getFixedInvestmentType());
        assert LocalDate.now().equals(fixedInvestment.getInvestmentDate());
        assert LocalDate.now().plusYears(1).equals(fixedInvestment.getExpirationDate());
        assert "Bank XYZ".equals(fixedInvestment.getInstitution());
        assert fixedInvestment.getTaxRates().length == 2;
        assert 1100.0 == fixedInvestment.getRedeemValue();
        assert LocalDate.now().plusYears(1).equals(fixedInvestment.getRedeemDate());
        assert 30 == fixedInvestment.getLiquidityPeriod();
        assert 100.0 == fixedInvestment.getNetGainLoss();
    }

    private static void testSetFixedInvestmentType() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        fixedInvestment.setFixedInvestmentType(FixedInvestmentType.LCI);

        assert FixedInvestmentType.LCI.equals(fixedInvestment.getFixedInvestmentType());
    }

    private static void testSetInvestmentDate() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        LocalDate newDate = LocalDate.now().plusDays(1);
        fixedInvestment.setInvestmentDate(newDate);

        assert newDate.equals(fixedInvestment.getInvestmentDate());
    }

    private static void testSetExpirationDate() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        LocalDate newDate = LocalDate.now().plusYears(2);
        fixedInvestment.setExpirationDate(newDate);

        assert newDate.equals(fixedInvestment.getExpirationDate());
    }

    private static void testSetInstitution() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        fixedInvestment.setInstitution("New Bank");

        assert "New Bank".equals(fixedInvestment.getInstitution());
    }

    private static void testSetTaxRates() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        Double[] newRates = {0.3, 0.4};
        fixedInvestment.setTaxRates(newRates);

        assert newRates.length == fixedInvestment.getTaxRates().length;
    }

    private static void testSetRedeemValue() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        fixedInvestment.setRedeemValue(1200.0);

        assert 1200.0 == fixedInvestment.getRedeemValue();
    }

    private static void testSetRedeemDate() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        LocalDate newDate = LocalDate.now().plusYears(2);
        fixedInvestment.setRedeemDate(newDate);

        assert newDate.equals(fixedInvestment.getRedeemDate());
    }

    private static void testSetLiquidityPeriod() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        fixedInvestment.setLiquidityPeriod(40);

        assert 40 == fixedInvestment.getLiquidityPeriod();
    }

    private static void testSetNetGainLoss() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        FixedInvestment fixedInvestment = new FixedInvestment("INV001", "Fixed Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", FixedInvestmentType.CDB, LocalDate.now(), LocalDate.now().plusYears(1), "Bank XYZ", new Double[]{0.1, 0.2}, 1100.0, LocalDate.now().plusYears(1), 30, 100.0);
        fixedInvestment.setNetGainLoss(200.0);

        assert 200.0 == fixedInvestment.getNetGainLoss();
    }
}
package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.entities.VariableInvestment;
import br.com.numpax.domain.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VariableInvestmentTest {
    public static void main(String[] args) {
        testVariableInvestmentCreation();
        testSetVariableInvestmentType();
        testSetBroker();
        testSetPurchaseDate();
        testSetExpirationDate();
        testSetAssetCode();
        testSetQuantity();
        testSetUnitPrice();
        testSetSaleDate();
        testSetSalePrice();
        testSetBrokerFees();
        testSetOtherFees();
    }

    private static VariableInvestment createDefaultVariableInvestment() {
        Category category = new Category("Investment", "Investment category", "icon.png", CategoryType.INCOME, true);
        User user = new User("Márcia Kamilly Pietra Lima", "marcia.kamilly.lima@solviagens.com", "qdDsgPDZtO", LocalDate.of(2000, 2, 2));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.SAVINGS);
        return new VariableInvestment("INV001", "Variable Investment", "Description", BigDecimal.valueOf(1000), category, regularAccount, NatureOfTransaction.INCOME, "Receiver", "Sender", LocalDate.now(), false, RepeatableType.NONE, "Note", VariableInvestmentType.STOCK, LocalDate.now().plusYears(1), "Broker XYZ", LocalDate.now(), "ASSET001", BigDecimal.valueOf(10), BigDecimal.valueOf(100), LocalDate.now().plusYears(1), BigDecimal.valueOf(150), new BigDecimal[]{BigDecimal.valueOf(1), BigDecimal.valueOf(2)}, new BigDecimal[]{BigDecimal.valueOf(3), BigDecimal.valueOf(4)});
    }

    private static void testVariableInvestmentCreation() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();

        assert VariableInvestmentType.STOCK.equals(variableInvestment.getVariableInvestmentType());
        assert "Broker XYZ".equals(variableInvestment.getBroker());
        assert LocalDate.now().equals(variableInvestment.getPurchaseDate());
        assert LocalDate.now().plusYears(1).equals(variableInvestment.getExpirationDate());
        assert "ASSET001".equals(variableInvestment.getAssetCode());
        assert BigDecimal.valueOf(10).equals(variableInvestment.getQuantity());
        assert BigDecimal.valueOf(100).equals(variableInvestment.getUnitPrice());
        assert LocalDate.now().plusYears(1).equals(variableInvestment.getSaleDate());
        assert BigDecimal.valueOf(150).equals(variableInvestment.getSalePrice());
        assert variableInvestment.getBrokerFees().length == 2;
        assert variableInvestment.getOtherFees().length == 2;
    }

    private static void testSetVariableInvestmentType() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        variableInvestment.setVariableInvestmentType(VariableInvestmentType.BONDS);

        assert VariableInvestmentType.BONDS.equals(variableInvestment.getVariableInvestmentType());
    }

    private static void testSetBroker() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        variableInvestment.setBroker("New Broker");

        assert "New Broker".equals(variableInvestment.getBroker());
    }

    private static void testSetPurchaseDate() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        LocalDate newDate = LocalDate.now().plusDays(1);
        variableInvestment.setPurchaseDate(newDate);

        assert newDate.equals(variableInvestment.getPurchaseDate());
    }

    private static void testSetExpirationDate() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        LocalDate newDate = LocalDate.now().plusYears(2);
        variableInvestment.setExpirationDate(newDate);

        assert newDate.equals(variableInvestment.getExpirationDate());
    }

    private static void testSetAssetCode() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        variableInvestment.setAssetCode("NEWASSET001");

        assert "NEWASSET001".equals(variableInvestment.getAssetCode());
    }

    private static void testSetQuantity() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        variableInvestment.setQuantity(BigDecimal.valueOf(20));

        assert BigDecimal.valueOf(20).equals(variableInvestment.getQuantity());
    }

    private static void testSetUnitPrice() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        variableInvestment.setUnitPrice(BigDecimal.valueOf(200));

        assert BigDecimal.valueOf(200).equals(variableInvestment.getUnitPrice());
    }

    private static void testSetSaleDate() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        LocalDate newDate = LocalDate.now().plusYears(2);
        variableInvestment.setSaleDate(newDate);

        assert newDate.equals(variableInvestment.getSaleDate());
    }

    private static void testSetSalePrice() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        variableInvestment.setSalePrice(BigDecimal.valueOf(300));

        assert BigDecimal.valueOf(300).equals(variableInvestment.getSalePrice());
    }

    private static void testSetBrokerFees() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        variableInvestment.setBrokerFees(new BigDecimal[]{BigDecimal.valueOf(5), BigDecimal.valueOf(6)});

        assert variableInvestment.getBrokerFees().length == 2;
        assert BigDecimal.valueOf(5).equals(variableInvestment.getBrokerFees()[0]);
        assert BigDecimal.valueOf(6).equals(variableInvestment.getBrokerFees()[1]);
    }

    private static void testSetOtherFees() {
        VariableInvestment variableInvestment = createDefaultVariableInvestment();
        variableInvestment.setOtherFees(new BigDecimal[]{BigDecimal.valueOf(7), BigDecimal.valueOf(8)});

        assert variableInvestment.getOtherFees().length == 2;
        assert BigDecimal.valueOf(7).equals(variableInvestment.getOtherFees()[0]);
        assert BigDecimal.valueOf(8).equals(variableInvestment.getOtherFees()[1]);
    }
}

package br.com.numpax.test.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.Transaction;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.CategoryType;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

public class TransactionTest {
    public static void main(String[] args) {
        testTransactionCreation();
        testSetName();
        testSetDescription();
        testSetAmount();
        testSetCategory();
        testSetRegularAccount();
        testSetNatureOfTransaction();
        testSetReceiver();
        testSetSender();
        testSetTransactionDate();
        testSetRepeatableType();
        testSetNote();
        testSetActive();
        testApply();
    }

    private static void testTransactionCreation() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");

        assert transaction.getId() != null;
        assert "TRX001".equals(transaction.getCode());
        assert "Transaction".equals(transaction.getName());
        assert "Description".equals(transaction.getDescription());
        assert BigDecimal.valueOf(100.0).equals(transaction.getAmount());
        assert category.equals(transaction.getCategory());
        assert regularAccount.equals(transaction.getRegularAccount());
        assert NatureOfTransaction.EXPENSE.equals(transaction.getNatureOfTransaction());
        assert "Receiver".equals(transaction.getReceiver());
        assert "Sender".equals(transaction.getSender());
        assert LocalDate.now().equals(transaction.getTransactionDate());
        assert RepeatableType.NONE.equals(transaction.getRepeatableType());
        assert "Note".equals(transaction.getNote());
        assert transaction.isActive();
        assert transaction.getCreatedAt() != null;
        assert transaction.getUpdatedAt() != null;
    }

    private static void testSetName() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setName("New Name");

        assert "New Name".equals(transaction.getName());
    }

    private static void testSetDescription() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setDescription("New Description");

        assert "New Description".equals(transaction.getDescription());
    }

    private static void testSetAmount() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setAmount(BigDecimal.valueOf(200.0));

        assert BigDecimal.valueOf(200.0).equals(transaction.getAmount());
    }

    private static void testSetCategory() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        Category newCategory = new Category("New Category", "New description", "new_icon.png", CategoryType.INCOME, false);
        transaction.setCategory(newCategory);

        assert newCategory.equals(transaction.getCategory());
    }

    private static void testSetRegularAccount() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        RegularAccount newRegularAccount = new RegularAccount("New Account", "New description", user, AccountType.SAVINGS);
        transaction.setRegularAccount(newRegularAccount);

        assert newRegularAccount.equals(transaction.getRegularAccount());
    }

    private static void testSetNatureOfTransaction() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setNatureOfTransaction(NatureOfTransaction.INCOME);

        assert NatureOfTransaction.INCOME.equals(transaction.getNatureOfTransaction());
    }

    private static void testSetReceiver() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setReceiver("New Receiver");

        assert "New Receiver".equals(transaction.getReceiver());
    }

    private static void testSetSender() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setSender("New Sender");

        assert "New Sender".equals(transaction.getSender());
    }

    private static void testSetTransactionDate() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        LocalDate newDate = LocalDate.now().plusDays(1);
        transaction.setTransactionDate(newDate);

        assert newDate.equals(transaction.getTransactionDate());
    }

    private static void testSetRepeatableType() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setRepeatableType(RepeatableType.MONTHLY);

        assert RepeatableType.MONTHLY.equals(transaction.getRepeatableType());
    }

    private static void testSetNote() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setNote("New Note");

        assert "New Note".equals(transaction.getNote());
    }

    private static void testSetActive() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setActive(false);

        assert !transaction.isActive();
    }

    private static void testApply() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("John Doe", "john.doe@example.com", "password", LocalDate.of(1990, 1, 1));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.apply();

        assert transaction.isEffective();
    }
}

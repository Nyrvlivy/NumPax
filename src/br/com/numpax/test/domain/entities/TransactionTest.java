package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.RegularAccount;
import br.com.numpax.domain.entities.Transaction;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.CategoryType;
import br.com.numpax.domain.enums.NatureOfTransaction;
import br.com.numpax.domain.enums.RepeatableType;

import java.math.BigDecimal;
import java.time.LocalDate;

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
        User user = new User("Sarah Regina Santos", "sarah.regina.santos@ci.com.br", "72qLyjOJHf", LocalDate.of(2000, 9, 1));
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
        User user = new User("Daiane Mirella Nunes", "daiane_nunes@iname.com", "yFzFMeWNCg", LocalDate.of(2000, 5, 17));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setName("New Name");

        assert "New Name".equals(transaction.getName());
    }

    private static void testSetDescription() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Olivia Melissa Sandra Aparício", "olivia-aparicio85@adherminer.com.br", "5toCgr0uRF", LocalDate.of(2000, 9, 3));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setDescription("New Description");

        assert "New Description".equals(transaction.getDescription());
    }

    private static void testSetAmount() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Priscila Priscila Rita da Conceição", "priscila-daconceicao90@eguia.com.br", "xlkx4iiYwC", LocalDate.of(2000, 3, 6));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setAmount(BigDecimal.valueOf(200.0));

        assert BigDecimal.valueOf(200.0).equals(transaction.getAmount());
    }

    private static void testSetCategory() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Márcia Kamilly Pietra Lima", "marcia.kamilly.lima@solviagens.com", "qdDsgPDZtO", LocalDate.of(2000, 2, 2));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        Category newCategory = new Category("New Category", "New description", "new_icon.png", CategoryType.INCOME, false);
        transaction.setCategory(newCategory);

        assert newCategory.equals(transaction.getCategory());
    }

    private static void testSetRegularAccount() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Maitê Louise Sabrina da Rosa", "maite-darosa73@l3ambiental.com.br", "ggUBtebm0e", LocalDate.of(2000, 4, 6));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        RegularAccount newRegularAccount = new RegularAccount("New Account", "New description", user, AccountType.SAVINGS);
        transaction.setRegularAccount(newRegularAccount);

        assert newRegularAccount.equals(transaction.getRegularAccount());
    }

    private static void testSetNatureOfTransaction() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Giovana Tatiane Fabiana Corte Real", "giovanatatianecortereal@poli.ufrj.br", "tnBptH1u2B", LocalDate.of(2000, 5, 15));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setNatureOfTransaction(NatureOfTransaction.INCOME);

        assert NatureOfTransaction.INCOME.equals(transaction.getNatureOfTransaction());
    }

    private static void testSetReceiver() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Larissa Emilly Caldeira", "larissa.emilly.caldeira@selaz.com.br", "gZXDA6pyPT", LocalDate.of(2000, 9, 6));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setReceiver("New Receiver");

        assert "New Receiver".equals(transaction.getReceiver());
    }

    private static void testSetSender() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Isadora Mirella Silveira", "isadoramirellasilveira@fictor.com.br", "rhE3ht33cH", LocalDate.of(2000, 3, 18));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setSender("New Sender");

        assert "New Sender".equals(transaction.getSender());
    }

    private static void testSetTransactionDate() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Jennifer Giovanna Stefany Almeida", "jennifer-almeida80@djapan.com.br", "fRE9GiCMAJ", LocalDate.of(2000, 9, 3));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        LocalDate newDate = LocalDate.now().plusDays(1);
        transaction.setTransactionDate(newDate);

        assert newDate.equals(transaction.getTransactionDate());
    }

    private static void testSetRepeatableType() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Jaqueline Allana Nogueira", "jaqueline_allana_nogueira@hospitalprovisao.org.br", "WKUH2ZwG3H", LocalDate.of(2000, 9, 3));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setRepeatableType(RepeatableType.MONTHLY);

        assert RepeatableType.MONTHLY.equals(transaction.getRepeatableType());
    }

    private static void testSetNote() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Melissa Tereza Louise da Paz", "melissa_dapaz@recoferindustria.com.br", "LywWEC3rQR", LocalDate.of(2000, 1, 13));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setNote("New Note");

        assert "New Note".equals(transaction.getNote());
    }

    private static void testSetActive() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Laura Laís Martins", "laura_martins@gmx.com.br", "yGl2N8Cw8L", LocalDate.of(2000, 7, 7));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.setActive(false);

        assert !transaction.isActive();
    }

    private static void testApply() {
        Category category = new Category("Expense", "Expense category", "icon.png", CategoryType.EXPENSE, true);
        User user = new User("Carla Mariana Gomes", "carla_gomes@signa.net.br", "TjTIAdhS5c", LocalDate.of(2000, 4, 14));
        RegularAccount regularAccount = new RegularAccount("Main Account", "Primary account", user, AccountType.CHECKING);
        Transaction transaction = new Transaction("TRX001", "Transaction", "Description", BigDecimal.valueOf(100.0), category, regularAccount, NatureOfTransaction.EXPENSE, "Receiver", "Sender", LocalDate.now(), RepeatableType.NONE, "Note");
        transaction.apply();

        assert transaction.isEffective();
    }
}

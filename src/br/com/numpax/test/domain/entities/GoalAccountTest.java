package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.GoalAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;
import br.com.numpax.domain.enums.CategoryType;

import java.time.LocalDate;

public class GoalAccountTest {
    public static void main(String[] args) {
        testGoalAccountCreation();
        testSetTargetValue();
        testSetAmountValue();
        testSetTargetTaxRate();
        testSetMonthlyTaxRate();
        testSetMonthlyEstimate();
        testSetMonthlyAchievement();
        testSetCategory();
        testSetTargetDate();
        testSetStartDate();
        testSetEndDate();
        testViewGoalProgress();
    }

    private static Category createCategory() {
        return new Category("Savings", "Savings category", "icon.png", CategoryType.SAVINGS, true);
    }

    private static void testGoalAccountCreation() {
        User user = new User("Sarah Regina Santos", "sarah.regina.santos@ci.com.br", "72qLyjOJHf", LocalDate.of(2000, 9, 1));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));

        assert 10000.0 == goalAccount.getTargetValue();
        assert 0.0 == goalAccount.getAmountValue();
        assert 0.05 == goalAccount.getTargetTaxRate();
        assert 0.01 == goalAccount.getMonthlyTaxRate();
        assert 500.0 == goalAccount.getMonthlyEstimate();
        assert 100.0 == goalAccount.getMonthlyAchievement();
        assert category.equals(goalAccount.getCategory());
        assert LocalDate.now().plusYears(1).equals(goalAccount.getTargetDate());
        assert LocalDate.now().equals(goalAccount.getStartDate());
        assert LocalDate.now().plusYears(1).equals(goalAccount.getEndDate());
    }

    private static void testSetTargetValue() {
        User user = new User("Daiane Mirella Nunes", "daiane_nunes@iname.com", "yFzFMeWNCg", LocalDate.of(2000, 5, 17));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        goalAccount.setTargetValue(20000.0);

        assert 20000.0 == goalAccount.getTargetValue();
    }

    private static void testSetAmountValue() {
        User user = new User("Olivia Melissa Sandra Aparício", "olivia-aparicio85@adherminer.com.br", "5toCgr0uRF", LocalDate.of(2000, 9, 3));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        goalAccount.setAmountValue(5000.0);

        assert 5000.0 == goalAccount.getAmountValue();
    }

    private static void testSetTargetTaxRate() {
        User user = new User("Priscila Priscila Rita da Conceição", "priscila-daconceicao90@eguia.com.br", "xlkx4iiYwC", LocalDate.of(2000, 3, 6));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        goalAccount.setTargetTaxRate(0.1);

        assert 0.1 == goalAccount.getTargetTaxRate();
    }

    private static void testSetMonthlyTaxRate() {
        User user = new User("Márcia Kamilly Pietra Lima", "marcia.kamilly.lima@solviagens.com", "qdDsgPDZtO", LocalDate.of(2000, 2, 2));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        goalAccount.setMonthlyTaxRate(0.02);

        assert 0.02 == goalAccount.getMonthlyTaxRate();
    }

    private static void testSetMonthlyEstimate() {
        User user = new User("Maitê Louise Sabrina da Rosa", "maite-darosa73@l3ambiental.com.br", "ggUBtebm0e", LocalDate.of(2000, 4, 6));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        goalAccount.setMonthlyEstimate(1000.0);

        assert 1000.0 == goalAccount.getMonthlyEstimate();
    }

    private static void testSetMonthlyAchievement() {
        User user = new User("Giovana Tatiane Fabiana Corte Real", "giovanatatianecortereal@poli.ufrj.br", "tnBptH1u2B", LocalDate.of(2000, 5, 15));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        goalAccount.setMonthlyAchievement(200.0);

        assert 200.0 == goalAccount.getMonthlyAchievement();
    }

    private static void testSetCategory() {
        User user = new User("Larissa Emilly Caldeira", "larissa.emilly.caldeira@selaz.com.br", "gZXDA6pyPT", LocalDate.of(2000, 9, 6));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        Category newCategory = new Category("New Category", "New description", "new_icon.png", CategoryType.INCOME, false);
        goalAccount.setCategory(newCategory);

        assert newCategory.equals(goalAccount.getCategory());
    }

    private static void testSetTargetDate() {
        User user = new User("Isadora Mirella Silveira", "isadoramirellasilveira@fictor.com.br", "rhE3ht33cH", LocalDate.of(2000, 3, 18));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        LocalDate newDate = LocalDate.now().plusYears(2);
        goalAccount.setTargetDate(newDate);

        assert newDate.equals(goalAccount.getTargetDate());
    }

    private static void testSetStartDate() {
        User user = new User("Jennifer Giovanna Stefany Almeida", "jennifer-almeida80@djapan.com.br", "fRE9GiCMAJ", LocalDate.of(2000, 9, 3));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        LocalDate newDate = LocalDate.now().plusDays(1);
        goalAccount.setStartDate(newDate);

        assert newDate.equals(goalAccount.getStartDate());
    }

    private static void testSetEndDate() {
        User user = new User("Jaqueline Allana Nogueira", "jaqueline_allana_nogueira@hospitalprovisao.org.br", "WKUH2ZwG3H", LocalDate.of(2000, 9, 3));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        LocalDate newDate = LocalDate.now().plusYears(2);
        goalAccount.setEndDate(newDate);

        assert newDate.equals(goalAccount.getEndDate());
    }

    private static void testViewGoalProgress() {
        User user = new User("Melissa Tereza Louise da Paz", "melissa_dapaz@recoferindustria.com.br", "LywWEC3rQR", LocalDate.of(2000, 1, 13));
        Category category = createCategory();
        GoalAccount goalAccount = new GoalAccount("Goal Account", "Account for goals", user, AccountType.GOAL, 10000.0, 0.05, 0.01, 500.0, 100.0, category, LocalDate.now().plusYears(1), LocalDate.now(), LocalDate.now().plusYears(1));
        goalAccount.setAmountValue(5000.0);
        goalAccount.setTargetValue(10000.0);
        goalAccount.viewGoalProgress();
    }
}

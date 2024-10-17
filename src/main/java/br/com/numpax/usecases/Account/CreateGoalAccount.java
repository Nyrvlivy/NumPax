package br.com.numpax.usecases.Account;

import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.GoalAccount;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.application.enums.AccountType;

import java.time.LocalDate;

public class CreateGoalAccount {
    public GoalAccount execute(
        String name,
        String description,
        User user,
        double targetValue,
        double targetTaxRate,
        double monthlyTaxRate,
        double monthlyEstimate,
        double monthlyAchievement,
        Category category,
        LocalDate targetDate,
        LocalDate startDate,
        LocalDate endDate
    ) {
        LocalDate finalEndDate = (endDate == null) ? LocalDate.now() : endDate;
        return new GoalAccount(name,
            description,
            user,
            AccountType.GOAL,
            targetValue,
            targetTaxRate,
            monthlyTaxRate,
            monthlyEstimate,
            monthlyAchievement,
            category,
            targetDate,
            startDate,
            finalEndDate
        );
    }
}

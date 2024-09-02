package br.com.numpax.usecases.Account;

import java.time.LocalDate;

import br.com.numpax.domain.entities.Category;
import br.com.numpax.domain.entities.GoalAccount;
import br.com.numpax.domain.entities.User;
import br.com.numpax.domain.enums.AccountType;

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
    ){
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
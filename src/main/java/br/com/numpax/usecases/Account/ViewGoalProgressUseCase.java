package br.com.numpax.usecases.Account;

import br.com.numpax.infrastructure.entities.GoalAccount;

public class ViewGoalProgressUseCase {
    public void execute(GoalAccount account) {
        account.viewGoalProgress();
    }
}

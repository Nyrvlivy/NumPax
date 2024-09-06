package br.com.numpax.test.domain.entities;

import br.com.numpax.domain.entities.Account;
import br.com.numpax.domain.entities.RelatedAccounts;
import br.com.numpax.domain.entities.User;

import java.time.LocalDate;

public class RelatedAccountsTest {
    public static void main(String[] args) {
        testRelatedAccountsCreation();
        testSetRelatedAccounts();
    }

    private static void testRelatedAccountsCreation() {
        User user = new User("Sarah Regina Santos", "sarah.regina.santos@ci.com.br", "72qLyjOJHf", LocalDate.of(2000, 9, 1));
        Account[] relatedAccounts = new Account[2];
        relatedAccounts[0] = new Account("Account 1", "Description 1", user);
        relatedAccounts[1] = new Account("Account 2", "Description 2", user);
        RelatedAccounts relatedAccountsEntity = new RelatedAccounts("Related Accounts", "Description", user, 2000.0, relatedAccounts, 2);

        assert 2000.0 == relatedAccountsEntity.getTotalBalance();
        assert 2 == relatedAccountsEntity.getTotalAccounts();
        assert relatedAccounts.length == relatedAccountsEntity.getRelatedAccounts().length;
    }

    private static void testSetRelatedAccounts() {
        User user = new User("Daiane Mirella Nunes", "daiane_nunes@iname.com", "yFzFMeWNCg", LocalDate.of(2000, 5, 17));
        Account[] relatedAccounts = new Account[2];
        relatedAccounts[0] = new Account("Account 1", "Description 1", user);
        relatedAccounts[1] = new Account("Account 2", "Description 2", user);
        RelatedAccounts relatedAccountsEntity = new RelatedAccounts("Related Accounts", "Description", user, 2000.0, relatedAccounts, 2);

        Account[] newRelatedAccounts = new Account[3];
        newRelatedAccounts[0] = new Account("Account 3", "Description 3", user);
        newRelatedAccounts[1] = new Account("Account 4", "Description 4", user);
        newRelatedAccounts[2] = new Account("Account 5", "Description 5", user);
        relatedAccountsEntity.setRelatedAccounts(newRelatedAccounts);

        assert newRelatedAccounts.length == relatedAccountsEntity.getRelatedAccounts().length;
    }
}

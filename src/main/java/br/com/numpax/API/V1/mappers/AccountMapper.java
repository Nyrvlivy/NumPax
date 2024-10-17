package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.User;

public class AccountMapper {
    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(account.getId(), account.getName(), account.getBalance());
    }

    public static Account toEntity(AccountDTO accountDTO, User user) {
        return new Account(accountDTO.getId(), accountDTO.getName(), "", accountDTO.getBalance(), true, user, null, null);
    }
}
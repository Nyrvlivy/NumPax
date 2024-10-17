package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.infrastructure.entities.Account;

public class AccountMapper {

    public static Account toEntity(AccountDTO dto) {
        return new Account(
            dto.getId(),
            dto.getName(),
            dto.getDescription(),
            dto.getBalance(),
            dto.getAccountType(),
            dto.getIsActive(),
            dto.getCreatedAt(),
            dto.getUpdatedAt(),
            dto.getUserId()
        );
    }

    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(
            account.getId(),
            account.getName(),
            account.getDescription(),
            account.getBalance(),
            account.getAccountType(),
            account.getIsActive(),
            account.getCreatedAt(),
            account.getUpdatedAt(),
            account.getUserId()
        );
    }
}

package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.CheckingAccount;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.SavingsAccount;
import java.math.BigDecimal;

public class AccountMapper {

    public static Account toEntity(AccountDTO dto) {
        Account account;
        switch (dto.getAccountType()) {
            case CHECKING:
                account = new CheckingAccount(
                    dto.getName(),
                    dto.getDescription(),
                    dto.getAccountType(),
                    dto.getUserId(),
                    "", // bank name
                    "", // agency
                    ""  // account number
                );
                break;
            case SAVINGS:
                account = new SavingsAccount(
                    dto.getName(),
                    dto.getDescription(),
                    dto.getUserId(),
                    null, // nearest deadline
                    null, // furthest deadline
                    null, // latest deadline
                    BigDecimal.ZERO, // average tax rate
                    BigDecimal.ZERO, // number of fixed investments
                    BigDecimal.ZERO, // total maturity amount
                    BigDecimal.ZERO  // total deposit amount
                );
                break;
            case INVESTMENT:
                account = new InvestmentAccount(
                    dto.getName(),
                    dto.getDescription(),
                    dto.getUserId(),
                    InvestmentSubtype.OTHER // default subtype
                );
                break;
            default:
                throw new IllegalArgumentException("Tipo de conta não suportado: " + dto.getAccountType());
        }
        
        account.setBalance(dto.getBalance());
        account.setActive(dto.isActive());
        return account;
    }

    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(
            account.getId(),
            account.getName(),
            account.getDescription(),
            account.getBalance(),
            account.getAccountType(),
            account.isActive(),
            account.getCreatedAt(),
            account.getUpdatedAt(),
            account.getUserId()
        );
    }
}

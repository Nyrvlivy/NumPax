package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.entities.Account;
import br.com.numpax.API.V1.entities.CheckingAccount;
import br.com.numpax.API.V1.entities.SavingsAccount;
import br.com.numpax.API.V1.entities.InvestmentAccount;
import br.com.numpax.API.V1.entities.GoalAccount;
import br.com.numpax.API.V1.entities.RelatedAccount;
import br.com.numpax.API.V1.mappers.CheckingAccountMapper;
import br.com.numpax.API.V1.mappers.SavingsAccountMapper;
import br.com.numpax.API.V1.mappers.InvestmentAccountMapper;
import br.com.numpax.API.V1.mappers.GoalAccountMapper;
import br.com.numpax.API.V1.mappers.RelatedAccountMapper;

public class AccountMapperFactory {
    private static final AccountMapperFactory INSTANCE = new AccountMapperFactory();
    
    private AccountMapperFactory() {}
    
    public static AccountMapperFactory getInstance() {
        return INSTANCE;
    }

    public Account toEntity(AccountDTO dto) {
        if (dto == null) {
            return null;
        }

        return switch (dto.getAccountType()) {
            case CHECKING -> CheckingAccountMapper.getInstance().toEntity((CheckingAccountDTO) dto);
            case SAVINGS -> SavingsAccountMapper.getInstance().toEntity((SavingsAccountDTO) dto);
            case INVESTMENT -> InvestmentAccountMapper.getInstance().toEntity((InvestmentAccountDTO) dto);
            case GOAL -> GoalAccountMapper.getInstance().toEntity((GoalAccountDTO) dto);
            default -> throw new IllegalArgumentException("Unsupported account type: " + dto.getAccountType());
        };
    }

    public AccountDTO toDTO(Account entity) {
        if (entity == null) {
            return null;
        }

        return switch (entity.getAccountType()) {
            case CHECKING -> CheckingAccountMapper.getInstance().toDTO((CheckingAccount) entity);
            case SAVINGS -> SavingsAccountMapper.getInstance().toDTO((SavingsAccount) entity);
            case INVESTMENT -> InvestmentAccountMapper.getInstance().toDTO((InvestmentAccount) entity);
            case GOAL -> GoalAccountMapper.getInstance().toDTO((GoalAccount) entity);
            default -> throw new IllegalArgumentException("Unsupported account type: " + entity.getAccountType());
        };
    }
} 
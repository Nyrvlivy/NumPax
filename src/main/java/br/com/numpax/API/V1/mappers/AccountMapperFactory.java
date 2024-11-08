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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapperFactory {
    @Autowired private CheckingAccountMapper checkingAccountMapper;
    @Autowired private SavingsAccountMapper savingsAccountMapper;
    @Autowired private InvestmentAccountMapper investmentAccountMapper;
    @Autowired private GoalAccountMapper goalAccountMapper;
    @Autowired private RelatedAccountMapper relatedAccountMapper;

    public Account toEntity(AccountDTO dto) {
        return switch (dto.getAccountType()) {
            case CHECKING -> checkingAccountMapper.toEntity((CheckingAccountDTO) dto);
            case SAVINGS -> savingsAccountMapper.toEntity((SavingsAccountDTO) dto);
            case INVESTMENT -> investmentAccountMapper.toEntity((InvestmentAccountDTO) dto);
            case GOAL -> goalAccountMapper.toEntity((GoalAccountDTO) dto);
            case RELATED -> relatedAccountMapper.toEntity((RelatedAccountDTO) dto);
            default -> throw new IllegalArgumentException("Unsupported account type: " + dto.getAccountType());
        };
    }

    public AccountDTO toDTO(Account entity) {
        return switch (entity.getAccountType()) {
            case CHECKING -> checkingAccountMapper.toDTO((CheckingAccount) entity);
            case SAVINGS -> savingsAccountMapper.toDTO((SavingsAccount) entity);
            case INVESTMENT -> investmentAccountMapper.toDTO((InvestmentAccount) entity);
            case GOAL -> goalAccountMapper.toDTO((GoalAccount) entity);
            case RELATED -> relatedAccountMapper.toDTO((RelatedAccount) entity);
            default -> throw new IllegalArgumentException("Unsupported account type: " + entity.getAccountType());
        };
    }
} 
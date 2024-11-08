package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.*;
import br.com.numpax.infrastructure.entities.*;
import br.com.numpax.application.enums.AccountType;
import br.com.numpax.API.V1.exceptions.InvalidAccountTypeException;

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

        try {
            return switch (dto.getAccountType()) {
                case CHECKING -> {
                    validateDtoType(dto, CheckingAccountDTO.class);
                    yield CheckingAccountMapper.getInstance().toEntity((CheckingAccountDTO) dto);
                }
                case SAVINGS -> {
                    validateDtoType(dto, SavingsAccountDTO.class);
                    yield SavingsAccountMapper.getInstance().toEntity((SavingsAccountDTO) dto);
                }
                case INVESTMENT -> {
                    validateDtoType(dto, InvestmentAccountDTO.class);
                    yield InvestmentAccountMapper.getInstance().toEntity((InvestmentAccountDTO) dto);
                }
                case GOAL -> {
                    validateDtoType(dto, GoalAccountDTO.class);
                    yield GoalAccountMapper.getInstance().toEntity((GoalAccountDTO) dto);
                }
            };
        } catch (ClassCastException e) {
            throw new InvalidAccountTypeException("Invalid DTO type for account type: " + dto.getAccountType(), e);
        }
    }

    public AccountDTO toDTO(Account entity) {
        if (entity == null) {
            return null;
        }

        try {
            return switch (entity.getAccountType()) {
                case CHECKING -> {
                    validateEntityType(entity, CheckingAccount.class);
                    yield CheckingAccountMapper.getInstance().toDTO((CheckingAccount) entity);
                }
                case SAVINGS -> {
                    validateEntityType(entity, SavingsAccount.class);
                    yield SavingsAccountMapper.getInstance().toDTO((SavingsAccount) entity);
                }
                case INVESTMENT -> {
                    validateEntityType(entity, InvestmentAccount.class);
                    yield InvestmentAccountMapper.getInstance().toDTO((InvestmentAccount) entity);
                }
                case GOAL -> {
                    validateEntityType(entity, GoalAccount.class);
                    yield GoalAccountMapper.getInstance().toDTO((GoalAccount) entity);
                }
            };
        } catch (ClassCastException e) {
            throw new InvalidAccountTypeException("Invalid entity type for account type: " + entity.getAccountType(), e);
        }
    }

    private <T> void validateDtoType(AccountDTO dto, Class<T> expectedClass) {
        if (!expectedClass.isInstance(dto)) {
            throw new InvalidAccountTypeException(
                String.format("Expected DTO of type %s but got %s",
                    expectedClass.getSimpleName(),
                    dto.getClass().getSimpleName())
            );
        }
    }

    private <T> void validateEntityType(Account entity, Class<T> expectedClass) {
        if (!expectedClass.isInstance(entity)) {
            throw new InvalidAccountTypeException(
                String.format("Expected entity of type %s but got %s",
                    expectedClass.getSimpleName(),
                    entity.getClass().getSimpleName())
            );
        }
    }
}

package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.dto.GoalAccountDTO;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.GoalAccount;

public class GoalAccountMapper {
    private static final GoalAccountMapper INSTANCE = new GoalAccountMapper();
    
    private GoalAccountMapper() {} // Construtor privado para singleton
    
    public static GoalAccountMapper getInstance() {
        return INSTANCE;
    }

    public GoalAccount toEntity(GoalAccountDTO dto) {
        if (dto == null) {
            return null;
        }

        GoalAccount account = new GoalAccount(
            dto.getName(),
            dto.getDescription(),
            dto.getAccountType(),
            dto.getUserId(),
            dto.getTargetAmount(),
            dto.getTargetDate(),
            dto.getCategory(),
            dto.getPriority()
        );
        
        setCommonFields(account, dto);
        account.setMonthlyContribution(dto.getMonthlyContribution());
        account.setProgressPercentage(dto.getProgressPercentage());
        account.setIsCompleted(dto.getIsCompleted());
        
        return account;
    }
    
    public GoalAccountDTO toDTO(GoalAccount entity) {
        if (entity == null) {
            return null;
        }

        return new GoalAccountDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getBalance(),
            entity.isActive(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getUserId(),
            entity.getTargetAmount(),
            entity.getTargetDate(),
            entity.getMonthlyContribution(),
            entity.getProgressPercentage(),
            entity.getCategory(),
            entity.getPriority(),
            entity.getIsCompleted()
        );
    }
    
    private void setCommonFields(Account account, AccountDTO dto) {
        if (dto.getId() != null) {
            account.setId(dto.getId());
        }
        if (dto.getBalance() != null) {
            account.setBalance(dto.getBalance());
        }
        account.setActive(dto.getIsActive());
        if (dto.getCreatedAt() != null) {
            account.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            account.setUpdatedAt(dto.getUpdatedAt());
        }
    }
} 
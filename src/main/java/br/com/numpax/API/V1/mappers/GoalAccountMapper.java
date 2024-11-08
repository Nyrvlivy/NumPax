package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.GoalAccountDTO;
import br.com.numpax.API.V1.entities.Account;
import br.com.numpax.API.V1.entities.GoalAccount;
import org.springframework.stereotype.Component;

@Component
public class GoalAccountMapper {
    public GoalAccount toEntity(GoalAccountDTO dto) {
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
        return GoalAccountDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .balance(entity.getBalance())
            .isActive(entity.isActive())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .userId(entity.getUserId())
            .targetAmount(entity.getTargetAmount())
            .targetDate(entity.getTargetDate())
            .monthlyContribution(entity.getMonthlyContribution())
            .progressPercentage(entity.getProgressPercentage())
            .category(entity.getCategory())
            .priority(entity.getPriority())
            .isCompleted(entity.getIsCompleted())
            .build();
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
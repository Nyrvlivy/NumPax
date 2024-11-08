package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.dto.SavingsAccountDTO;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.SavingsAccount;

import java.math.BigDecimal;

public class SavingsAccountMapper {
    private static final SavingsAccountMapper INSTANCE = new SavingsAccountMapper();
    
    private SavingsAccountMapper() {} // Private constructor for singleton
    
    public static SavingsAccountMapper getInstance() {
        return INSTANCE;
    }

    public SavingsAccount toEntity(SavingsAccountDTO dto) {
        SavingsAccount account = new SavingsAccount(
            dto.getName(),
            dto.getDescription(),
            dto.getAccountType(),
            dto.getUserId(),
            dto.getNearestDeadline(),
            dto.getFurthestDeadline(),
            dto.getLatestDeadline(),
            dto.getAverageTaxRate(),
            dto.getNumberOfFixedInvestments(),
            dto.getTotalMaturityAmount(),
            dto.getTotalDepositAmount()
        );

        setCommonFields(account, dto);
        account.setNumberOfFixedInvestments(BigDecimal.valueOf(dto.getNumberOfFixedInvestments()));
        account.setTotalMaturityAmount(dto.getTotalMaturityAmount());
        account.setTotalDepositAmount(dto.getTotalDepositAmount());

        return account;
    }

    public SavingsAccountDTO toDTO(SavingsAccount entity) {
        return SavingsAccountDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .balance(entity.getBalance())
            .isActive(entity.isActive())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .userId(entity.getUserId())
            .nearestDeadline(entity.getNearestDeadline())
            .furthestDeadline(entity.getFurthestDeadline())
            .latestDeadline(entity.getLatestDeadline())
            .averageTaxRate(entity.getAverageTaxRate())
            .numberOfFixedInvestments(entity.getNumberOfFixedInvestments())
            .totalMaturityAmount(entity.getTotalMaturityAmount())
            .totalDepositAmount(entity.getTotalDepositAmount())
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
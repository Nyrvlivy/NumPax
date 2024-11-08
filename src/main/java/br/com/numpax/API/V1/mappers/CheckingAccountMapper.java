package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.dto.CheckingAccountDTO;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.CheckingAccount;

public class CheckingAccountMapper {
    public CheckingAccount toEntity(CheckingAccountDTO dto) {
        CheckingAccount account = new CheckingAccount(
            dto.getName(),
            dto.getDescription(),
            dto.getAccountType(),
            dto.getUserId(),
            dto.getBankName(),
            dto.getAgency(),
            dto.getAccountNumber()
        );
        
        setCommonFields(account, dto);
        return account;
    }
    
    public CheckingAccountDTO toDTO(CheckingAccount entity) {
        return CheckingAccountDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .balance(entity.getBalance())
            .isActive(entity.isActive())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .userId(entity.getUserId())
            .bankName(entity.getBankName())
            .agency(entity.getAgency())
            .accountNumber(entity.getAccountNumber())
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
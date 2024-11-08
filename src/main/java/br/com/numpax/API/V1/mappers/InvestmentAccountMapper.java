package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.InvestmentAccountDTO;
import br.com.numpax.API.V1.entities.InvestmentAccount;
import br.com.numpax.API.V1.entities.Account;
import br.com.numpax.API.V1.entities.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class InvestmentAccountMapper {
    public InvestmentAccount toEntity(InvestmentAccountDTO dto) {
        InvestmentAccount account = new InvestmentAccount(
            dto.getName(),
            dto.getDescription(),
            dto.getAccountType(),
            dto.getUserId(),
            dto.getBroker(),
            dto.getAccountNumber(),
            dto.getInvestmentTypes()
        );
        
        setCommonFields(account, dto);
        account.setTotalInvested(dto.getTotalInvested());
        account.setProfitability(dto.getProfitability());
        account.setCurrentYield(dto.getCurrentYield());
        account.setLastUpdate(dto.getLastUpdate());
        
        return account;
    }
    
    public InvestmentAccountDTO toDTO(InvestmentAccount entity) {
        return InvestmentAccountDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .balance(entity.getBalance())
            .isActive(entity.isActive())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .userId(entity.getUserId())
            .broker(entity.getBroker())
            .accountNumber(entity.getAccountNumber())
            .totalInvested(entity.getTotalInvested())
            .profitability(entity.getProfitability())
            .currentYield(entity.getCurrentYield())
            .lastUpdate(entity.getLastUpdate())
            .investmentTypes(entity.getInvestmentTypes())
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
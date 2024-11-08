package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.API.V1.dto.InvestmentAccountDTO;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.InvestmentAccount;

public class InvestmentAccountMapper {
    private static final InvestmentAccountMapper INSTANCE = new InvestmentAccountMapper();
    
    private InvestmentAccountMapper() {} // Construtor privado para singleton
    
    public static InvestmentAccountMapper getInstance() {
        return INSTANCE;
    }

    public InvestmentAccount toEntity(InvestmentAccountDTO dto) {
        if (dto == null) {
            return null;
        }

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
        if (entity == null) {
            return null;
        }

        return new InvestmentAccountDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getBalance(),
            entity.isActive(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getUserId(),
            entity.getBroker(),
            entity.getAccountNumber(),
            entity.getTotalInvested(),
            entity.getProfitability(),
            entity.getCurrentYield(),
            entity.getLastUpdate(),
            entity.getInvestmentTypes()
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
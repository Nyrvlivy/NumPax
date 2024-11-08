package br.com.numpax.application.mapper;

import br.com.numpax.application.dto.request.CreateInvestmentAccountRequestDTO;
import br.com.numpax.application.dto.response.InvestmentAccountResponseDTO;
import br.com.numpax.application.entity.InvestmentAccount;
import br.com.numpax.application.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class InvestmentAccountMapper extends AccountMapper {
    
    public InvestmentAccount toEntity(CreateInvestmentAccountRequestDTO dto, User user) {
        InvestmentAccount entity = new InvestmentAccount();
        mapBaseAccountFields(entity, dto, user);
        
        entity.setTotalInvestedAmount(dto.getTotalInvestedAmount());
        entity.setTotalProfit(dto.getTotalProfit());
        entity.setTotalCurrentAmount(dto.getTotalCurrentAmount());
        entity.setTotalWithdrawnAmount(dto.getTotalWithdrawnAmount());
        entity.setNumberOfWithdrawals(dto.getNumberOfWithdrawals());
        entity.setNumberOfEntries(dto.getNumberOfEntries());
        entity.setNumberOfAssets(dto.getNumberOfAssets());
        entity.setAveragePurchasePrice(dto.getAveragePurchasePrice());
        entity.setTotalGainLoss(dto.getTotalGainLoss());
        entity.setTotalDividendYield(dto.getTotalDividendYield());
        entity.setRiskLevelType(dto.getRiskLevelType());
        entity.setInvestmentSubtype(dto.getInvestmentSubtype());
        
        return entity;
    }
    
    public InvestmentAccountResponseDTO toDTO(InvestmentAccount entity) {
        if (entity == null) return null;
        
        InvestmentAccountResponseDTO dto = new InvestmentAccountResponseDTO();
        // Map base fields
        AccountResponseDTO baseDTO = super.toDTO(entity);
        BeanUtils.copyProperties(baseDTO, dto);
        
        // Map investment account specific fields
        dto.setTotalInvestedAmount(entity.getTotalInvestedAmount());
        dto.setTotalProfit(entity.getTotalProfit());
        dto.setTotalCurrentAmount(entity.getTotalCurrentAmount());
        dto.setTotalWithdrawnAmount(entity.getTotalWithdrawnAmount());
        dto.setNumberOfWithdrawals(entity.getNumberOfWithdrawals());
        dto.setNumberOfEntries(entity.getNumberOfEntries());
        dto.setNumberOfAssets(entity.getNumberOfAssets());
        dto.setAveragePurchasePrice(entity.getAveragePurchasePrice());
        dto.setTotalGainLoss(entity.getTotalGainLoss());
        dto.setTotalDividendYield(entity.getTotalDividendYield());
        dto.setRiskLevelType(entity.getRiskLevelType());
        dto.setInvestmentSubtype(entity.getInvestmentSubtype());
        
        return dto;
    }
} 
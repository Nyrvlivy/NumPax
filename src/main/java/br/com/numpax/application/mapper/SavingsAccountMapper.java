package br.com.numpax.application.mapper;

import br.com.numpax.application.dto.request.CreateSavingsAccountRequestDTO;
import br.com.numpax.application.dto.response.AccountResponseDTO;
import br.com.numpax.application.dto.response.SavingsAccountResponseDTO;
import br.com.numpax.application.entity.SavingsAccount;
import br.com.numpax.application.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SavingsAccountMapper extends AccountMapper {
    
    public SavingsAccount toEntity(CreateSavingsAccountRequestDTO dto, User user) {
        SavingsAccount entity = new SavingsAccount();
        mapBaseAccountFields(entity, dto, user);
        
        entity.setNearestDeadline(dto.getNearestDeadline());
        entity.setFurthestDeadline(dto.getFurthestDeadline());
        entity.setLatestDeadline(dto.getLatestDeadline());
        entity.setAverageTaxRate(dto.getAverageTaxRate());
        entity.setNumberOfFixedInvestments(dto.getNumberOfFixedInvestments());
        entity.setTotalMaturityAmount(dto.getTotalMaturityAmount());
        entity.setTotalDepositAmount(dto.getTotalDepositAmount());
        
        return entity;
    }
    
    public SavingsAccountResponseDTO toDTO(SavingsAccount entity) {
        if (entity == null) return null;
        
        SavingsAccountResponseDTO dto = new SavingsAccountResponseDTO();
        // Map base fields
        AccountResponseDTO baseDTO = super.toDTO(entity);
        BeanUtils.copyProperties(baseDTO, dto);
        
        // Map savings account specific fields
        dto.setNearestDeadline(entity.getNearestDeadline());
        dto.setFurthestDeadline(entity.getFurthestDeadline());
        dto.setLatestDeadline(entity.getLatestDeadline());
        dto.setAverageTaxRate(entity.getAverageTaxRate());
        dto.setNumberOfFixedInvestments(entity.getNumberOfFixedInvestments());
        dto.setTotalMaturityAmount(entity.getTotalMaturityAmount());
        dto.setTotalDepositAmount(entity.getTotalDepositAmount());
        
        return dto;
    }
} 
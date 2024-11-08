package br.com.numpax.application.mapper;

import br.com.numpax.application.dto.request.CreateGoalAccountRequestDTO;
import br.com.numpax.application.dto.response.AccountResponseDTO;
import br.com.numpax.application.dto.response.GoalAccountResponseDTO;
import br.com.numpax.application.entity.GoalAccount;
import br.com.numpax.application.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class GoalAccountMapper extends AccountMapper {
    
    public GoalAccount toEntity(CreateGoalAccountRequestDTO dto, User user) {
        GoalAccount entity = new GoalAccount();
        mapBaseAccountFields(entity, dto, user);
        
        entity.setTargetValue(dto.getTargetValue());
        entity.setAmountValue(dto.getAmountValue());
        entity.setTargetTaxRate(dto.getTargetTaxRate());
        entity.setMonthlyTaxRate(dto.getMonthlyTaxRate());
        entity.setMonthlyEstimate(dto.getMonthlyEstimate());
        entity.setMonthlyAchievement(dto.getMonthlyAchievement());
        // Category needs to be set through a service that fetches the category by ID
        entity.setTargetDate(dto.getTargetDate());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        
        return entity;
    }
    
    public GoalAccountResponseDTO toDTO(GoalAccount entity) {
        if (entity == null) return null;
        
        GoalAccountResponseDTO dto = new GoalAccountResponseDTO();
        // Map base fields
        AccountResponseDTO baseDTO = super.toDTO(entity);
        BeanUtils.copyProperties(baseDTO, dto);
        
        // Map goal account specific fields
        dto.setTargetValue(entity.getTargetValue());
        dto.setAmountValue(entity.getAmountValue());
        dto.setTargetTaxRate(entity.getTargetTaxRate());
        dto.setMonthlyTaxRate(entity.getMonthlyTaxRate());
        dto.setMonthlyEstimate(entity.getMonthlyEstimate());
        dto.setMonthlyAchievement(entity.getMonthlyAchievement());
        dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getId() : null);
        dto.setTargetDate(entity.getTargetDate());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        
        return dto;
    }
} 
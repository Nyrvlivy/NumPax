package br.com.numpax.application.mapper;

import br.com.numpax.application.dto.request.CreateCheckingAccountRequestDTO;
import br.com.numpax.application.dto.response.CheckingAccountResponseDTO;
import br.com.numpax.application.entity.CheckingAccount;
import br.com.numpax.application.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CheckingAccountMapper extends AccountMapper {
    
    public CheckingAccount toEntity(CreateCheckingAccountRequestDTO dto, User user) {
        CheckingAccount entity = new CheckingAccount();
        mapBaseAccountFields(entity, dto, user);
        entity.setBankName(dto.getBankCode());
        entity.setAgency(dto.getAgency());
        entity.setAccountNumber(dto.getAccountNumber());
        return entity;
    }
    
    public CheckingAccountResponseDTO toDTO(CheckingAccount entity) {
        if (entity == null) return null;
        
        CheckingAccountResponseDTO dto = new CheckingAccountResponseDTO();
        // Map base fields
        AccountResponseDTO baseDTO = super.toDTO(entity);
        BeanUtils.copyProperties(baseDTO, dto);
        
        // Map checking account specific fields
        dto.setBankCode(entity.getBankName());
        dto.setAgency(entity.getAgency());
        dto.setAccountNumber(entity.getAccountNumber());
        return dto;
    }
} 
package br.com.numpax.application.mapper;

import br.com.numpax.application.dto.response.AccountResponseDTO;
import br.com.numpax.application.dto.request.CreateAccountRequestDTO;
import br.com.numpax.application.entity.Account;
import br.com.numpax.application.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMapper {
    
    public AccountResponseDTO toDTO(Account entity) {
        if (entity == null) return null;
        
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountId(entity.getAccountId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setBalance(entity.getBalance());
        dto.setAccountType(entity.getAccountType());
        dto.setActive(entity.isActive());
        dto.setUserId(entity.getUserId().getUserId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
    
    protected void mapBaseAccountFields(Account entity, CreateAccountRequestDTO dto, User user) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAccountType(dto.getAccountType());
        entity.setUserId(user);
        entity.setBalance(BigDecimal.ZERO);
        entity.setActive(true);
    }
} 
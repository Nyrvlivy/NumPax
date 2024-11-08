package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.GoalAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.GoalAccountResponseDTO;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.GoalAccount;
import br.com.numpax.infrastructure.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class GoalAccountMapper {

    public static GoalAccount toEntity(GoalAccountRequestDTO dto, User user, Category category) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        // Mapeamento para entidade
        GoalAccount account = new GoalAccount();
        account.setAccountId(UUID.randomUUID().toString());
        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(dto.getAccountType());
        account.setIsActive(true);
        account.setUserId(user);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setTargetValue(dto.getTargetValue());
        account.setAmountValue(BigDecimal.ZERO);
        account.setTargetDate(dto.getTargetDate());
        account.setStartDate(LocalDateTime.now().toLocalDate());
        account.setCategory(category);
        return account;
    }

    public static GoalAccountResponseDTO toResponseDTO(GoalAccount account) {
        GoalAccountResponseDTO dto = new GoalAccountResponseDTO();
        dto.setAccountId(account.getAccountId());
        dto.setName(account.getName());
        dto.setDescription(account.getDescription());
        dto.setBalance(account.getBalance());
        dto.setAccountType(account.getAccountType());
        dto.setActive(account.isActive());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());
        dto.setTargetValue(account.getTargetValue());
        dto.setAmountValue(account.getAmountValue());
        dto.setTargetTaxRate(account.getTargetTaxRate());
        dto.setMonthlyTaxRate(account.getMonthlyTaxRate());
        dto.setMonthlyEstimate(account.getMonthlyEstimate());
        dto.setMonthlyAchievement(account.getMonthlyAchievement());
        dto.setCategory(account.getCategory());
        dto.setTargetDate(account.getTargetDate());
        dto.setStartDate(account.getStartDate());
        dto.setEndDate(account.getEndDate());
        return dto;
    }
}

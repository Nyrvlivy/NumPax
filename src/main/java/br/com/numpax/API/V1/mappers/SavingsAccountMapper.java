package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.SavingsAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.SavingsAccountResponseDTO;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.SavingsAccount;
import br.com.numpax.infrastructure.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class SavingsAccountMapper {

    public static SavingsAccount toEntity(SavingsAccountRequestDTO dto, User user) {
        SavingsAccount account = new SavingsAccount();
        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setNearestDeadline(dto.getNearestDeadline());
        account.setFurthestDeadline(dto.getFurthestDeadline());
        account.setLatestDeadline(dto.getLatestDeadline());
        account.setAverageTaxRate(dto.getAverageTaxRate());
        account.setNumberOfFixedInvestments(dto.getNumberOfFixedInvestments());
        account.setTotalMaturityAmount(dto.getTotalMaturityAmount());
        account.setTotalDepositAmount(dto.getTotalDepositAmount());
        account.setUserId(user);

        return account;
    }

    public static SavingsAccountResponseDTO toResponseDTO(SavingsAccount account) {
        SavingsAccountResponseDTO dto = new SavingsAccountResponseDTO();
        dto.setAccountId(account.getAccountId());
        dto.setName(account.getName());
        dto.setDescription(account.getDescription());
        dto.setBalance(account.getBalance());
        dto.setAccountType(account.getAccountType());
        dto.setActive(account.isActive());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());

        // Campos específicos da SavingsAccount
        dto.setNearestDeadline(account.getNearestDeadline());
        dto.setFurthestDeadline(account.getFurthestDeadline());
        dto.setLatestDeadline(account.getLatestDeadline());
        dto.setAverageTaxRate(account.getAverageTaxRate());
        dto.setNumberOfFixedInvestments(account.getNumberOfFixedInvestments());
        dto.setTotalMaturityAmount(account.getTotalMaturityAmount());
        dto.setTotalDepositAmount(account.getTotalDepositAmount());

        return dto;
    }
}

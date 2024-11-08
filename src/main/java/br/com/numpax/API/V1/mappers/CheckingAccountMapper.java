package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.CreateCheckingAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.CheckingAccountResponseDTO;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.CheckingAccount;
import br.com.numpax.infrastructure.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CheckingAccountMapper {

    public static CheckingAccount toEntity(CreateCheckingAccountRequestDTO dto, User user) {

        ValidatorUtil.validate(dto);

        CheckingAccount account = new CheckingAccount();
        account.setAccountId(UUID.randomUUID().toString());
        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(dto.getAccountType());
        account.setActive(true);
        account.setUserId(user);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setBankCode(dto.getBankCode());
        account.setAgency(dto.getAgency());
        account.setAccountNumber(dto.getAccountNumber());
        return account;
    }

    public static CheckingAccountResponseDTO toResponseDTO(CheckingAccount account) {
        CheckingAccountResponseDTO dto = new CheckingAccountResponseDTO();
        dto.setAccountId(account.getAccountId());
        dto.setName(account.getName());
        dto.setDescription(account.getDescription());
        dto.setBalance(account.getBalance());
        dto.setAccountType(account.getAccountType());
        dto.setActive(account.isActive());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());
        dto.setBankCode(account.getBankCode());
        dto.setAgency(account.getAgency());
        dto.setAccountNumber(account.getAccountNumber());
        return dto;
    }
}

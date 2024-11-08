package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.FixedInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.response.FixedInvestmentResponseDTO;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.FixedInvestment;
import br.com.numpax.infrastructure.entities.InvestmentAccount;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedInvestmentMapper {

    public static FixedInvestment toEntity(FixedInvestmentRequestDTO dto, InvestmentAccount account) {
        ValidatorUtil.validate(dto);

        FixedInvestment investment = new FixedInvestment();
        investment.setFixedInvestmentId(UUID.randomUUID().toString());
        investment.setName(dto.getName());
        investment.setDescription(dto.getDescription());
        investment.setInvestmentType(dto.getInvestmentType());
        investment.setInvestmentAccount(account);
        investment.setInvestmentAmount(dto.getInvestmentAmount());
        investment.setTaxRate(dto.getTaxRate());
        investment.setInvestmentDate(dto.getInvestmentDate());
        investment.setMaturityDate(dto.getMaturityDate());
        investment.setExpectedReturn(dto.getExpectedReturn());
        investment.setBroker(dto.getBroker());
        investment.setInstitution(dto.getInstitution());
        investment.setNote(dto.getNote());
        investment.setRedeemed(false);
        investment.setActive(true);
        investment.setCreatedAt(LocalDateTime.now());
        investment.setUpdatedAt(LocalDateTime.now());
        
        return investment;
    }

    public static FixedInvestmentResponseDTO toResponseDTO(FixedInvestment investment) {
        FixedInvestmentResponseDTO dto = new FixedInvestmentResponseDTO();
        dto.setFixedInvestmentId(investment.getFixedInvestmentId());
        dto.setName(investment.getName());
        dto.setDescription(investment.getDescription());
        dto.setInvestmentType(investment.getInvestmentType());
        dto.setInvestmentAccountId(investment.getInvestmentAccount().getAccountId());
        dto.setInvestmentAmount(investment.getInvestmentAmount());
        dto.setTaxRate(investment.getTaxRate());
        dto.setInvestmentDate(investment.getInvestmentDate());
        dto.setMaturityDate(investment.getMaturityDate());
        dto.setExpectedReturn(investment.getExpectedReturn());
        dto.setCurrentAmount(investment.getCurrentAmount());
        dto.setProfitAmount(investment.getProfitAmount());
        dto.setBroker(investment.getBroker());
        dto.setInstitution(investment.getInstitution());
        dto.setNote(investment.getNote());
        dto.setRedeemed(investment.isRedeemed());
        dto.setRedemptionDate(investment.getRedemptionDate());
        dto.setRedemptionAmount(investment.getRedemptionAmount());
        dto.setActive(investment.isActive());
        dto.setCreatedAt(investment.getCreatedAt());
        dto.setUpdatedAt(investment.getUpdatedAt());
        
        return dto;
    }
} 
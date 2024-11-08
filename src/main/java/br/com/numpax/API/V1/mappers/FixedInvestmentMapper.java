package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.FixedInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.response.FixedInvestmentResponseDTO;
import br.com.numpax.application.enums.FixedInvestmentType;
import br.com.numpax.application.enums.NatureOfTransaction;
import br.com.numpax.infrastructure.entities.FixedInvestment;
import java.util.UUID;
import java.time.LocalDateTime;

public class FixedInvestmentMapper {

    public static FixedInvestment toEntity(FixedInvestmentRequestDTO dto) {
        FixedInvestment investment = new FixedInvestment();
        investment.setTransactionId(UUID.randomUUID().toString());
        investment.setCode(dto.getCode());
        investment.setName(dto.getName());
        investment.setDescription(dto.getDescription());
        investment.setAmount(dto.getAmount());
        investment.setCategoryId(dto.getCategoryId());
        investment.setAccountId(dto.getAccountId());
        investment.setNatureOfTransaction(NatureOfTransaction.INVESTMENT);
        investment.setReceiver(dto.getReceiver());
        investment.setSender(dto.getSender());
        investment.setTransactionDate(dto.getInvestmentDate());
        investment.setNote(dto.getNote());
        investment.setCreatedAt(LocalDateTime.now());
        investment.setUpdatedAt(LocalDateTime.now());
        
        // FixedInvestment specific fields
        investment.setFixedInvestmentType(FixedInvestmentType.valueOf(dto.getFixedInvestmentType()));
        investment.setInvestmentDate(dto.getInvestmentDate());
        investment.setExpirationDate(dto.getExpirationDate());
        investment.setInstitution(dto.getInstitution());
        
        return investment;
    }

    public static FixedInvestmentResponseDTO toResponseDTO(FixedInvestment investment) {
        FixedInvestmentResponseDTO dto = new FixedInvestmentResponseDTO();
        dto.setTransactionId(investment.getTransactionId());
        dto.setCode(investment.getCode());
        dto.setName(investment.getName());
        dto.setDescription(investment.getDescription());
        dto.setAmount(investment.getAmount());
        dto.setCategoryId(investment.getCategoryId());
        dto.setAccountId(investment.getAccountId());
        dto.setNatureOfTransaction(investment.getNatureOfTransaction());
        dto.setReceiver(investment.getReceiver());
        dto.setSender(investment.getSender());
        dto.setTransactionDate(investment.getTransactionDate());
        dto.setNote(investment.getNote());
        dto.setCreatedAt(investment.getCreatedAt());
        dto.setUpdatedAt(investment.getUpdatedAt());
        
        // FixedInvestment specific fields
        dto.setFixedInvestmentType(investment.getFixedInvestmentType());
        dto.setInvestmentDate(investment.getInvestmentDate());
        dto.setExpirationDate(investment.getExpirationDate());
        dto.setInstitution(investment.getInstitution());
        dto.setRedeemValue(investment.getRedeemValue());
        dto.setRedeemDate(investment.getRedeemDate());
        dto.setLiquidityPeriod(investment.getLiquidityPeriod());
        dto.setNetGainLoss(investment.getNetGainLoss());
        
        return dto;
    }
} 
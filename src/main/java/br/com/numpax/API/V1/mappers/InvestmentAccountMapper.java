package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.InvestmentAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.InvestmentAccountResponseDTO;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.VariableInvestment;
import br.com.numpax.application.enums.RiskLevelType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InvestmentAccountMapper {

    public static InvestmentAccount toEntity(InvestmentAccountRequestDTO dto) {
        ValidatorUtil.validate(dto);

        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId(UUID.randomUUID().toString());
        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setInstitution(dto.getInstitution());
        account.setAccountNumber(dto.getAccountNumber());
        account.setAgency(dto.getAgency());
        account.setNote(dto.getNote());
        account.setActive(true);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        return account;
    }

    public static InvestmentAccountResponseDTO toResponseDTO(
            InvestmentAccount account,
            RiskLevelType mostFrequentRiskLevel,
            Map<String, BigDecimal> calculatedValues,
            Map<String, Integer> calculatedCounts) {
        
        InvestmentAccountResponseDTO dto = new InvestmentAccountResponseDTO();
        
        // Dados básicos
        dto.setAccountId(account.getAccountId());
        dto.setName(account.getName());
        dto.setDescription(account.getDescription());
        dto.setInstitution(account.getInstitution());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAgency(account.getAgency());
        dto.setNote(account.getNote());
        dto.setActive(account.isActive());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());

        // RiskLevelType (calculado pela moda dos investimentos)
        dto.setRiskLevelType(mostFrequentRiskLevel);

        // Valores monetários calculados
        dto.setTotalInvestedAmount(calculatedValues.getOrDefault("totalInvestedAmount", BigDecimal.ZERO));
        dto.setTotalProfit(calculatedValues.getOrDefault("totalProfit", BigDecimal.ZERO));
        dto.setTotalCurrentAmount(calculatedValues.getOrDefault("totalCurrentAmount", BigDecimal.ZERO));
        dto.setTotalWithdrawnAmount(calculatedValues.getOrDefault("totalWithdrawnAmount", BigDecimal.ZERO));
        dto.setAveragePurchasePrice(calculatedValues.getOrDefault("averagePurchasePrice", BigDecimal.ZERO));
        dto.setTotalGainLoss(calculatedValues.getOrDefault("totalGainLoss", BigDecimal.ZERO));
        dto.setTotalDividendYield(calculatedValues.getOrDefault("totalDividendYield", BigDecimal.ZERO));

        // Contadores
        dto.setNumberOfWithdrawals(calculatedCounts.getOrDefault("numberOfWithdrawals", 0));
        dto.setNumberOfEntries(calculatedCounts.getOrDefault("numberOfEntries", 0));
        dto.setNumberOfAssets(calculatedCounts.getOrDefault("numberOfAssets", 0));

        return dto;
    }
}

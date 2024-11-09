package br.com.numpax.API.V1.mappers;


import br.com.numpax.API.V1.dto.request.InvestmentAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.InvestmentAccountResponseDTO;
import br.com.numpax.application.enums.InvestmentSubtype;
import br.com.numpax.application.enums.RiskLevelType;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class InvestmentAccountMapper {

    public static InvestmentAccount toEntity(InvestmentAccountRequestDTO dto, User user) {
        // Validação do DTO
        ValidatorUtil.validate(dto);

        // Mapeamento para entidade
        InvestmentAccount account = new InvestmentAccount();
        account.setAccountId(UUID.randomUUID().toString());
        account.setName(dto.getName());
        account.setDescription(dto.getDescription());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(dto.getAccountType());
        account.setIsActive(true);
        account.setUserId(user);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        // Campos específicos da InvestmentAccount com valores padrão
        account.setTotalInvestedAmount(dto.getTotalInvestedAmount() != null ? dto.getTotalInvestedAmount() : BigDecimal.ZERO);
        account.setTotalProfit(dto.getTotalProfit() != null ? dto.getTotalProfit() : BigDecimal.ZERO);
        account.setTotalCurrentAmount(dto.getTotalCurrentAmount() != null ? dto.getTotalCurrentAmount() : BigDecimal.ZERO);
        account.setTotalWithdrawnAmount(dto.getTotalWithdrawnAmount() != null ? dto.getTotalWithdrawnAmount() : BigDecimal.ZERO);
        account.setNumberOfWithdrawals(dto.getNumberOfWithdrawals() != null ? dto.getNumberOfWithdrawals() : 0);
        account.setNumberOfEntries(dto.getNumberOfEntries() != null ? dto.getNumberOfEntries() : 0);
        account.setNumberOfAssets(dto.getNumberOfAssets() != null ? dto.getNumberOfAssets() : 0);
        account.setAveragePurchasePrice(dto.getAveragePurchasePrice() != null ? dto.getAveragePurchasePrice() : BigDecimal.ZERO);
        account.setTotalGainLoss(dto.getTotalGainLoss() != null ? dto.getTotalGainLoss() : BigDecimal.ZERO);
        account.setTotalDividendYield(dto.getTotalDividendYield() != null ? dto.getTotalDividendYield() : BigDecimal.ZERO);
        account.setRiskLevelType(dto.getRiskLevelType() != null ? dto.getRiskLevelType() : RiskLevelType.LOW);
        account.setInvestmentSubtype(dto.getInvestmentSubtype() != null ? dto.getInvestmentSubtype() : InvestmentSubtype.FIXED_INVESTMENT);

        return account;
    }

    public static InvestmentAccountResponseDTO toResponseDTO(InvestmentAccount account) {
        InvestmentAccountResponseDTO dto = new InvestmentAccountResponseDTO();
        dto.setAccountId(account.getAccountId());
        dto.setName(account.getName());
        dto.setDescription(account.getDescription());
        dto.setBalance(account.getBalance());
        dto.setAccountType(account.getAccountType());
        dto.setActive(account.isActive());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());

        // Campos específicos da InvestmentAccount
        dto.setTotalInvestedAmount(account.getTotalInvestedAmount());
        dto.setTotalProfit(account.getTotalProfit());
        dto.setTotalCurrentAmount(account.getTotalCurrentAmount());
        dto.setTotalWithdrawnAmount(account.getTotalWithdrawnAmount());
        dto.setNumberOfWithdrawals(account.getNumberOfWithdrawals());
        dto.setNumberOfEntries(account.getNumberOfEntries());
        dto.setNumberOfAssets(account.getNumberOfAssets());
        dto.setAveragePurchasePrice(account.getAveragePurchasePrice());
        dto.setTotalGainLoss(account.getTotalGainLoss());
        dto.setTotalDividendYield(account.getTotalDividendYield());
        dto.setRiskLevelType(account.getRiskLevelType());
        dto.setInvestmentSubtype(account.getInvestmentSubtype());

        return dto;
    }
}

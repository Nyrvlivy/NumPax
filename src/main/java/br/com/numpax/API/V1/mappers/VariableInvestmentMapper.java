package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.VariableInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.response.VariableInvestmentResponseDTO;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.VariableInvestment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public class VariableInvestmentMapper {

    public static VariableInvestment toEntity(VariableInvestmentRequestDTO dto, InvestmentAccount account) {
        ValidatorUtil.validate(dto);

        VariableInvestment investment = new VariableInvestment();
        investment.setVariableInvestmentId(UUID.randomUUID().toString());
        investment.setName(dto.getName());
        investment.setDescription(dto.getDescription());
        investment.setInvestmentType(dto.getInvestmentType());
        investment.setInvestmentAccount(account);
        investment.setTicker(dto.getTicker().toUpperCase());
        investment.setQuantity(dto.getQuantity());
        investment.setPurchasePrice(dto.getPurchasePrice());
        investment.setCurrentPrice(dto.getPurchasePrice()); // Inicialmente, preço atual = preço de compra
        investment.setTotalInvested(calculateTotalInvested(dto.getQuantity(), dto.getPurchasePrice()));
        investment.setCurrentTotal(investment.getTotalInvested()); // Inicialmente, total atual = total investido
        investment.setProfitAmount(BigDecimal.ZERO);
        investment.setProfitPercentage(BigDecimal.ZERO);
        investment.setPurchaseDate(dto.getPurchaseDate());
        investment.setSector(dto.getSector());
        investment.setBroker(dto.getBroker());
        investment.setNote(dto.getNote());
        investment.setActive(true);
        investment.setLastUpdate(LocalDateTime.now());
        investment.setCreatedAt(LocalDateTime.now());
        investment.setUpdatedAt(LocalDateTime.now());
        
        return investment;
    }

    public static VariableInvestmentResponseDTO toResponseDTO(VariableInvestment investment) {
        VariableInvestmentResponseDTO dto = new VariableInvestmentResponseDTO();
        dto.setVariableInvestmentId(investment.getVariableInvestmentId());
        dto.setName(investment.getName());
        dto.setDescription(investment.getDescription());
        dto.setInvestmentType(investment.getInvestmentType());
        dto.setInvestmentAccountId(investment.getInvestmentAccount().getAccountId());
        dto.setTicker(investment.getTicker());
        dto.setQuantity(investment.getQuantity());
        dto.setPurchasePrice(investment.getPurchasePrice());
        dto.setCurrentPrice(investment.getCurrentPrice());
        dto.setTotalInvested(investment.getTotalInvested());
        dto.setCurrentTotal(investment.getCurrentTotal());
        dto.setProfitAmount(investment.getProfitAmount());
        dto.setProfitPercentage(investment.getProfitPercentage());
        dto.setPurchaseDate(investment.getPurchaseDate());
        dto.setSector(investment.getSector());
        dto.setBroker(investment.getBroker());
        dto.setNote(investment.getNote());
        dto.setActive(investment.isActive());
        dto.setLastUpdate(investment.getLastUpdate());
        dto.setCreatedAt(investment.getCreatedAt());
        dto.setUpdatedAt(investment.getUpdatedAt());
        
        return dto;
    }

    public static void updatePriceAndCalculations(VariableInvestment investment, BigDecimal newPrice) {
        investment.setCurrentPrice(newPrice);
        investment.setCurrentTotal(calculateTotalInvested(investment.getQuantity(), newPrice));
        investment.setProfitAmount(calculateProfitAmount(investment));
        investment.setProfitPercentage(calculateProfitPercentage(investment));
        investment.setLastUpdate(LocalDateTime.now());
        investment.setUpdatedAt(LocalDateTime.now());
    }

    private static BigDecimal calculateTotalInvested(Integer quantity, BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal calculateProfitAmount(VariableInvestment investment) {
        return investment.getCurrentTotal()
                .subtract(investment.getTotalInvested())
                .setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal calculateProfitPercentage(VariableInvestment investment) {
        if (investment.getTotalInvested().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return investment.getProfitAmount()
                .multiply(BigDecimal.valueOf(100))
                .divide(investment.getTotalInvested(), 2, RoundingMode.HALF_UP);
    }
} 
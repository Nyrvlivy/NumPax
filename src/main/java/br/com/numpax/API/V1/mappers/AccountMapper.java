package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.AccountDTO;
import br.com.numpax.application.enums.InvestmentSubtype;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.CheckingAccount;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.SavingsAccount;
import java.math.BigDecimal;

public class AccountMapper {

    public static Account toEntity(AccountDTO dto) {
        Account account = switch (dto.getAccountType()) {
            case CHECKING -> new CheckingAccount(
                dto.getName(),
                dto.getDescription(),
                dto.getAccountType(),
                dto.getUserId(),
                dto.getBankName() != null ? dto.getBankName() : "",
                dto.getAgency() != null ? dto.getAgency() : "",
                dto.getAccountNumber() != null ? dto.getAccountNumber() : ""
            );
            case SAVINGS -> new SavingsAccount(
                dto.getName(),
                dto.getDescription(),
                dto.getUserId(),
                dto.getNearestDeadline(),
                dto.getFurthestDeadline(),
                dto.getLatestDeadline(),
                dto.getAverageTaxRate(),
                dto.getNumberOfFixedInvestments(),
                dto.getTotalMaturityAmount(),
                dto.getTotalDepositAmount()
            );
            case INVESTMENT -> new InvestmentAccount(
                dto.getName(),
                dto.getDescription(),
                dto.getUserId(),
                dto.getInvestmentSubtype() != null ? dto.getInvestmentSubtype() : InvestmentSubtype.OTHER
            );
            case GOAL -> new GoalAccount(
                dto.getName(),
                dto.getDescription(),
                dto.getAccountType(),
                dto.getUserId(),
                dto.getTargetValue(),
                dto.getTargetTaxRate(),
                dto.getMonthlyTaxRate(),
                dto.getMonthlyEstimate(),
                dto.getMonthlyAchievement(),
                dto.getCategory(),
                dto.getTargetDate(),
                dto.getStartDate(),
                dto.getEndDate()
            );
            case RELATED -> new RelatedAccounts(
                dto.getName(),
                dto.getDescription(),
                dto.getAccountType(),
                dto.getUserId()
            );
            default -> throw new IllegalArgumentException("Tipo de conta não suportado: " + dto.getAccountType());
        };

        if (dto.getId() != null) {
            account.setId(dto.getId());
        }
        if (dto.getBalance() != null) {
            account.setBalance(dto.getBalance());
        }
        account.setActive(dto.getIsActive());
        if (dto.getCreatedAt() != null) {
            account.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            account.setUpdatedAt(dto.getUpdatedAt());
        }
        
        return account;
    }

    public static AccountDTO toDTO(Account account) {
        AccountDTO.AccountDTOBuilder builder = AccountDTO.builder()
            .id(account.getId())
            .name(account.getName())
            .description(account.getDescription())
            .balance(account.getBalance())
            .accountType(account.getAccountType())
            .isActive(account.isActive())
            .createdAt(account.getCreatedAt())
            .updatedAt(account.getUpdatedAt())
            .userId(account.getUserId());

        // Adicionar campos específicos baseado no tipo de conta
        if (account instanceof CheckingAccount checkingAccount) {
            builder.bankName(checkingAccount.getBankName())
                  .agency(checkingAccount.getAgency())
                  .accountNumber(checkingAccount.getAccountNumber());
        } else if (account instanceof SavingsAccount savingsAccount) {
            builder.nearestDeadline(savingsAccount.getNearestDeadline())
                  .furthestDeadline(savingsAccount.getFurthestDeadline())
                  .latestDeadline(savingsAccount.getLatestDeadline())
                  .averageTaxRate(savingsAccount.getAverageTaxRate())
                  .numberOfFixedInvestments(savingsAccount.getNumberOfFixedInvestments())
                  .totalMaturityAmount(savingsAccount.getTotalMaturityAmount())
                  .totalDepositAmount(savingsAccount.getTotalDepositAmount());
        } else if (account instanceof InvestmentAccount investmentAccount) {
            builder.investmentSubtype(investmentAccount.getInvestmentSubtype());
        } else if (account instanceof GoalAccount goalAccount) {
            builder.targetValue(goalAccount.getTargetValue())
                  .targetTaxRate(goalAccount.getTargetTaxRate())
                  .monthlyTaxRate(goalAccount.getMonthlyTaxRate())
                  .monthlyEstimate(goalAccount.getMonthlyEstimate())
                  .monthlyAchievement(goalAccount.getMonthlyAchievement())
                  .category(goalAccount.getCategory())
                  .targetDate(goalAccount.getTargetDate())
                  .startDate(goalAccount.getStartDate())
                  .endDate(goalAccount.getEndDate());
        }

        return builder.build();
    }
}

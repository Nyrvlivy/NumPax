package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.FixedInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.FixedInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.request.FixedInvestmentRedeemRequestDTO;
import br.com.numpax.API.V1.dto.response.FixedInvestmentResponseDTO;
import br.com.numpax.API.V1.exceptions.InvestmentNotFoundException;
import br.com.numpax.API.V1.mappers.FixedInvestmentMapper;
import br.com.numpax.application.services.FixedInvestmentService;
import br.com.numpax.infrastructure.entities.FixedInvestment;
import br.com.numpax.infrastructure.repositories.FixedInvestmentRepository;
import br.com.numpax.infrastructure.transaction.TransactionManager;
import br.com.numpax.utils.ValidatorUtil;

import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class FixedInvestmentServiceImpl implements FixedInvestmentService {

    private final FixedInvestmentRepository repository;
    private final TransactionManager transactionManager;
    private final UserService userService;

    @Inject
    public FixedInvestmentServiceImpl(
            FixedInvestmentRepository repository,
            TransactionManager transactionManager,
            UserService userService) {
        this.repository = repository;
        this.transactionManager = transactionManager;
        this.userService = userService;
    }

    @Override
    public FixedInvestmentResponseDTO createInvestment(FixedInvestmentRequestDTO dto, String userId) {
        try {
            ValidatorUtil.validate(dto);
            
            userService.validateUserExists(userId);
            
            transactionManager.beginTransaction();
            
            FixedInvestment investment = FixedInvestmentMapper.toEntity(dto);
            investment.setUserId(userId);
            investment.setCreatedAt(LocalDateTime.now());
            investment.setUpdatedAt(LocalDateTime.now());
            
            repository.create(investment);
            
            transactionManager.commitTransaction();
            return FixedInvestmentMapper.toResponseDTO(investment);
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public FixedInvestmentResponseDTO getInvestmentById(String investmentId) {
        FixedInvestment investment = findInvestmentOrThrow(investmentId);
        return FixedInvestmentMapper.toResponseDTO(investment);
    }

    @Override
    public FixedInvestmentResponseDTO updateInvestment(String investmentId, FixedInvestmentUpdateRequestDTO dto) {
        try {
            ValidatorUtil.validate(dto);
            
            transactionManager.beginTransaction();
            
            FixedInvestment investment = findInvestmentOrThrow(investmentId);

            // Atualiza campos
            if (dto.getCode() != null) investment.setCode(dto.getCode());
            if (dto.getName() != null) investment.setName(dto.getName());
            if (dto.getAmount() != null) investment.setAmount(dto.getAmount());
            if (dto.getCategoryId() != null) investment.setCategoryId(dto.getCategoryId());
            if (dto.getNote() != null) investment.setNote(dto.getNote());
            if (dto.getFixedInvestmentType() != null) 
                investment.setFixedInvestmentType(FixedInvestmentType.valueOf(dto.getFixedInvestmentType()));
            if (dto.getInvestmentDate() != null) investment.setInvestmentDate(dto.getInvestmentDate());
            if (dto.getExpirationDate() != null) investment.setExpirationDate(dto.getExpirationDate());
            if (dto.getInstitution() != null) investment.setInstitution(dto.getInstitution());
            if (dto.getRedeemValue() != null) investment.setRedeemValue(dto.getRedeemValue());
            if (dto.getRedeemDate() != null) investment.setRedeemDate(dto.getRedeemDate());
            if (dto.getLiquidityPeriod() != null) investment.setLiquidityPeriod(dto.getLiquidityPeriod());

            investment.setUpdatedAt(LocalDateTime.now());
            repository.update(investment);
            
            transactionManager.commitTransaction();
            return FixedInvestmentMapper.toResponseDTO(investment);
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public void deleteInvestment(String investmentId) {
        try {
            transactionManager.beginTransaction();
            
            FixedInvestment investment = findInvestmentOrThrow(investmentId);
            
            // Verifica se o investimento já foi resgatado
            if (investment.getRedeemDate() != null) {
                throw new IllegalStateException("Cannot delete a redeemed investment");
            }
            
            // Verifica se o investimento está vencido
            if (investment.getExpirationDate() != null && 
                investment.getExpirationDate().isBefore(LocalDate.now())) {
                throw new IllegalStateException("Cannot delete a matured investment");
            }
            
            repository.delete(investmentId);
            
            transactionManager.commitTransaction();
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public void redeemInvestment(String investmentId, FixedInvestmentRedeemRequestDTO dto) {
        try {
            ValidatorUtil.validate(dto);
            
            transactionManager.beginTransaction();
            
            FixedInvestment investment = findInvestmentOrThrow(investmentId);
            
            if (investment.getRedeemDate() != null) {
                throw new IllegalStateException("Investment already redeemed");
            }
            
            investment.setRedeemDate(dto.getRedeemDate());
            investment.setRedeemValue(dto.getRedeemValue());
            investment.setNote(dto.getNote());
            investment.setUpdatedAt(LocalDateTime.now());
            
            repository.update(investment);
            
            transactionManager.commitTransaction();
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public List<FixedInvestmentResponseDTO> findByInvestmentAccountId(String accountId) {
        try {
            transactionManager.beginTransaction();
            
            List<FixedInvestmentResponseDTO> investments = repository.findByAccountId(accountId)
                    .stream()
                    .map(FixedInvestmentMapper::toResponseDTO)
                    .collect(Collectors.toList());
            
            transactionManager.commitTransaction();
            return investments;
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public List<FixedInvestmentResponseDTO> findByMaturityDateRange(LocalDate startDate, LocalDate endDate) {
        return repository.findByMaturityDateRange(startDate, endDate).stream()
                .map(FixedInvestmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findRedeemedInvestments() {
        return repository.findAll().stream()
                .filter(investment -> investment.getRedeemDate() != null)
                .map(FixedInvestmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findActiveInvestments() {
        return repository.findAll().stream()
                .filter(investment -> 
                    investment.getRedeemDate() == null && 
                    (investment.getExpirationDate() == null || 
                     investment.getExpirationDate().isAfter(LocalDate.now())))
                .map(FixedInvestmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findMaturedInvestments() {
        return repository.findAll().stream()
                .filter(investment -> 
                    investment.getExpirationDate() != null && 
                    (investment.getExpirationDate().isBefore(LocalDate.now()) || 
                     investment.getExpirationDate().isEqual(LocalDate.now())))
                .map(FixedInvestmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculateCurrentEarnings(String investmentId) {
        try {
            transactionManager.beginTransaction();
            FixedInvestment investment = findInvestmentOrThrow(investmentId);
            
            BigDecimal amount = investment.getAmount();
            BigDecimal rate = investment.getInterestRate();
            LocalDate startDate = investment.getInvestmentDate();
            LocalDate currentDate = LocalDate.now();
            
            long months = ChronoUnit.MONTHS.between(startDate, currentDate);
            BigDecimal earnings = amount.multiply(rate.divide(BigDecimal.valueOf(100)))
                                      .multiply(BigDecimal.valueOf(months))
                                      .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
            
            transactionManager.commitTransaction();
            return earnings;
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public BigDecimal calculateProjectedValue(String investmentId) {
        try {
            transactionManager.beginTransaction();
            FixedInvestment investment = findInvestmentOrThrow(investmentId);
            
            BigDecimal amount = investment.getAmount();
            BigDecimal rate = investment.getInterestRate();
            LocalDate startDate = investment.getInvestmentDate();
            LocalDate endDate = investment.getExpirationDate();
            
            long months = ChronoUnit.MONTHS.between(startDate, endDate);
            BigDecimal projectedValue = amount.multiply(
                BigDecimal.ONE.add(
                    rate.divide(BigDecimal.valueOf(100))
                        .multiply(BigDecimal.valueOf(months))
                        .divide(BigDecimal.valueOf(12), 8, RoundingMode.HALF_UP)
                )
            );
            
            transactionManager.commitTransaction();
            return projectedValue.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public BigDecimal simulateRedemption(String investmentId, LocalDate redeemDate) {
        try {
            transactionManager.beginTransaction();
            FixedInvestment investment = findInvestmentOrThrow(investmentId);
            
            if (redeemDate.isBefore(investment.getInvestmentDate())) {
                throw new IllegalArgumentException("Redemption date cannot be before investment date");
            }
            
            BigDecimal amount = investment.getAmount();
            BigDecimal rate = investment.getInterestRate();
            LocalDate startDate = investment.getInvestmentDate();
            
            long months = ChronoUnit.MONTHS.between(startDate, redeemDate);
            BigDecimal earnings = amount.multiply(rate.divide(BigDecimal.valueOf(100)))
                                      .multiply(BigDecimal.valueOf(months))
                                      .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
            
            // Aplica penalidade por resgate antecipado se aplicável
            if (redeemDate.isBefore(investment.getExpirationDate())) {
                earnings = earnings.multiply(BigDecimal.valueOf(0.85)); // 15% de penalidade
            }
            
            transactionManager.commitTransaction();
            return amount.add(earnings).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    private FixedInvestment findInvestmentOrThrow(String investmentId) {
        return repository.findById(investmentId)
                .orElseThrow(() -> new InvestmentNotFoundException("Fixed investment not found: " + investmentId));
    }
} 
package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.FixedInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.FixedInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.FixedInvestmentResponseDTO;
import br.com.numpax.API.V1.exceptions.InvestmentNotFoundException;
import br.com.numpax.API.V1.mappers.FixedInvestmentMapper;
import br.com.numpax.application.services.FixedInvestmentService;
import br.com.numpax.infrastructure.entities.FixedInvestment;
import br.com.numpax.infrastructure.repositories.FixedInvestmentRepository;
import br.com.numpax.infrastructure.transaction.TransactionManager;

import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FixedInvestmentServiceImpl implements FixedInvestmentService {

    @Inject
    private FixedInvestmentRepository repository;
    
    @Inject
    private TransactionManager transactionManager;

    @Override
    public FixedInvestmentResponseDTO createInvestment(FixedInvestmentRequestDTO dto) {
        try {
            transactionManager.beginTransaction();
            
            FixedInvestment investment = FixedInvestmentMapper.toEntity(dto);
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
            transactionManager.beginTransaction();
            
            FixedInvestment investment = findInvestmentOrThrow(investmentId);

            // Atualiza campos da Transaction
            if (dto.getCode() != null) investment.setCode(dto.getCode());
            if (dto.getName() != null) investment.setName(dto.getName());
            if (dto.getDescription() != null) investment.setDescription(dto.getDescription());
            if (dto.getAmount() != null) investment.setAmount(dto.getAmount());
            if (dto.getCategoryId() != null) investment.setCategoryId(dto.getCategoryId());
            if (dto.getNote() != null) investment.setNote(dto.getNote());

            // Atualiza campos do FixedInvestment
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
            
            if (!repository.existsById(investmentId)) {
                throw new InvestmentNotFoundException("Investment not found: " + investmentId);
            }
            
            repository.delete(investmentId);
            
            transactionManager.commitTransaction();
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public void redeemInvestment(String investmentId, LocalDate redemptionDate, BigDecimal redemptionAmount) {
        try {
            transactionManager.beginTransaction();
            
            FixedInvestment investment = findInvestmentOrThrow(investmentId);
            
            if (investment.getRedeemDate() != null) {
                throw new IllegalStateException("Investment is already redeemed");
            }

            repository.redeemInvestment(investmentId, redemptionDate, redemptionAmount);
            
            transactionManager.commitTransaction();
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public List<FixedInvestmentResponseDTO> findByInvestmentAccountId(String accountId) {
        return repository.findByInvestmentAccountId(accountId).stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findByMaturityDateRange(LocalDate startDate, LocalDate endDate) {
        return repository.findByMaturityDateRange(startDate, endDate).stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findRedeemedInvestments() {
        return repository.findRedeemedInvestments().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findActiveInvestments() {
        return repository.findActiveInvestments().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findMaturedInvestments() {
        return repository.findMaturedInvestments().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    private FixedInvestment findInvestmentOrThrow(String investmentId) {
        return repository.findById(investmentId)
            .orElseThrow(() -> new InvestmentNotFoundException("Investment not found: " + investmentId));
    }
} 
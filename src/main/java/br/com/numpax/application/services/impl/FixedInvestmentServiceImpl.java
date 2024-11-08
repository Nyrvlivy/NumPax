package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.FixedInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.FixedInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.FixedInvestmentResponseDTO;
import br.com.numpax.API.V1.exceptions.InvestmentAccountNotFoundException;
import br.com.numpax.API.V1.exceptions.InvestmentNotFoundException;
import br.com.numpax.API.V1.mappers.FixedInvestmentMapper;
import br.com.numpax.application.services.FixedInvestmentService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.FixedInvestment;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.repositories.FixedInvestmentRepository;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FixedInvestmentServiceImpl implements FixedInvestmentService {

    private final FixedInvestmentRepository fixedInvestmentRepository;
    private final InvestmentAccountRepository investmentAccountRepository;

    public FixedInvestmentServiceImpl(FixedInvestmentRepository fixedInvestmentRepository,
                                    InvestmentAccountRepository investmentAccountRepository) {
        this.fixedInvestmentRepository = fixedInvestmentRepository;
        this.investmentAccountRepository = investmentAccountRepository;
    }

    @Override
    public FixedInvestmentResponseDTO createInvestment(FixedInvestmentRequestDTO dto) {
        ValidatorUtil.validate(dto);

        InvestmentAccount account = investmentAccountRepository.findById(dto.getInvestmentAccountId())
            .orElseThrow(() -> new InvestmentAccountNotFoundException("Investment account not found: " + dto.getInvestmentAccountId()));

        FixedInvestment investment = FixedInvestmentMapper.toEntity(dto, account);
        fixedInvestmentRepository.create(investment);

        return FixedInvestmentMapper.toResponseDTO(investment);
    }

    @Override
    public FixedInvestmentResponseDTO getInvestmentById(String investmentId) {
        FixedInvestment investment = findInvestmentOrThrow(investmentId);
        return FixedInvestmentMapper.toResponseDTO(investment);
    }

    @Override
    public FixedInvestmentResponseDTO updateInvestment(String investmentId, FixedInvestmentUpdateRequestDTO dto) {
        ValidatorUtil.validate(dto);

        FixedInvestment investment = findInvestmentOrThrow(investmentId);

        if (dto.getName() != null) investment.setName(dto.getName());
        if (dto.getDescription() != null) investment.setDescription(dto.getDescription());
        if (dto.getInvestmentType() != null) investment.setInvestmentType(dto.getInvestmentType());
        
        if (dto.getInvestmentAccountId() != null) {
            InvestmentAccount account = investmentAccountRepository.findById(dto.getInvestmentAccountId())
                .orElseThrow(() -> new InvestmentAccountNotFoundException("Investment account not found: " + dto.getInvestmentAccountId()));
            investment.setInvestmentAccount(account);
        }

        if (dto.getInvestmentAmount() != null) investment.setInvestmentAmount(dto.getInvestmentAmount());
        if (dto.getTaxRate() != null) investment.setTaxRate(dto.getTaxRate());
        if (dto.getInvestmentDate() != null) investment.setInvestmentDate(dto.getInvestmentDate());
        if (dto.getMaturityDate() != null) investment.setMaturityDate(dto.getMaturityDate());
        if (dto.getExpectedReturn() != null) investment.setExpectedReturn(dto.getExpectedReturn());
        if (dto.getBroker() != null) investment.setBroker(dto.getBroker());
        if (dto.getInstitution() != null) investment.setInstitution(dto.getInstitution());
        if (dto.getNote() != null) investment.setNote(dto.getNote());
        
        if (dto.getIsRedeemed() != null) {
            investment.setRedeemed(dto.getIsRedeemed());
            if (dto.getIsRedeemed()) {
                investment.setRedemptionDate(dto.getRedemptionDate() != null ? dto.getRedemptionDate() : LocalDate.now());
                investment.setRedemptionAmount(dto.getRedemptionAmount());
            }
        }

        investment.setUpdatedAt(LocalDateTime.now());
        fixedInvestmentRepository.update(investment);

        return FixedInvestmentMapper.toResponseDTO(investment);
    }

    @Override
    public void redeemInvestment(String investmentId, LocalDate redemptionDate, double redemptionAmount) {
        FixedInvestment investment = findInvestmentOrThrow(investmentId);
        
        if (investment.isRedeemed()) {
            throw new IllegalStateException("Investment is already redeemed");
        }

        fixedInvestmentRepository.redeemInvestment(investmentId, redemptionDate, redemptionAmount);
    }

    @Override
    public void deactivateInvestment(String investmentId) {
        FixedInvestment investment = findInvestmentOrThrow(investmentId);
        investment.setActive(false);
        investment.setUpdatedAt(LocalDateTime.now());
        fixedInvestmentRepository.update(investment);
    }

    @Override
    public void deleteInvestment(String investmentId) {
        findInvestmentOrThrow(investmentId);
        fixedInvestmentRepository.delete(investmentId);
    }

    @Override
    public List<FixedInvestmentResponseDTO> listAllInvestments() {
        return fixedInvestmentRepository.findAll().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> listAllActiveInvestments() {
        return fixedInvestmentRepository.findAllActive().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> listAllInactiveInvestments() {
        return fixedInvestmentRepository.findAllInactive().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findByInvestmentAccountId(String accountId) {
        return fixedInvestmentRepository.findByInvestmentAccountId(accountId).stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findByMaturityDateRange(LocalDate startDate, LocalDate endDate) {
        return fixedInvestmentRepository.findByMaturityDateRange(startDate, endDate).stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findRedeemedInvestments() {
        return fixedInvestmentRepository.findRedeemedInvestments().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findActiveInvestments() {
        return fixedInvestmentRepository.findActiveInvestments().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<FixedInvestmentResponseDTO> findMaturedInvestments() {
        return fixedInvestmentRepository.findMaturedInvestments().stream()
            .map(FixedInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    private FixedInvestment findInvestmentOrThrow(String investmentId) {
        return fixedInvestmentRepository.findById(investmentId)
            .orElseThrow(() -> new InvestmentNotFoundException("Fixed investment not found: " + investmentId));
    }
} 
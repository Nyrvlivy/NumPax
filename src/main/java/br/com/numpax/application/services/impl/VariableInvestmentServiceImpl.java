package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.VariableInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.VariableInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.VariableInvestmentResponseDTO;
import br.com.numpax.API.V1.exceptions.InvestmentAccountNotFoundException;
import br.com.numpax.API.V1.exceptions.InvestmentNotFoundException;
import br.com.numpax.API.V1.mappers.VariableInvestmentMapper;
import br.com.numpax.application.services.VariableInvestmentService;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.VariableInvestment;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;
import br.com.numpax.infrastructure.repositories.VariableInvestmentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class VariableInvestmentServiceImpl implements VariableInvestmentService {

    private final VariableInvestmentRepository variableInvestmentRepository;
    private final InvestmentAccountRepository investmentAccountRepository;

    public VariableInvestmentServiceImpl(VariableInvestmentRepository variableInvestmentRepository,
                                       InvestmentAccountRepository investmentAccountRepository) {
        this.variableInvestmentRepository = variableInvestmentRepository;
        this.investmentAccountRepository = investmentAccountRepository;
    }

    @Override
    public VariableInvestmentResponseDTO createInvestment(VariableInvestmentRequestDTO dto) {
        ValidatorUtil.validate(dto);

        InvestmentAccount account = investmentAccountRepository.findById(dto.getInvestmentAccountId())
            .orElseThrow(() -> new InvestmentAccountNotFoundException("Investment account not found: " + dto.getInvestmentAccountId()));

        VariableInvestment investment = VariableInvestmentMapper.toEntity(dto, account);
        variableInvestmentRepository.create(investment);

        return VariableInvestmentMapper.toResponseDTO(investment);
    }

    @Override
    public VariableInvestmentResponseDTO getInvestmentById(String investmentId) {
        VariableInvestment investment = findInvestmentOrThrow(investmentId);
        return VariableInvestmentMapper.toResponseDTO(investment);
    }

    @Override
    public VariableInvestmentResponseDTO updateInvestment(String investmentId, VariableInvestmentUpdateRequestDTO dto) {
        ValidatorUtil.validate(dto);

        VariableInvestment investment = findInvestmentOrThrow(investmentId);

        if (dto.getName() != null) investment.setName(dto.getName());
        if (dto.getDescription() != null) investment.setDescription(dto.getDescription());
        if (dto.getInvestmentType() != null) investment.setInvestmentType(dto.getInvestmentType());
        
        if (dto.getInvestmentAccountId() != null) {
            InvestmentAccount account = investmentAccountRepository.findById(dto.getInvestmentAccountId())
                .orElseThrow(() -> new InvestmentAccountNotFoundException("Investment account not found: " + dto.getInvestmentAccountId()));
            investment.setInvestmentAccount(account);
        }

        if (dto.getTicker() != null) investment.setTicker(dto.getTicker().toUpperCase());
        if (dto.getQuantity() != null) investment.setQuantity(dto.getQuantity());
        if (dto.getPurchasePrice() != null) investment.setPurchasePrice(dto.getPurchasePrice());
        if (dto.getPurchaseDate() != null) investment.setPurchaseDate(dto.getPurchaseDate());
        if (dto.getSector() != null) investment.setSector(dto.getSector());
        if (dto.getBroker() != null) investment.setBroker(dto.getBroker());
        if (dto.getNote() != null) investment.setNote(dto.getNote());
        
        // Se houver atualização de preço, recalcula os valores
        if (dto.getCurrentPrice() != null) {
            VariableInvestmentMapper.updatePriceAndCalculations(investment, dto.getCurrentPrice());
        }

        investment.setUpdatedAt(LocalDateTime.now());
        variableInvestmentRepository.update(investment);

        return VariableInvestmentMapper.toResponseDTO(investment);
    }

    @Override
    public void updateCurrentPrice(String investmentId, BigDecimal newPrice) {
        VariableInvestment investment = findInvestmentOrThrow(investmentId);
        VariableInvestmentMapper.updatePriceAndCalculations(investment, newPrice);
        variableInvestmentRepository.update(investment);
    }

    @Override
    public void batchUpdatePrices(List<String> tickers, List<BigDecimal> prices) {
        if (tickers.size() != prices.size()) {
            throw new IllegalArgumentException("Tickers and prices lists must have the same size");
        }
    }

    @Override
    public List<VariableInvestmentResponseDTO> listAllInvestments() {
        return variableInvestmentRepository.findAll().stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> listAllActiveInvestments() {
        return variableInvestmentRepository.findAllActive().stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> listAllInactiveInvestments() {
        return variableInvestmentRepository.findAllInactive().stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> findByInvestmentAccountId(String accountId) {
        return variableInvestmentRepository.findByInvestmentAccountId(accountId).stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> findByTicker(String ticker) {
        return variableInvestmentRepository.findByTicker(ticker.toUpperCase()).stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> findBySector(String sector) {
        return variableInvestmentRepository.findBySector(sector).stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> findByPurchaseDateRange(LocalDate startDate, LocalDate endDate) {
        return variableInvestmentRepository.findByPurchaseDateRange(startDate, endDate).stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> findByProfitRange(BigDecimal minProfit, BigDecimal maxProfit) {
        return variableInvestmentRepository.findByProfitRange(minProfit, maxProfit).stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> findProfitableInvestments() {
        return variableInvestmentRepository.findProfitableInvestments().stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<VariableInvestmentResponseDTO> findUnprofitableInvestments() {
        return variableInvestmentRepository.findUnprofitableInvestments().stream()
            .map(VariableInvestmentMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    private VariableInvestment findInvestmentOrThrow(String investmentId) {
        return variableInvestmentRepository.findById(investmentId)
            .orElseThrow(() -> new InvestmentNotFoundException("Variable investment not found: " + investmentId));
    }
} 
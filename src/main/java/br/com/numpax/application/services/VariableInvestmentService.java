package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.VariableInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.VariableInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.VariableInvestmentResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VariableInvestmentService {
    
    VariableInvestmentResponseDTO createInvestment(VariableInvestmentRequestDTO dto);
    
    VariableInvestmentResponseDTO getInvestmentById(String investmentId);
    
    VariableInvestmentResponseDTO updateInvestment(String investmentId, VariableInvestmentUpdateRequestDTO dto);
    
    void updateCurrentPrice(String investmentId, BigDecimal newPrice);
    
    void batchUpdatePrices(List<String> tickers, List<BigDecimal> prices);
    
    void deactivateInvestment(String investmentId);
    
    void deleteInvestment(String investmentId);
    
    List<VariableInvestmentResponseDTO> listAllInvestments();
    
    List<VariableInvestmentResponseDTO> listAllActiveInvestments();
    
    List<VariableInvestmentResponseDTO> listAllInactiveInvestments();
    
    List<VariableInvestmentResponseDTO> findByInvestmentAccountId(String accountId);
    
    List<VariableInvestmentResponseDTO> findByTicker(String ticker);
    
    List<VariableInvestmentResponseDTO> findBySector(String sector);
    
    List<VariableInvestmentResponseDTO> findByPurchaseDateRange(LocalDate startDate, LocalDate endDate);
    
    List<VariableInvestmentResponseDTO> findByProfitRange(BigDecimal minProfit, BigDecimal maxProfit);
    
    List<VariableInvestmentResponseDTO> findProfitableInvestments();
    
    List<VariableInvestmentResponseDTO> findUnprofitableInvestments();
} 
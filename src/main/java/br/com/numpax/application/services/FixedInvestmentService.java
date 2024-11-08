package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.FixedInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.FixedInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.FixedInvestmentResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FixedInvestmentService {
    FixedInvestmentResponseDTO createInvestment(FixedInvestmentRequestDTO dto);
    FixedInvestmentResponseDTO getInvestmentById(String investmentId);
    FixedInvestmentResponseDTO updateInvestment(String investmentId, FixedInvestmentUpdateRequestDTO dto);
    void deleteInvestment(String investmentId);
    void redeemInvestment(String investmentId, LocalDate redemptionDate, BigDecimal redemptionAmount);
    List<FixedInvestmentResponseDTO> findByInvestmentAccountId(String accountId);
    List<FixedInvestmentResponseDTO> findByMaturityDateRange(LocalDate startDate, LocalDate endDate);
    List<FixedInvestmentResponseDTO> findRedeemedInvestments();
    List<FixedInvestmentResponseDTO> findActiveInvestments();
    List<FixedInvestmentResponseDTO> findMaturedInvestments();
} 
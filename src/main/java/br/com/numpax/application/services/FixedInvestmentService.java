package br.com.numpax.application.services;

import br.com.numpax.API.V1.dto.request.FixedInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.FixedInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.request.FixedInvestmentRedeemRequestDTO;
import br.com.numpax.API.V1.dto.response.FixedInvestmentResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FixedInvestmentService {
    FixedInvestmentResponseDTO createInvestment(FixedInvestmentRequestDTO dto, String userId);
    FixedInvestmentResponseDTO getInvestmentById(String investmentId);
    FixedInvestmentResponseDTO updateInvestment(String investmentId, FixedInvestmentUpdateRequestDTO dto);
    void deleteInvestment(String investmentId);
    void redeemInvestment(String investmentId, FixedInvestmentRedeemRequestDTO dto);
    List<FixedInvestmentResponseDTO> findByInvestmentAccountId(String accountId);
    List<FixedInvestmentResponseDTO> findByMaturityDateRange(LocalDate startDate, LocalDate endDate);
    List<FixedInvestmentResponseDTO> findRedeemedInvestments();
    List<FixedInvestmentResponseDTO> findActiveInvestments();
    List<FixedInvestmentResponseDTO> findMaturedInvestments();
    BigDecimal calculateCurrentEarnings(String investmentId);
    BigDecimal calculateProjectedValue(String investmentId);
    BigDecimal simulateRedemption(String investmentId, LocalDate redeemDate);
} 
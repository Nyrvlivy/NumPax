package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.FixedInvestment;

import java.time.LocalDate;
import java.util.List;

public interface FixedInvestmentRepository extends BaseRepository<FixedInvestment, String> {
    
    List<FixedInvestment> findByInvestmentAccountId(String accountId);
    
    List<FixedInvestment> findByMaturityDateRange(LocalDate startDate, LocalDate endDate);
    
    List<FixedInvestment> findRedeemedInvestments();
    
    List<FixedInvestment> findActiveInvestments();
    
    List<FixedInvestment> findMaturedInvestments();
    
    void redeemInvestment(String investmentId, LocalDate redemptionDate, double redemptionAmount);
} 
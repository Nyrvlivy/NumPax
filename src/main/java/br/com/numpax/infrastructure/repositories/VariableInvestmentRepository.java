package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.VariableInvestment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VariableInvestmentRepository extends BaseRepository<VariableInvestment, String> {
    
    List<VariableInvestment> findByInvestmentAccountId(String accountId);
    
    List<VariableInvestment> findByTicker(String ticker);
    
    List<VariableInvestment> findBySector(String sector);
    
    List<VariableInvestment> findByPurchaseDateRange(LocalDate startDate, LocalDate endDate);
    
    List<VariableInvestment> findByProfitRange(BigDecimal minProfit, BigDecimal maxProfit);
    
    List<VariableInvestment> findProfitableInvestments();
    
    List<VariableInvestment> findUnprofitableInvestments();
    
    void updateCurrentPrice(String investmentId, BigDecimal newPrice);
    
    void batchUpdatePrices(List<String> tickers, List<BigDecimal> prices);
    
    BigDecimal calculateTotalProfit(String accountId);
    BigDecimal calculateTotalCurrentAmount(String accountId);
    BigDecimal calculateTotalWithdrawnAmount(String accountId);
    BigDecimal calculateAveragePurchasePrice(String accountId);
    BigDecimal calculateTotalGainLoss(String accountId);
    int countWithdrawals(String accountId);
    int countEntries(String accountId);
    int countDistinctAssets(String accountId);
} 
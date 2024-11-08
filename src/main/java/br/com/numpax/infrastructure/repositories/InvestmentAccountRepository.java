package br.com.numpax.infrastructure.repositories;

import br.com.numpax.application.enums.RiskLevelType;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import java.util.List;
import java.util.Optional;

public interface InvestmentAccountRepository extends BaseRepository<InvestmentAccount, String> {
    
    List<InvestmentAccount> findByInstitution(String institution);
    
    List<InvestmentAccount> findAllActive();
    
    List<InvestmentAccount> findAllInactive();
    
    Optional<InvestmentAccount> findByAccountNumber(String accountNumber);
    
    Optional<InvestmentAccount> findByAgencyAndAccountNumber(String agency, String accountNumber);
    
    boolean existsByAccountNumber(String accountNumber);
    
    boolean existsByAgencyAndAccountNumber(String agency, String accountNumber);
    
    RiskLevelType findMostFrequentRiskLevel(String accountId);
}

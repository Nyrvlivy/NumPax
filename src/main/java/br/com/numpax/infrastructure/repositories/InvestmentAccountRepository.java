package br.com.numpax.infrastructure.repositories;

import br.com.numpax.infrastructure.entities.InvestmentAccount;
import java.util.List;

public interface InvestmentAccountRepository extends BaseRepository<InvestmentAccount, String> {

    List<InvestmentAccount> findByUserId(String userId);

}

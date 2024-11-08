package br.com.numpax.application.service;

import br.com.numpax.application.dto.request.CreateAccountRequestDTO;
import br.com.numpax.application.dto.request.CreateCheckingAccountRequestDTO;
import br.com.numpax.application.dto.request.CreateGoalAccountRequestDTO;
import br.com.numpax.application.dto.request.CreateInvestmentAccountRequestDTO;
import br.com.numpax.application.dto.request.CreateSavingsAccountRequestDTO;
import br.com.numpax.application.dto.response.AccountResponseDTO;
import br.com.numpax.application.entity.CheckingAccount;
import br.com.numpax.application.entity.GoalAccount;
import br.com.numpax.application.entity.InvestmentAccount;
import br.com.numpax.application.entity.SavingsAccount;
import br.com.numpax.application.mapper.CheckingAccountMapper;
import br.com.numpax.application.mapper.GoalAccountMapper;
import br.com.numpax.application.mapper.InvestmentAccountMapper;
import br.com.numpax.application.mapper.SavingsAccountMapper;
import br.com.numpax.application.user.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final CheckingAccountMapper checkingAccountMapper;
    private final SavingsAccountMapper savingsAccountMapper;
    private final GoalAccountMapper goalAccountMapper;
    private final InvestmentAccountMapper investmentAccountMapper;
    
    // Constructor injection...
    
    public AccountResponseDTO createAccount(CreateAccountRequestDTO dto, User user) {
        switch (dto.getAccountType()) {
            case CHECKING:
                CheckingAccount checkingAccount = checkingAccountMapper.toEntity((CreateCheckingAccountRequestDTO) dto, user);
                // Save account...
                return checkingAccountMapper.toDTO(checkingAccount);
            case SAVINGS:
                SavingsAccount savingsAccount = savingsAccountMapper.toEntity((CreateSavingsAccountRequestDTO) dto, user);
                // Save account...
                return savingsAccountMapper.toDTO(savingsAccount);
            case GOAL:
                GoalAccount goalAccount = goalAccountMapper.toEntity((CreateGoalAccountRequestDTO) dto, user);
                // Save account...
                return goalAccountMapper.toDTO(goalAccount);
            case INVESTMENT:
                InvestmentAccount investmentAccount = investmentAccountMapper.toEntity((CreateInvestmentAccountRequestDTO) dto, user);
                // Save account...
                return investmentAccountMapper.toDTO(investmentAccount);
            default:
                throw new IllegalArgumentException("Unsupported account type: " + dto.getAccountType());
        }
    }
} 
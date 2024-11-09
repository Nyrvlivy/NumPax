package br.com.numpax.application.services.impl;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.exceptions.TransactionNotFoundException;
import br.com.numpax.API.V1.mappers.TransactionMapper;
import br.com.numpax.application.services.FixedInvestmentService;
import br.com.numpax.infrastructure.entities.FixedInvestment;
import br.com.numpax.infrastructure.repositories.TransactionRepository;
import br.com.numpax.infrastructure.repositories.InvestmentAccountRepository;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.application.utils.ValidatorUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FixedInvestmentServiceImpl implements FixedInvestmentService {

    private final TransactionRepository transactionRepository;
    private final InvestmentAccountRepository investmentAccountRepository;

    public FixedInvestmentServiceImpl(
            TransactionRepository transactionRepository,
            InvestmentAccountRepository investmentAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.investmentAccountRepository = investmentAccountRepository;
    }

    @Override
    public TransactionResponseDTO createInvestment(TransactionRequestDTO dto) {
        ValidatorUtil.validate(dto);

        Account account = investmentAccountRepository.findById(dto.getAccountId())
            .orElseThrow(() -> new AccountNotFoundException("Conta de investimento não encontrada: " + dto.getAccountId()));

        FixedInvestment investment = new FixedInvestment();
        investment.setAccount(account);
        investment.setCode(dto.getCode());
        investment.setName(dto.getName());
        investment.setDescription(dto.getDescription());
        investment.setAmount(dto.getAmount());
        investment.setTransactionDate(dto.getTransactionDate());
        investment.setDate(); // Atualiza created_at e updated_at

        transactionRepository.create(investment);

        return TransactionMapper.toResponseDTO(investment);
    }

    @Override
    public TransactionResponseDTO findById(String investmentId) {
        FixedInvestment investment = (FixedInvestment) transactionRepository.findById(investmentId)
            .orElseThrow(() -> new TransactionNotFoundException("Investimento não encontrado: " + investmentId));
        return TransactionMapper.toResponseDTO(investment);
    }

    @Override
    public List<TransactionResponseDTO> findAllByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId).stream()
            .filter(transaction -> transaction instanceof FixedInvestment)
            .map(TransactionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TransactionResponseDTO update(String investmentId, TransactionRequestDTO dto) {
        ValidatorUtil.validate(dto);

        FixedInvestment investment = (FixedInvestment) transactionRepository.findById(investmentId)
            .orElseThrow(() -> new TransactionNotFoundException("Investimento não encontrado: " + investmentId));

        Account account = investmentAccountRepository.findById(dto.getAccountId())
            .orElseThrow(() -> new AccountNotFoundException("Conta de investimento não encontrada: " + dto.getAccountId()));

        investment.setAccount(account);
        investment.setCode(dto.getCode());
        investment.setName(dto.getName());
        investment.setDescription(dto.getDescription());
        investment.setAmount(dto.getAmount());
        investment.setTransactionDate(dto.getTransactionDate());
        investment.setDate(); // Atualiza updated_at

        transactionRepository.update(investment);

        return TransactionMapper.toResponseDTO(investment);
    }

    @Override
    public void redeem(String investmentId) {
        FixedInvestment investment = (FixedInvestment) transactionRepository.findById(investmentId)
            .orElseThrow(() -> new TransactionNotFoundException("Investimento não encontrado: " + investmentId));
            
        investment.setRedeemDate(LocalDateTime.now());
        investment.setDate(); // Atualiza updated_at
        
        transactionRepository.update(investment);
    }
} 
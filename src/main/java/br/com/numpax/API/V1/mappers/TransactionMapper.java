package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.Transaction;
import br.com.numpax.infrastructure.entities.CheckingAccount;
import br.com.numpax.infrastructure.entities.SavingsAccount;
import br.com.numpax.infrastructure.entities.InvestmentAccount;
import br.com.numpax.infrastructure.entities.GoalAccount;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionMapper {
    
    public static Transaction toEntity(TransactionRequestDTO dto, Account account, Category category) {
        ValidatorUtil.validate(dto);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setCode(dto.getCode());
        transaction.setName(dto.getName());
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setNatureOfTransaction(dto.getNatureOfTransaction());
        transaction.setReceiver(dto.getReceiver());
        transaction.setSender(dto.getSender());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setRepeatable(dto.isRepeatable());
        transaction.setRepeatableType(dto.getRepeatableType());
        transaction.setNote(dto.getNote());
        transaction.setActive(true);
        transaction.setEffective(false);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setAccount(account);
        transaction.setCategory(category);
        
        return transaction;
    }
    
    public static TransactionResponseDTO toResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setCode(transaction.getCode());
        dto.setName(transaction.getName());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setNatureOfTransaction(transaction.getNatureOfTransaction());
        dto.setReceiver(transaction.getReceiver());
        dto.setSender(transaction.getSender());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setRepeatable(transaction.isRepeatable());
        dto.setRepeatableType(transaction.getRepeatableType());
        dto.setNote(transaction.getNote());
        dto.setActive(transaction.isActive());
        dto.setEffective(transaction.isEffective());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());
        
        // Usando o mapper específico baseado no tipo da conta
        Account account = transaction.getAccount();
        if (account instanceof CheckingAccount) {
            dto.setAccount(CheckingAccountMapper.toResponseDTO((CheckingAccount) account));
        } else if (account instanceof SavingsAccount) {
            dto.setAccount(SavingsAccountMapper.toResponseDTO((SavingsAccount) account));
        } else if (account instanceof InvestmentAccount) {
            dto.setAccount(InvestmentAccountMapper.toResponseDTO((InvestmentAccount) account));
        } else if (account instanceof GoalAccount) {
            dto.setAccount(GoalAccountMapper.toResponseDTO((GoalAccount) account));
        }
        
        dto.setCategory(CategoryMapper.toResponseDTO(transaction.getCategory()));
        
        return dto;
    }
} 
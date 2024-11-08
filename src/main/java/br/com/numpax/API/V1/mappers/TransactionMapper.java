package br.com.numpax.API.V1.mappers;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.application.utils.ValidatorUtil;
import br.com.numpax.infrastructure.entities.Account;
import br.com.numpax.infrastructure.entities.Category;
import br.com.numpax.infrastructure.entities.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionMapper {

    public static Transaction toEntity(TransactionRequestDTO dto, Category category, Account account) {
        ValidatorUtil.validate(dto);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setCode(dto.getCode());
        transaction.setEffective(false);
        transaction.setName(dto.getName());
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setCategory(category);
        transaction.setAccount(account);
        transaction.setNatureOfTransaction(dto.getNatureOfTransaction());
        transaction.setReceiver(dto.getReceiver());
        transaction.setSender(dto.getSender());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setRepeatable(dto.isRepeatable());
        transaction.setRepeatableType(dto.getRepeatableType());
        transaction.setNote(dto.getNote());
        transaction.setActive(true);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        
        return transaction;
    }

    public static TransactionResponseDTO toResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setCode(transaction.getCode());
        dto.setEffective(transaction.isEffective());
        dto.setName(transaction.getName());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setCategoryId(transaction.getCategory().getId());
        dto.setAccountId(transaction.getAccount().getAccountId());
        dto.setNatureOfTransaction(transaction.getNatureOfTransaction());
        dto.setReceiver(transaction.getReceiver());
        dto.setSender(transaction.getSender());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setRepeatable(transaction.isRepeatable());
        dto.setRepeatableType(transaction.getRepeatableType());
        dto.setNote(transaction.getNote());
        dto.setActive(transaction.isActive());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());
        
        return dto;
    }
} 
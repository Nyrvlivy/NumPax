package br.com.numpax.domain.entities;

import br.com.numpax.domain.enums.CategoryType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Category {
    private final String id;                 // Identificador único
    private String name;                     // Nome da categoria
    private String description;              // Descrição da categoria
    private String icon;                     // Ícone da categoria
    private CategoryType categoryType;       // Tipo da categoria (Contas, Transações, Pessoal)
    private Boolean isActive;                // Ativa ou Inativa
    private Boolean isDefault;               // Padrão (criadas por nós) ou não (criadas pelo usuário)
    private LocalDateTime createdAt;         // Data de criação
    private LocalDateTime updatedAt;         // Data de atualização

    public Category(String name, String description, String icon, CategoryType categoryType, Boolean isDefault) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.categoryType = categoryType;
        this.isActive = true;
        this.isDefault = isDefault;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}

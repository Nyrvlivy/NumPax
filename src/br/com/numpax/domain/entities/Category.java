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
    private boolean isActive;                // Ativa ou Inativa
    private boolean isDefault;               // Padrão (criadas por nós) ou não (criadas pelo usuário)
    private final LocalDateTime createdAt;   // Data de criação
    private LocalDateTime updatedAt;         // Data de atualização

    public Category(String name, String description, String icon, CategoryType categoryType, boolean isDefault) {
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

    public String getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String getIcon() { return icon; }

    public void setIcon(String icon) {
        this.icon = icon;
        this.updatedAt = LocalDateTime.now();
    }

    public CategoryType getCategoryType() { return categoryType; }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) {
        this.isActive = active;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isDefault() { return isDefault; }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

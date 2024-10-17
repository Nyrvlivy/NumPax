package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.CategoryType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Category {
    private final String id;
    private String name;
    private String description;
    private String icon;
    private CategoryType categoryType;
    private boolean isActive;
    private boolean isDefault;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public Category(String id, String name, String description, String icon,
                    CategoryType categoryType, boolean isActive, boolean isDefault,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.categoryType = categoryType;
        this.isActive = isActive;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateTimestamp();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        updateTimestamp();
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
        updateTimestamp();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
        updateTimestamp();
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}

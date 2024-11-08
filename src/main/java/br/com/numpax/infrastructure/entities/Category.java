package br.com.numpax.infrastructure.entities;

import br.com.numpax.application.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Category {
    private String id;
    private String name;
    private String description;
    private String icon;
    private CategoryType categoryType;
    private boolean isActive;
    private boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Category() {
    }

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

    public void setIsDefault(boolean aDefault) {
    }

    public void setIsActive(boolean b) {

    }
}

package br.com.numpax.API.V1.dto.response;

import br.com.numpax.application.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryResponseDTO {

    private String id;
    private String name;
    private String description;
    private String icon;
    private CategoryType categoryType;
    private boolean isActive;
    private boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

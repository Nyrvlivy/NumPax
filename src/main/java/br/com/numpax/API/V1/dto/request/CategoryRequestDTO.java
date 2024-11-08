package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryRequestDTO {

    @NotBlank(message = "Name value is required.")
    @Size(max = 50, message = "Name value must be at most 50 characters.")
    private String name;

    @Size(max = 255, message = "Description value must be at most 255 characters.")
    private String description;

    @Size(max = 100, message = "Icon value must be at most 100 characters.")
    private String icon;

    @NotNull(message = "Category type value is required.")
    private CategoryType categoryType;

    private boolean isDefault;
}

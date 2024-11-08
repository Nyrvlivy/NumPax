package br.com.numpax.API.V1.dto.request;

import br.com.numpax.application.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckingAccountRequestDTO extends AccountRequestDTO {

    @NotBlank(message = "Bank code value is required.")
    @Pattern(regexp = "\\d{1,4}", message = "Bank code value must have between 1 and 4 digits.")
    private String bankCode;

    @NotBlank(message = "Agency value is required.")
    @Pattern(regexp = "\\d{1,4}", message = "Agency value must have between 1 and 4 digits.")
    private String agency;

    @NotBlank(message = "Account number value is required.")
    @Pattern(regexp = "\\d{1,12}", message = "Account number value must have between 1 and 12 digits.")
    private String accountNumber;

    public CheckingAccountRequestDTO() {
        this.setAccountType(AccountType.CHECKING);
    }
}

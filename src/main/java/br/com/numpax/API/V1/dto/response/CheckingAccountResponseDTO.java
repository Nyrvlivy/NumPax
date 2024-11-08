package br.com.numpax.API.V1.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckingAccountResponseDTO extends AccountResponseDTO {
    private String bankCode;
    private String agency;
    private String accountNumber;
}
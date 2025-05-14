package com.orizon.webdriver.domain.model.institution;

import com.orizon.webdriver.domain.model.institution.addressdata.ZipCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Address {
    private ZipCode zipcode;          // CEP (formato: "12345-678" ou "12345678")
    private String street;           // Nome da rua/av/alameda
    private String number;           // Número (String para casos como "12A" ou "S/N")
    private String neighborhood;     // Bairro
    private String city;             // Cidade
    private String state;            // Estado (sigla: "SP", "RJ", etc.)

    @Setter
    private String complement;       // Complemento (apartamento, bloco, etc.)
    @Setter
    private String country;          // País (padrão: "Brasil" ou código ISO)

    @Override
    public String toString() {
        return String.format(
                "%s, %s - %s, %s - %s, %s, %s",
                street,
                number,
                (complement != null ? complement : ""),
                neighborhood,
                city,
                state,
                zipcode.getFormattedZipCode()
        );
    }
}

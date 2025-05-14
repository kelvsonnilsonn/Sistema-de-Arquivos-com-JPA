package com.orizon.webdriver.domain.valueobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private ZipCode zipcode;          // CEP (formato: "12345-678" ou "12345678")
    private String street;           // Nome da rua/av/alameda
    private String number;           // Número (String para casos como "12A" ou "S/N")
    private String neighborhood;     // Bairro
    private String city;             // Cidade
    private String state;            // Estado (sigla: "SP", "RJ", etc.)

    private String complement;       // Complemento (apartamento, bloco, etc.)
    private String country;          // País (padrão: "Brasil" ou código ISO)

    public Address(String city, String state, String neighborhood, String street){
        this.city = city;
        this.state = state;
        this.neighborhood = neighborhood;
        this.street = street;
    }

    public void setZipCode(String zipcode) { this.zipcode = new ZipCode(zipcode);}

    @Override
    public String toString() {
        return String.format(
                """
                
                🌐 País: %s
                📍 Logradouro: %s, %s%s
                🏙️ Bairro: %s
                🏢 Cidade/Estado: %s/%s
                🏷️ CEP: %s
                """,
                (country != null ? country : "Brasil"),
                street,
                number,
                (complement != null && !complement.isBlank() ? " (" + complement + ")" : ""),
                neighborhood,
                city,
                state,
                zipcode != null ? zipcode.getFormattedZipCode() : "Não informado"
        );
    }
}

package model.InstitutionDataModel;

import model.InstitutionDataModel.AddressDataModel.ZipCode;

public class Address {
    private ZipCode zipcode;          // CEP (formato: "12345-678" ou "12345678")
    private String street;           // Nome da rua/av/alameda
    private String number;           // Número (String para casos como "12A" ou "S/N")
    private String complement;       // Complemento (apartamento, bloco, etc.)
    private String neighborhood;     // Bairro
    private String city;             // Cidade
    private String state;            // Estado (sigla: "SP", "RJ", etc.)
    private String country;          // País (padrão: "Brasil" ou código ISO)

    public Address(ZipCode zipcode, String street, String number, String neighborhood,
                   String city, String state) { // dados obrigatórios
        this.zipcode = zipcode;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = "Brasil"; // padrão
    }

    public void setComplement(String complement) { this.complement = complement; }
    public void changeAddressCountry(String country) { this.country = country; } // mudar o padrão

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

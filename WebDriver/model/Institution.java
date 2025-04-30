package model;

import model.InstitutionDataModel.Address;

public class Institution {
    private final int id;
    private String name;
    private String socialCause;
    private Address address;

    public Institution(int id, String name, String socialCause, Address address){
        this.id = id;
        this.name = name;
        this.socialCause = socialCause;
        this.address = address;
    }

    public int getInstitutionId() { return this.id; }
    public String getInstitutionName() { return this.name; }
    public String geTInstitutionSocialCause() { return this.socialCause; }
    public String getInstitutionAddress() { return this.address.toString(); }
}

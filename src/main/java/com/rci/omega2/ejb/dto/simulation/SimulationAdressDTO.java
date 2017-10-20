package com.rci.omega2.ejb.dto.simulation;

import java.util.Date;

import com.rci.omega2.ejb.dto.AddressTypeDTO;
import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;
import com.rci.omega2.ejb.dto.ResidenceTypeDTO;

public class SimulationAdressDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private String cep;
    private String number;
    private String address;
    private String complement;
    private String neighborhood;
    private String city;
    private ProvinceDTO province;
    private ResidenceTypeDTO residenceType;
    private Date residesInAddressSince;
    private AddressTypeDTO mailingAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }

    public ResidenceTypeDTO getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(ResidenceTypeDTO residenceType) {
        this.residenceType = residenceType;
    }

    public Date getResidesInAddressSince() {
        return residesInAddressSince == null ? null : new Date(residesInAddressSince.getTime());
    }

    public void setResidesInAddressSince(Date residesInAddressSince) {
        this.residesInAddressSince = residesInAddressSince == null ? null : new Date(residesInAddressSince.getTime());
    }

    public AddressTypeDTO getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(AddressTypeDTO mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

}

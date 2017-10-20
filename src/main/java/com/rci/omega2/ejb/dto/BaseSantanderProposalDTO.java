package com.rci.omega2.ejb.dto;

import com.rci.omega2.entity.enumeration.PersonTypeEnum;

public class BaseSantanderProposalDTO {

    private String clientCode;
    private String clientCodeRelashionship;
    private String numberPropostalAdp;
    private PersonTypeEnum personType;
    private String ocupation;
    
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientCodeRelashionship() {
        return clientCodeRelashionship;
    }

    public void setClientCodeRelashionship(String clientCodeRelashionship) {
        this.clientCodeRelashionship = clientCodeRelashionship;
    }

    public String getNumberPropostalAdp() {
        return numberPropostalAdp;
    }

    public void setNumberPropostalAdp(String numberPropostalAdp) {
        this.numberPropostalAdp = numberPropostalAdp;
    }

    public PersonTypeEnum getPersonType() {
        return personType;
    }

    public void setPersonType(PersonTypeEnum personType) {
        this.personType = personType;
    }

    public String getOcupation() {
        return ocupation;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation;
    }
    
}

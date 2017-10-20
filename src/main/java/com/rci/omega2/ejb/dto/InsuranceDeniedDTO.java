package com.rci.omega2.ejb.dto;

import java.util.Date;

public class InsuranceDeniedDTO extends BaseDTO{

    /**
     * 
     */
    private static final long serialVersionUID = 8368542450457900375L;    
    
    private String idDossier;
    private String adp;
    private String typePerson;
    private String nameClient;
    private Date dateCreationInit;
    private Date dateCreationEnd;
    private String saleTypeId;
    private String dealership;
    private Date dateExpirationInit;
    private Date dateExpirationEnd;
    
    public String getIdDossier() {
        return idDossier;
    }
    public void setIdDossier(String idDossier) {
        this.idDossier = idDossier;
    }
    public String getAdp() {
        return adp;
    }
    public void setAdp(String adp) {
        this.adp = adp;
    }
    public String getTypePerson() {
        return typePerson;
    }
    public void setTypePerson(String typePerson) {
        this.typePerson = typePerson;
    }
    public String getNameClient() {
        return nameClient;
    }
    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }
    public Date getDateCreationInit() {
        return dateCreationInit;
    }
    public void setDateCreationInit(Date dateCreationInit) {
        this.dateCreationInit = dateCreationInit;
    }
    public Date getDateCreationEnd() {
        return dateCreationEnd;
    }
    public void setDateCreationEnd(Date dateCreationEnd) {
        this.dateCreationEnd = dateCreationEnd;
    }
    public String getSaleTypeId() {
        return saleTypeId;
    }
    public void setSaleTypeId(String saleTypeId) {
        this.saleTypeId = saleTypeId;
    }
    public String getDealership() {
        return dealership;
    }
    public void setDealership(String dealership) {
        this.dealership = dealership;
    }
    public Date getDateExpirationInit() {
        return dateExpirationInit;
    }
    public void setDateExpirationInit(Date dateExpirationInit) {
        this.dateExpirationInit = dateExpirationInit;
    }
    public Date getDateExpirationEnd() {
        return dateExpirationEnd;
    }
    public void setDateExpirationEnd(Date dateExpirationEnd) {
        this.dateExpirationEnd = dateExpirationEnd;
    }

}

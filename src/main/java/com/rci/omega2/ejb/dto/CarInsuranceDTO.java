package com.rci.omega2.ejb.dto;

import java.util.Date;

import com.rci.omega2.entity.enumeration.PersonTypeEnum;

public class CarInsuranceDTO extends BaseDTO{

    /**serial version */
    private static final long serialVersionUID = 1L;
    
    private Long dossierId;
    
    private Long proposalId;
    
    private String adp;
   
    private String nameSalesman;
    
    private String cpfCnpj;
    
    private String statusRevaluation;
    
    private String vehicleModel;
    
    private String nameClient;
    
    private Date dateCreationInit;
    
    private Date dateCreationEnd;
    
    private String saleTypeId;
    
    private String dealership;
    
    private Date dateExpirationInit;
    
    private Date dateExpirationEnd;
    
    
    private PersonTypeEnum personType;
    
    
    public String getNameSalesman() {
        return nameSalesman;
    }

    public void setNameSalesman(String nameSalesman) {
        this.nameSalesman = nameSalesman;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getStatusRevaluation() {
        return statusRevaluation;
    }

    public void setStatusRevaluation(String statusRevaluation) {
        this.statusRevaluation = statusRevaluation;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
    
    public String getAdp() {
        return adp;
    }

    public void setAdp(String adp) {
        this.adp = adp;
    }

    public Long getDossierId() {
        return dossierId;
    }

    public void setDossierId(Long dossierId) {
        this.dossierId = dossierId;
    }

    
    public PersonTypeEnum getPersonType() {
        return personType;
    }

    public void setPersonType(PersonTypeEnum personType) {
        this.personType = personType;
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

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }
    
    
    

}

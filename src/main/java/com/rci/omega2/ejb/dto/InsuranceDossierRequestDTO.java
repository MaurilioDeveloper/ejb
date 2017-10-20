package com.rci.omega2.ejb.dto;

import java.util.Date;

public class InsuranceDossierRequestDTO extends BaseDTO{

    /**
     * 
     */
    private static final long serialVersionUID = 5546107024773570868L;
    
    private Long numberProposal;
    private Long proposal;
    private String adp;
    private String clientType;
    private String name;
    private String statusSeguro;
    private String saleTypeId;
    private Date dateCreationInit;
    private Date dateCreationEnd;
    private String dealership;
    private String salesMan;
    
     
  
    public Long getNumberProposal() {
        return numberProposal;
    }
    public void setNumberProposal(Long numberProposal) {
        this.numberProposal = numberProposal;
    }
    public Long getProposal() {
        return proposal;
    }
    public void setProposal(Long proposal) {
        this.proposal = proposal;
    }
    public String getAdp() {
        return adp;
    }
    public void setAdp(String adp) {
        this.adp = adp;
    }
    public String getClientType() {
        return clientType;
    }
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStatusSeguro() {
        return statusSeguro;
    }
    public void setStatusSeguro(String statusSeguro) {
        this.statusSeguro = statusSeguro;
    }
    public String getSaleTypeId() {
        return saleTypeId;
    }
    public void setSaleTypeId(String saleTypeId) {
        this.saleTypeId = saleTypeId;
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
    public String getDealership() {
        return dealership;
    }
    public void setDealership(String dealership) {
        this.dealership = dealership;
    }
    public String getSalesMan() {
        return salesMan;
    }
    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }

    
    
}

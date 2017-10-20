package com.rci.omega2.ejb.dto;

import java.util.Date;

public class InsuranceDossierSoldDTO extends BaseDTO{

    /**
     * 
     */
    private static final long serialVersionUID = 6419010227277360647L;
    
    private String numberProposal;
    private String saleTypeId;
    private String proposal;
    private String adp;
    private String cpf_cnpj;
    private String client;
    private Date date;
    private String salesMan;
    private String premium;
    private String statusSeguro;
    private String dossierId;
    
    public String getNumberProposal() {
        return numberProposal;
    }
    public void setNumberProposal(String numberProposal) {
        this.numberProposal = numberProposal;
    }
    public String getSaleTypeId() {
        return saleTypeId;
    }
    public void setSaleTypeId(String saleTypeId) {
        this.saleTypeId = saleTypeId;
    }
    public String getProposal() {
        return proposal;
    }
    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
    public String getAdp() {
        return adp;
    }
    public void setAdp(String adp) {
        this.adp = adp;
    }
    public String getCpf_cnpj() {
        return cpf_cnpj;
    }
    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getSalesMan() {
        return salesMan;
    }
    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }
    public String getPremium() {
        return premium;
    }
    public void setPremium(String premium) {
        this.premium = premium;
    }
    public String getStatusSeguro() {
        return statusSeguro;
    }
    public void setStatusSeguro(String statusSeguro) {
        this.statusSeguro = statusSeguro;
    }
    public String getDossierId() {
        return dossierId;
    }
    public void setDossierId(String dossierId) {
        this.dossierId = dossierId;
    }
    

}

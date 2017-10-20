package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;
import java.util.Date;

public class DossierDTO extends BaseDTO{

    private static final long serialVersionUID = 8892195633474441163L;
    private String nameSalesman;
    private String numDossier;
    private String adp;
    private String cpfCnpj;
    private String nameClient;
    private String status;
    private Date expirationDate;
    private String vehicleModel;
    private String proposalId;
    private String dossierId;
    private String saleType;
    private String productId;
    private BigDecimal financedAmount;
    private String salesmanId;
    private String structureId;
    private String bir;
    
    
    /**
     * @return the nameSalesman
     */
    public String getNameSalesman() {
        return nameSalesman;
    }
    /**
     * @param nameSalesman the nameSalesman to set
     */
    public void setNameSalesman(String nameSalesman) {
        this.nameSalesman = nameSalesman;
    }
    /**
     * @return the numDossier
     */
    public String getNumDossier() {
        return numDossier;
    }
    /**
     * @param numDossier the numDossier to set
     */
    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
    }
    /**
     * @return the adp
     */
    public String getAdp() {
        return adp;
    }
    /**
     * @param adp the adp to set
     */
    public void setAdp(String adp) {
        this.adp = adp;
    }
    /**
     * @return the cpfCnpj
     */
    public String getCpfCnpj() {
        return cpfCnpj;
    }
    /**
     * @param cpfCnpj the cpfCnpj to set
     */
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
    /**
     * @return the nameClient
     */
    public String getNameClient() {
        return nameClient;
    }
    /**
     * @param nameClient the nameClient to set
     */
    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate == null ? null : new Date(expirationDate.getTime());
    }
    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate == null ? null : new Date(expirationDate.getTime());
    }
    /**
     * @return the vehicleModel
     */
    public String getVehicleModel() {
        return vehicleModel;
    }
    /**
     * @param vehicleModel the vehicleModel to set
     */
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
    /**
     * @return the proposalId
     */
    public String getProposalId() {
        return proposalId;
    }
    /**
     * @param proposalId the proposalId to set
     */
    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }
    /**
     * @return the dossierId
     */
    public String getDossierId() {
        return dossierId;
    }
    /**
     * @param dossierId the dossierId to set
     */
    public void setDossierId(String dossierId) {
        this.dossierId = dossierId;
    }
    /**
     * @return the saleType
     */
    public String getSaleType() {
        return saleType;
    }
    /**
     * @param saleType the saleType to set
     */
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }
    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }
    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }
    /**
     * @return the financedAmount
     */
    public BigDecimal getFinancedAmount() {
        return financedAmount;
    }
    /**
     * @param financedAmount the financedAmount to set
     */
    public void setFinancedAmount(BigDecimal financedAmount) {
        this.financedAmount = financedAmount;
    }
    /**
     * @return the salesmanId
     */
    public String getSalesmanId() {
        return salesmanId;
    }
    /**
     * @param salesmanId the salesmanId to set
     */
    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }
    /**
     * @return the structureId
     */
    public String getStructureId() {
        return structureId;
    }
    /**
     * @param structureId the structureId to set
     */
    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }
    /**
     * @return the bir
     */
    public String getBir() {
        return bir;
    }
    /**
     * @param bir the bir to set
     */
    public void setBir(String bir) {
        this.bir = bir;
    }
    
}

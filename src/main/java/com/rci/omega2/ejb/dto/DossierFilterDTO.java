package com.rci.omega2.ejb.dto;

import java.util.Date;

public class DossierFilterDTO extends BaseDTO{
    
    private static final long serialVersionUID = -9159620015606656182L;
    private Long idDossier;
    private Long adp;
    private String typePerson;
    private String cpfCnpj;
    private String nameClient;
    private Long proposedStatus;
    private Date dateCreationInit;
    private Date dateCreationEnd;
    private Date dateExpirationInit;
    private Date dateExpirationEnd;
    private Long salesman;
    private Long dealership;
    private Long userId;
    private Boolean regionalView;
    private Long saleTypeId; 
    private Boolean taxTc;
    private String userType;
    

    /**
     * @return the idDossier
     */
    public Long getIdDossier() {
        return idDossier;
    }
    /**
     * @param idDossier the idDossier to set
     */
    public void setIdDossier(Long idDossier) {
        this.idDossier = idDossier;
    }
    /**
     * @return the adp
     */
    public Long getAdp() {
        return adp;
    }
    /**
     * @param adp the adp to set
     */
    public void setAdp(Long adp) {
        this.adp = adp;
    }
    /**
     * @return the typePerson
     */
    public String getTypePerson() {
        return typePerson;
    }
    /**
     * @param typePerson the typePerson to set
     */
    public void setTypePerson(String typePerson) {
        this.typePerson = typePerson;
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
     * @return the proposedStatus
     */
    public Long getProposedStatus() {
        return proposedStatus;
    }
    /**
     * @param proposedStatus the proposedStatus to set
     */
    public void setProposedStatus(Long proposedStatus) {
        this.proposedStatus = proposedStatus;
    }
    /**
     * @return the dateCreationInit
     */
    public Date getDateCreationInit() {
        return dateCreationInit == null ? null : new Date(dateCreationInit.getTime());
    }
    /**
     * @param dateCreationInit the dateCreationInit to set
     */
    public void setDateCreationInit(Date dateCreationInit) {
        this.dateCreationInit = dateCreationInit == null ? null : new Date(dateCreationInit.getTime());
    }
    /**
     * @return the dateCreationEnd
     */
    public Date getDateCreationEnd() {
        return dateCreationEnd == null ? null : new Date(dateCreationEnd.getTime());
    }
    /**
     * @param dateCreationEnd the dateCreationEnd to set
     */
    public void setDateCreationEnd(Date dateCreationEnd) {
        this.dateCreationEnd = dateCreationEnd == null ? null : new Date(dateCreationEnd.getTime());
    }
    /**
     * @return the dateExpirationInit
     */
    public Date getDateExpirationInit() {
        return dateExpirationInit == null ? null : new Date(dateExpirationInit.getTime());
    }
    /**
     * @param dateExpirationInit the dateExpirationInit to set
     */
    public void setDateExpirationInit(Date dateExpirationInit) {
        this.dateExpirationInit = dateExpirationInit == null ? null : new Date(dateExpirationInit.getTime());
    }
    /**
     * @return the dateExpirationEnd
     */
    public Date getDateExpirationEnd() {
        return dateExpirationEnd == null ? null : new Date(dateExpirationEnd.getTime());
    }
    /**
     * @param dateExpirationEnd the dateExpirationEnd to set
     */
    public void setDateExpirationEnd(Date dateExpirationEnd) {
        this.dateExpirationEnd = dateExpirationEnd == null ? null : new Date(dateExpirationEnd.getTime());
    }
    /**
     * @return the salesman
     */
    public Long getSalesman() {
        return salesman;
    }
    /**
     * @param salesman the salesman to set
     */
    public void setSalesman(Long salesman) {
        this.salesman = salesman;
    }
    /**
     * @return the dealership
     */
    public Long getDealership() {
        return dealership;
    }
    /**
     * @param dealership the dealership to set
     */
    public void setDealership(Long dealership) {
        this.dealership = dealership;
    }
    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    /**
     * @return the regionalView
     */
    public Boolean getRegionalView() {
        return regionalView;
    }
    /**
     * @param regionalView the regionalView to set
     */
    public void setRegionalView(Boolean regionalView) {
        this.regionalView = regionalView;
    }
    /**
     * @return the saleTypeId
     */
    public Long getSaleTypeId() {
        return saleTypeId;
    }
    /**
     * @param saleTypeId the saleTypeId to set
     */
    public void setSaleTypeId(Long saleTypeId) {
        this.saleTypeId = saleTypeId;
    }
    /**
     * @return the taxTc
     */
    public Boolean getTaxTc() {
        return taxTc;
    }
    /**
     * @param taxTc the taxTc to set
     */
    public void setTaxTc(Boolean taxTc) {
        this.taxTc = taxTc;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
}

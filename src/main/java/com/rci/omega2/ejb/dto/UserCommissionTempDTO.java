package com.rci.omega2.ejb.dto;

import java.util.Date;

public class UserCommissionTempDTO extends BaseDTO{
    
    private static final long serialVersionUID = -2951565245760372261L;
    private String id;
    private Date insertDate;
    private String userName;
    private String salesmanName;
    private String commission;
    private String financeType;
    private String saleType;
    private Date expireDate;
    private Long proposalId;
    private Boolean selected;
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the insertDate
     */
    public Date getInsertDate() {
        return insertDate == null ? null :  new Date(insertDate.getTime());
    }
    /**
     * @param insertDate the insertDate to set
     */
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate == null ? null : new Date(insertDate.getTime());
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the salesmanName
     */
    public String getSalesmanName() {
        return salesmanName;
    }
    /**
     * @param salesmanName the salesmanName to set
     */
    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }
    /**
     * @return the commission
     */
    public String getCommission() {
        return commission;
    }
    /**
     * @param commission the commission to set
     */
    public void setCommission(String commission) {
        this.commission = commission;
    }
    /**
     * @return the financeType
     */
    public String getFinanceType() {
        return financeType;
    }
    /**
     * @param financeType the financeType to set
     */
    public void setFinanceType(String financeType) {
        this.financeType = financeType;
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
     * @return the expireDate
     */
    public Date getExpireDate() {
        return expireDate == null ? null : new Date(expireDate.getTime());
    }
    /**
     * @param expireDate the expireDate to set
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate == null ? null : new Date(expireDate.getTime());
    }
    /**
     * @return the proposalId
     */
    public Long getProposalId() {
        return proposalId;
    }
    /**
     * @param proposalId the proposalId to set
     */
    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }
    public Boolean getSelected() {
        return selected;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
}

package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class ProductPromotionalDTO extends BaseDTO {
    
    private static final long serialVersionUID = -8697395598806256185L;
    
    private String commissionId;
    private Integer delayValue;
    private Integer term;
    private BigDecimal depositPercent;
    private Boolean mainSource;
    private String repackageId;
    
    public String getCommissionId() {
        return commissionId;
    }
    public void setCommissionId(String commissionId) {
        this.commissionId = commissionId;
    }
    public Integer getDelayValue() {
        return delayValue;
    }
    public void setDelayValue(Integer delayValue) {
        this.delayValue = delayValue;
    }
    public Integer getTerm() {
        return term;
    }
    public void setTerm(Integer term) {
        this.term = term;
    }
    public BigDecimal getDepositPercent() {
        return depositPercent;
    }
    public void setDepositPercent(BigDecimal depositPercent) {
        this.depositPercent = depositPercent;
    }
    public Boolean getMainSource() {
        return mainSource;
    }
    public void setMainSource(Boolean mainSource) {
        this.mainSource = mainSource;
    }
    public String getRepackageId() {
        return repackageId;
    }
    public void setRepackageId(String repackageId) {
        this.repackageId = repackageId;
    }
    
}

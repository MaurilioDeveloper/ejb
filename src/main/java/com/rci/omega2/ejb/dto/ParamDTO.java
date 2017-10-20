package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class ParamDTO extends BaseDTO{

    private static final long serialVersionUID = -1132509214019563026L;
    private String paramId;
    private Boolean paramBoolean;
    private String description;
    private Integer paramValue;
    private BigDecimal paramAmount;
    
    /**
     * @return the paramId
     */
    public String getParamId() {
        return paramId;
    }
    /**
     * @param paramId the paramId to set
     */
    public void setParamId(String paramId) {
        this.paramId = paramId;
    }
    /**
     * @return the paramBoolean
     */
    public Boolean getParamBoolean() {
        return paramBoolean;
    }
    /**
     * @param paramBoolean the paramBoolean to set
     */
    public void setParamBoolean(Boolean paramBoolean) {
        this.paramBoolean = paramBoolean;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getParamValue() {
        return paramValue;
    }
    public void setParamValue(Integer paramValue) {
        this.paramValue = paramValue;
    }
    public BigDecimal getParamAmount() {
        return paramAmount;
    }
    public void setParamAmount(BigDecimal paramAmount) {
        this.paramAmount = paramAmount;
    }

    
}

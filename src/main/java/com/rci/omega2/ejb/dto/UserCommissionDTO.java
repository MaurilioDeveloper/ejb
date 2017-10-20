package com.rci.omega2.ejb.dto;

public class UserCommissionDTO extends BaseDTO{
    
    private static final long serialVersionUID = 7145944057986338280L;
    private String commisonId;
    private String description;
    private Boolean temp;
    private Boolean selected;

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

    /**
     * @return the commisonId
     */
    public String getCommisonId() {
        return commisonId;
    }

    /**
     * @param commisonId the commisonId to set
     */
    public void setCommisonId(String commisonId) {
        this.commisonId = commisonId;
    }

    public Boolean getTemp() {
        return temp;
    }

    public void setTemp(Boolean temp) {
        this.temp = temp;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    
}

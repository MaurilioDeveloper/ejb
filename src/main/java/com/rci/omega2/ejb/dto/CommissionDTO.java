package com.rci.omega2.ejb.dto;

public class CommissionDTO extends BaseDTO{

    /**serial version */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String description;
    private Boolean temp;
    private Boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

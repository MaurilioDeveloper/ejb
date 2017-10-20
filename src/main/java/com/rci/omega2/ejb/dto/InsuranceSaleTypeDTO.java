package com.rci.omega2.ejb.dto;

public class InsuranceSaleTypeDTO extends BaseDTO{

    private static final long serialVersionUID = -2265956427757821644L;
    
    private String name;
    private String description;
    
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}

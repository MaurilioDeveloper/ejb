package com.rci.omega2.ejb.dto;


public class InsuranceStatusDTO extends BaseDTO{
    
    private static final long serialVersionUID = -6565173832203816154L;
    private String car_insurance_status_id;
    private String description;
    
    public String getCar_insurance_status_id() {
        return car_insurance_status_id;
    }
    
    public void setCar_insurance_status_id(String car_insurance_status_id) {
        this.car_insurance_status_id = car_insurance_status_id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    

    
    
}

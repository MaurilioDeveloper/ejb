package com.rci.omega2.ejb.dto;

public class CivilStateDTO extends BaseDTO{

    private static final long serialVersionUID = 1368963027800444133L;
    private String id;
    private Long value;
    private String description;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}

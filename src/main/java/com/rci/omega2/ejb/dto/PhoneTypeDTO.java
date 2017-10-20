package com.rci.omega2.ejb.dto;

public class PhoneTypeDTO extends BaseDTO{

    private static final long serialVersionUID = 1368963027800444133L;
    private int ordinal;
    private String description;
    private Integer importCode;
    private String name;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getImportCode() {
        return importCode;
    }

    public void setImportCode(Integer importCode) {
        this.importCode = importCode;
    }

}

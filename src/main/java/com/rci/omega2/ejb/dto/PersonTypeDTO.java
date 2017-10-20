package com.rci.omega2.ejb.dto;

public class PersonTypeDTO extends BaseDTO{

    private static final long serialVersionUID = 6012087978645069181L;
    private String name;
    private String description;
    private String abbreviation;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }
    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    
}

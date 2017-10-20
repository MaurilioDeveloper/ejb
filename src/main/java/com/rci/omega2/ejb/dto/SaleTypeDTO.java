package com.rci.omega2.ejb.dto;

public class SaleTypeDTO extends BaseDTO {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    private String id;
    private String description;
    private Boolean active;
    

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
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }
    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    
}

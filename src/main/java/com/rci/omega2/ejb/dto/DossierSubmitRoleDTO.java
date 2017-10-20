package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class DossierSubmitRoleDTO extends BaseDTO{
    
    private static final long serialVersionUID = 4531798657923678737L;
    private String userTypeId;
    private String description;
    private BigDecimal allowed;
    private String idStructure;
    
    /**
     * @return the userTypeId
     */
    public String getUserTypeId() {
        return userTypeId;
    }
    /**
     * @param userTypeId the userTypeId to set
     */
    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
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
     * @return the allowed
     */
    public BigDecimal getAllowed() {
        return allowed;
    }
    /**
     * @param allowed the allowed to set
     */
    public void setAllowed(BigDecimal allowed) {
        this.allowed = allowed;
    }
    /**
     * @return the idStructure
     */
    public String getIdStructure() {
        return idStructure;
    }
    /**
     * @param idStructure the idStructure to set
     */
    public void setIdStructure(String idStructure) {
        this.idStructure = idStructure;
    }
    
   
    
}

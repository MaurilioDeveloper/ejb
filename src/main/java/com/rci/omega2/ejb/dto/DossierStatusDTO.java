package com.rci.omega2.ejb.dto;

public class DossierStatusDTO extends BaseDTO{

    private static final long serialVersionUID = -7630872061942581824L;
    private String dossierStatusId;
    private String description;
    
    /**
     * @return the dossierStatusId
     */
    public String getDossierStatusId() {
        return dossierStatusId;
    }
    /**
     * @param dossierStatusId the dossierStatusId to set
     */
    public void setDossierStatusId(String dossierStatusId) {
        this.dossierStatusId = dossierStatusId;
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
    
    
}

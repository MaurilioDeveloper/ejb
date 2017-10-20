package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class UserDTO extends BaseDTO{
    
    private static final long serialVersionUID = 6058667530442606031L;
    private String userid;
    private String namePerson;
    private BigDecimal dossierSubmitAllowed;
    
    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }
    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }
    /**
     * @return the namePerson
     */
    public String getNamePerson() {
        return namePerson;
    }
    /**
     * @param namePerson the namePerson to set
     */
    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }
    /**
     * @return the dossierSubmitAllowed
     */
    public BigDecimal getDossierSubmitAllowed() {
        return dossierSubmitAllowed;
    }
    /**
     * @param dossierSubmitAllowed the dossierSubmitAllowed to set
     */
    public void setDossierSubmitAllowed(BigDecimal dossierSubmitAllowed) {
        this.dossierSubmitAllowed = dossierSubmitAllowed;
    }
    
    
    
}

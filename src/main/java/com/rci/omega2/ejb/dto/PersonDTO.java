package com.rci.omega2.ejb.dto;

public class PersonDTO extends BaseDTO{
    
    private static final long serialVersionUID = -7883926559280410909L;
    private String personId;
    private String userId;
    private String name;
    
    public String getPersonId() {
        return personId;
    }
    public void setPersonId(String personId) {
        this.personId = personId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    

}

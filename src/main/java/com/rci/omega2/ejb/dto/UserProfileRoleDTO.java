package com.rci.omega2.ejb.dto;

public class UserProfileRoleDTO extends BaseDTO{

    /**serial version  */
    private static final long serialVersionUID = 6051024176629288707L;
    
    private String id;
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

    public void setDescription(String description) {
        this.description = description;
    }

}

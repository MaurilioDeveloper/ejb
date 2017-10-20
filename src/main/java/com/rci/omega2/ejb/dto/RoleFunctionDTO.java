package com.rci.omega2.ejb.dto;

public class RoleFunctionDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3521950811899655503L;
    
    private String url;
    private String description;
    private String role;
    private String profile;
   
    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}

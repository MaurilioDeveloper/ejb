package com.rci.omega2.ejb.dto;

public class UserProfileFunctionDTO extends BaseDTO{

    /**serial version */
    private static final long serialVersionUID = 5607692145478214324L;
    
    private String id;
    private String description;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

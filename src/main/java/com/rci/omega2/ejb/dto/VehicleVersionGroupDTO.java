package com.rci.omega2.ejb.dto;

public class VehicleVersionGroupDTO extends BaseDTO {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    private String id;
    private String description;
    private String fipe;
    private String gender;
    private String url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String descritpion) {
        this.description = descritpion;
    }

    public String getFipe() {
        return fipe;
    }

    public void setFipe(String fipe) {
        this.fipe = fipe;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}

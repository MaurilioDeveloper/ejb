package com.rci.omega2.ejb.dto;

import java.util.List;

public class VehicleModelDTO extends BaseDTO {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String description;
    private List<String> url;
    private String vehicleType;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descrition) {
        this.description = descrition;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

}

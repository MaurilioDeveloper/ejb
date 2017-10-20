package com.rci.omega2.ejb.dto.simulation;

import com.rci.omega2.ejb.dto.BaseDTO;

public class CarDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;
    private String id;
    private String description;
    private String url;
    private boolean selected;
    private String gender;
    private String vehicleType;
    private SimulationVehicleVersionDTO version;

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public SimulationVehicleVersionDTO getVersion() {
        return version;
    }

    public void setVersion(SimulationVehicleVersionDTO version) {
        this.version = version;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

}

package com.rci.omega2.ejb.dto;

public class VehicleVersionDTO extends BaseDTO {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    private String versionId;
    private Integer yearModel;
    private String description;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String id) {
        this.versionId = id;
    }

    public Integer getYearModel() {
        return yearModel;
    }

    public void setYearModel(Integer yearModel) {
        this.yearModel = yearModel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

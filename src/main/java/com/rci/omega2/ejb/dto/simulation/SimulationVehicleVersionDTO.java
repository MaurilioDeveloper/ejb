package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;
import java.util.List;

import com.rci.omega2.ejb.dto.BaseDTO;

public class SimulationVehicleVersionDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private String description;
    private String fipe;
    private Integer yearManufacture;
    private Integer yearModel;
    private BigDecimal price;
    private List<VehicleOptionsDTO> options;
    private List<VehicleAccessoriesDTO> acessories;

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

    public String getFipe() {
        return fipe;
    }

    public void setFipe(String fipe) {
        this.fipe = fipe;
    }

    public Integer getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(Integer yearManufacture) {
        this.yearManufacture = yearManufacture;
    }

    public Integer getYearModel() {
        return yearModel;
    }

    public void setYearModel(Integer yearModel) {
        this.yearModel = yearModel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<VehicleOptionsDTO> getOptions() {
        return options;
    }

    public void setOptions(List<VehicleOptionsDTO> options) {
        this.options = options;
    }

    public List<VehicleAccessoriesDTO> getAcessories() {
        return acessories;
    }

    public void setAcessories(List<VehicleAccessoriesDTO> acessories) {
        this.acessories = acessories;
    }

}

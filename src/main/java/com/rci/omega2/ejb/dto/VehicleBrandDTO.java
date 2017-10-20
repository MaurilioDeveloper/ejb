package com.rci.omega2.ejb.dto;

public class VehicleBrandDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private String description; 
    private boolean showDirectSale;

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

    public boolean isShowDirectSale() {
        return showDirectSale;
    }

    public void setShowDirectSale(boolean showDirectSale) {
        this.showDirectSale = showDirectSale;
    }

}

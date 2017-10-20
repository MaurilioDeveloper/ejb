package com.rci.omega2.ejb.dto;

public class StructureDTO extends BaseDTO{

    private static final long serialVersionUID = 1368963027800444133L;
    private String structureId;
    private String byr;
    private String tab;
    private String description;
    private String brand;
    private String brandVehicle;
    private String financeBrandDescription;
    private String financeBrandImportCode;
    
    /**
     * @return the structureId
     */
    public String getStructureId() {
        return structureId;
    }
    /**
     * @param structureId the structureId to set
     */
    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the byr
     */
    public String getByr() {
        return byr;
    }
    /**
     * @param byr the byr to set
     */
    public void setByr(String byr) {
        this.byr = byr;
    }
    public String getTab() {
        return tab;
    }
    public void setTab(String tab) {
        this.tab = tab;
    }
    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }
    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getBrandVehicle() {
        return brandVehicle;
    }
    public void setBrandVehicle(String brandVehicle) {
        this.brandVehicle = brandVehicle;
    }
    public String getFinanceBrandDescription() {
        return financeBrandDescription;
    }
    public void setFinanceBrandDescription(String financeBrandDescription) {
        this.financeBrandDescription = financeBrandDescription;
    }
    public String getFinanceBrandImportCode() {
        return financeBrandImportCode;
    }
    public void setFinanceBrandImportCode(String financeBrandImportCode) {
        this.financeBrandImportCode = financeBrandImportCode;
    }
    
    
}

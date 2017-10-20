package com.rci.omega2.ejb.dto;

public class SantanderProposalStep5DTO extends BaseSantanderProposalDTO {

    private String warrantyCode;
    private String saleType;
    private String objectCode;
    private String brandCode;
    private String brandDescription;
    private String modelCode;
    private String modelDescription;
    private String chassis;
    private String stateCodeLicensing;
    private String renavam;
    private String color;
    private String originIndicative;
    private Integer manufactureYear;
    private Integer modelYear;
    private String fuelType;
    private boolean zeroKm;
    private boolean taxi;
    private boolean adapted;
    private String plaque;
    private String statePlaque;

    public String getWarrantyCode() {
        return warrantyCode;
    }

    public void setWarrantyCode(String warrantyCode) {
        this.warrantyCode = warrantyCode;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getStateCodeLicensing() {
        return stateCodeLicensing;
    }

    public void setStateCodeLicensing(String stateCodeLicensing) {
        this.stateCodeLicensing = stateCodeLicensing;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOriginIndicative() {
        return originIndicative;
    }

    public void setOriginIndicative(String originIndicative) {
        this.originIndicative = originIndicative;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isZeroKm() {
        return zeroKm;
    }

    public void setZeroKm(boolean zeroKm) {
        this.zeroKm = zeroKm;
    }

    public boolean isTaxi() {
        return taxi;
    }

    public void setTaxi(boolean taxi) {
        this.taxi = taxi;
    }

    public boolean isAdapted() {
        return adapted;
    }

    public void setAdapted(boolean adapted) {
        this.adapted = adapted;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public String getStatePlaque() {
        return statePlaque;
    }

    public void setStatePlaque(String statePlaque) {
        this.statePlaque = statePlaque;
    }

}

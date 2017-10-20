package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class ServiceDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = -782139779813058386L;

    private String id;
    private String description;
    private String descriptionType;
    private BigDecimal amount;
    private BigDecimal maxAmount;
    private BigDecimal minAmount;
    private BigDecimal percentage;
    private Boolean required;
    private Boolean selecetedDefault;
    private Boolean activeStructure;
    private String idServiceStructure;
    private String idStructure;
    private Long importCodeOmega;
    private Long serviceTypeId;
    private Boolean editable;
    
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }
    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    /**
     * @return the percentage
     */
    public BigDecimal getPercentage() {
        return percentage;
    }
    /**
     * @param percentage the percentage to set
     */
    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
    /**
     * @return the required
     */
    public Boolean getRequired() {
        return required;
    }
    /**
     * @param required the required to set
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }
    /**
     * @return the activeStructure
     */
    public Boolean getActiveStructure() {
        return activeStructure;
    }
    /**
     * @param activeStructure the activeStructure to set
     */
    public void setActiveStructure(Boolean activeStructure) {
        this.activeStructure = activeStructure;
    }
    /**
     * @return the idServiceStructure
     */
    public String getIdServiceStructure() {
        return idServiceStructure;
    }
    /**
     * @param idServiceStructure the idServiceStructure to set
     */
    public void setIdServiceStructure(String idServiceStructure) {
        this.idServiceStructure = idServiceStructure;
    }
    /**
     * @return the idStructure
     */
    public String getIdStructure() {
        return idStructure;
    }
    /**
     * @param idStructure the idStructure to set
     */
    public void setIdStructure(String idStructure) {
        this.idStructure = idStructure;
    }
    public String getDescriptionType() {
        return descriptionType;
    }
    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }
    public BigDecimal getMinAmount() {
        return minAmount;
    }
    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }
    public Boolean getSelecetedDefault() {
        return this.selecetedDefault;
    }
    public void setSelecetedDefault(Boolean selecetedDefault) {
        this.selecetedDefault = selecetedDefault;
    }
    /**
     * @return the importCodeOmega
     */
    public Long getImportCodeOmega() {
        return importCodeOmega;
    }
    /**
     * @param importCodeOmega the importCodeOmega to set
     */
    public void setImportCodeOmega(Long importCodeOmega) {
        this.importCodeOmega = importCodeOmega;
    }
    public Long getServiceTypeId() {
        return serviceTypeId;
    }
    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
    public Boolean getEditable() {
        return editable;
    }
    public void setEditable(Boolean editable) {
        this.editable = editable;
    }
    
}

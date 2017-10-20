package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;
/**
 * 
 * @author Ricardo Zandonai (rzandonai@stefanini.com) 
 *
 */
public class SantanderProposalStep1DTO {
    private String typeOfFinancing;
    private String simulationTable;
    private String modalityOfFinancing;
    private BigDecimal totalValue;
    private BigDecimal inputValue;
    private Integer parcelsNumber;
    private BigDecimal parcelsValue;
    private String currencyType;
    private BigDecimal insuranceValue;
    private String firstPaymentDate;
    private Boolean taxTac;
    private Boolean taxTab;
    private String vendorName;
    public String getTypeOfFinancing() {
        return typeOfFinancing;
    }

    public void setTypeOfFinancing(String typeOfFinancing) {
        this.typeOfFinancing = typeOfFinancing;
    }

    public String getSimulationTable() {
        return simulationTable;
    }

    public void setSimulationTable(String simulationTable) {
        this.simulationTable = simulationTable;
    }

    public String getModalityOfFinancing() {
        return modalityOfFinancing;
    }

    public void setModalityOfFinancing(String modalityOfFinancing) {
        this.modalityOfFinancing = modalityOfFinancing;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getInputValue() {
        return inputValue;
    }

    public void setInputValue(BigDecimal inputValue) {
        this.inputValue = inputValue;
    }

    public Integer getParcelsNumber() {
        return parcelsNumber;
    }

    public void setParcelsNumber(Integer parcelsNumber) {
        this.parcelsNumber = parcelsNumber;
    }

    public BigDecimal getParcelsValue() {
        return parcelsValue;
    }

    public void setParcelsValue(BigDecimal parcelsValue) {
        this.parcelsValue = parcelsValue;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getInsuranceValue() {
        return insuranceValue;
    }

    public void setInsuranceValue(BigDecimal insuranceValue) {
        this.insuranceValue = insuranceValue;
    }

    public String getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public void setFirstPaymentDate(String firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    public Boolean getTaxTac() {
        return taxTac;
    }

    public void setTaxTac(Boolean taxTac) {
        this.taxTac = taxTac;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Boolean getTaxTab() {
        return taxTab;
    }

    public void setTaxTab(Boolean taxTab) {
        this.taxTab = taxTab;
    }


    
}

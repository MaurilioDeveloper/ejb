package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;
import java.util.List;

import com.rci.omega2.ejb.dto.simulation.InstalmentDTO;
import com.rci.omega2.ejb.dto.simulation.ServiceDTO;

public class CalculateDTO {
    
    private BigDecimal totalAmount;
    private BigDecimal totalAmountFinanced;
    private BigDecimal instalmentAmount;
    private BigDecimal deposit;
    private BigDecimal depositPercent;
    private BigDecimal vehiclePrice;
    
    private List<InstalmentDTO> listInstalment;
    private List<InstalmentGroupDTO> listInstalmentGroup;
    private String coeffcientId;
    private BigDecimal taxCoefficient;
    private BigDecimal coefficient;
    
    private List<TaxDTO> taxes;
    private List<ServiceDTO> services;
    
    private Boolean depositRecalculate = Boolean.FALSE;
    private Boolean calculateOk = Boolean.FALSE;
    
    public BigDecimal getInstalmentAmount() {
        return instalmentAmount;
    }
    public void setInstalmentAmount(BigDecimal instalmentAmount) {
        this.instalmentAmount = instalmentAmount;
    }
    public BigDecimal getTaxCoefficient() {
        return taxCoefficient;
    }
    public void setTaxCoefficient(BigDecimal taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public BigDecimal getTotalAmountFinanced() {
        return totalAmountFinanced;
    }
    public void setTotalAmountFinanced(BigDecimal totalAmountFinanced) {
        this.totalAmountFinanced = totalAmountFinanced;
    }
    public BigDecimal getDeposit() {
        return deposit;
    }
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }
    public BigDecimal getDepositPercent() {
        return depositPercent;
    }
    public void setDepositPercent(BigDecimal depositPercent) {
        this.depositPercent = depositPercent;
    }
    public Boolean getCalculateOk() {
        return calculateOk;
    }
    public void setCalculateOk(Boolean calculateOk) {
        this.calculateOk = calculateOk;
    }
    public Boolean getDepositRecalculate() {
        return depositRecalculate;
    }
    public void setDepositRecalculate(Boolean depositRecalculate) {
        this.depositRecalculate = depositRecalculate;
    }
    public String getCoeffcientId() {
        return coeffcientId;
    }
    public void setCoeffcientId(String coeffcientId) {
        this.coeffcientId = coeffcientId;
    }
    public BigDecimal getVehiclePrice() {
        return vehiclePrice;
    }
    public void setVehiclePrice(BigDecimal vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }
    public List<InstalmentDTO> getListInstalment() {
        return listInstalment;
    }
    public void setListInstalment(List<InstalmentDTO> listInstalment) {
        this.listInstalment = listInstalment;
    }
    public List<InstalmentGroupDTO> getListInstalmentGroup() {
        return listInstalmentGroup;
    }
    public void setListInstalmentGroup(List<InstalmentGroupDTO> listInstalmentGroup) {
        this.listInstalmentGroup = listInstalmentGroup;
    }
    public List<ServiceDTO> getServices() {
        return services;
    }
    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }
    public List<TaxDTO> getTaxes() {
        return taxes;
    }
    public void setTaxes(List<TaxDTO> taxes) {
        this.taxes = taxes;
    }
    public BigDecimal getCoefficient() {
        return coefficient;
    }
    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }
    
}

package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class DepositCalculateDTO {
    
    private BigDecimal deposit;
    private BigDecimal totalVehicleAmount;
    private BigDecimal totalAmount;
    private BigDecimal spf;
    private BigDecimal totalService;
    
    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getSpf() {
        return spf;
    }

    public void setSpf(BigDecimal spf) {
        this.spf = spf;
    }

    public BigDecimal getTotalService() {
        return totalService;
    }

    public void setTotalService(BigDecimal totalService) {
        this.totalService = totalService;
    }

    public BigDecimal getTotalVehicleAmount() {
        return totalVehicleAmount;
    }

    public void setTotalVehicleAmount(BigDecimal totalVehicleAmount) {
        this.totalVehicleAmount = totalVehicleAmount;
    }

}

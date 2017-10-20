package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class InstalmentGroupDTO {

    private Integer quantity;
    private BigDecimal instalmentAmount;
    
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getInstalmentAmount() {
        return instalmentAmount;
    }
    public void setInstalmentAmount(BigDecimal instalmentAmount) {
        this.instalmentAmount = instalmentAmount;
    }

}

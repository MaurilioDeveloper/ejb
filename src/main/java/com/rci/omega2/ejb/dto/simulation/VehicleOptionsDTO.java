package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;

import com.rci.omega2.ejb.dto.BaseDTO;

public class VehicleOptionsDTO extends BaseDTO{

    /**serial version  */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String description;
    private BigDecimal amount;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}

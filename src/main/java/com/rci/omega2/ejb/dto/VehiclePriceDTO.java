package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class VehiclePriceDTO extends BaseDTO {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    private String id;
    BigDecimal amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}

package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class VehicleVersionOptionsDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private String description;
    private BigDecimal amount;
    // tax_amount ?

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

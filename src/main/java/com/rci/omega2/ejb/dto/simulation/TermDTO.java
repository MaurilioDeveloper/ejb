package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;

import com.rci.omega2.ejb.dto.BaseDTO;

public class TermDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private Integer quantity;
    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
}

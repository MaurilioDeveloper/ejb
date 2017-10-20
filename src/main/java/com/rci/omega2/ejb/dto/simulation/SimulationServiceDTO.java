package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;

import com.rci.omega2.ejb.dto.BaseDTO;

public class SimulationServiceDTO extends BaseDTO {
    /** serial version */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private BigDecimal value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

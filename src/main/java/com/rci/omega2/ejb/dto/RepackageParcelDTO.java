package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class RepackageParcelDTO {
    private BigDecimal factor;
    private int Instalment;

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public int getInstalment() {
        return Instalment;
    }

    public void setInstalment(int instalment) {
        Instalment = instalment;
    }

}

package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class SimulationCalcDTO {

    private BigDecimal totalFinanced;
    private BigDecimal monthlyAmount;

    public BigDecimal getTotalFinanced() {
        return totalFinanced;
    }

    public void setTotalFinanced(BigDecimal totalFinanced) {
        this.totalFinanced = totalFinanced;
    }

    public BigDecimal getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(BigDecimal monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }
}

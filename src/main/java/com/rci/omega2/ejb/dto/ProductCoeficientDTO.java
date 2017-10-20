package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class ProductCoeficientDTO {
    private String coeffcientId;
    private BigDecimal coefficient;
    private BigDecimal taxCoefficient;
    private BigDecimal depositPercent;
    private Boolean depositRecalculate = Boolean.FALSE;
    
    public BigDecimal getCoefficient() {
        return coefficient;
    }
    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }
    public BigDecimal getTaxCoefficient() {
        return taxCoefficient;
    }
    public void setTaxCoefficient(BigDecimal taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }
    public String getCoeffcientId() {
        return coeffcientId;
    }
    public void setCoeffcientId(String coeffcientId) {
        this.coeffcientId = coeffcientId;
    }
    public BigDecimal getDepositPercent() {
        return depositPercent;
    }
    public void setDepositPercent(BigDecimal depositPercent) {
        this.depositPercent = depositPercent;
    }
    public Boolean getDepositRecalculate() {
        return depositRecalculate;
    }
    public void setDepositRecalculate(Boolean depositRecalculate) {
        this.depositRecalculate = depositRecalculate;
    }
    
}

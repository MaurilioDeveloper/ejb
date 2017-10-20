package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.omega2.ejb.dto.BaseDTO;

public class InstalmentDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;
    private Integer numberInstallment;
    private Date dueDate;
    private BigDecimal amount;
    private BigDecimal factor;
    
    public Integer getNumberInstallment() {
        return numberInstallment;
    }

    public void setNumberInstallment(Integer numberInstallment) {
        this.numberInstallment = numberInstallment;
    }

    public Date getDueDate() {
        return dueDate == null ? null : new Date(dueDate.getTime());
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }
   
}

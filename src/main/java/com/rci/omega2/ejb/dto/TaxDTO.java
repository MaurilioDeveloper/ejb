package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class TaxDTO extends BaseDTO {
    
    private static final long serialVersionUID = -5911579645935403418L;
    
    private String idTax;
    private String description;
    private String taxType;
    private BigDecimal value;
    
    /**
     * @return the idTax
     */
    public String getIdTax() {
        return idTax;
    }
    /**
     * @param idTax the idTax to set
     */
    public void setIdTax(String idTax) {
        this.idTax = idTax;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the taxType
     */
    public String getTaxType() {
        return taxType;
    }
    /**
     * @param taxType the taxType to set
     */
    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }
    /**
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    
}

package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;

public class RateDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private BigDecimal value;
    private ProvinceDTO province;
    private String taxType;

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

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

}

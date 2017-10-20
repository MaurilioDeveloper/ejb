package com.rci.omega2.ejb.dto.simulation;

import com.rci.omega2.ejb.dto.BaseDTO;

public class PhoneDTO extends BaseDTO {

    /** seial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private String number;
    private String extension;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}

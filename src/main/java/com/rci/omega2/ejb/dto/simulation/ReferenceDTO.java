package com.rci.omega2.ejb.dto.simulation;

import com.rci.omega2.ejb.dto.BaseDTO;

public class ReferenceDTO extends BaseDTO{

    /**serial version*/
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

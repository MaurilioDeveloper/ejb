package com.rci.omega2.ejb.utils;

public enum LinkTypeEnum {
    PROPONENTE("Proponente","1"), 
    CONJUGE_PROP("Cônjuge do proponente","2"), 
    AVALISTA1("Avalista 1", "3"),  
    CONJUGE_AVAL1("Cônjuge Avalista 1", "4"), 
    AVALISTA2("Avalista 2", "5"),
    CONJUGE_AVAL2("Cônjuge Avalista 2", "6");
    
    private String description;
    private String value;

    private LinkTypeEnum(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

}

package com.rci.omega2.ejb.dto;

public class RepackageDTO {
    
    private String id;
    private String importCode;
    private Integer duration;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getImportCode() {
        return importCode;
    }
    public void setImportCode(String importCode) {
        this.importCode = importCode;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
}

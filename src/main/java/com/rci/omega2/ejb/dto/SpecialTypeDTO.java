package com.rci.omega2.ejb.dto;
/**
 * 
 * @author Ricardo Zandonai
 * @email rzandonai@stefanini.com
 *
 */
public class SpecialTypeDTO extends BaseDTO {
    private static final long serialVersionUID = 1368963027800444133L;
    private String id;
    private String description;
    private Boolean checked;

   
    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

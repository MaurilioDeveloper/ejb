package com.rci.omega2.ejb.dto;

public class FinancialBrandDTO extends BaseDTO{

    private static final long serialVersionUID = 1368963027800444133L;
    
    private String id;
    private String importCodeActor;
    private String description;
    private String fileId;
    private String fileLink;
    private boolean checked;
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the importCodeActor
     */
    public String getImportCodeActor() {
        return importCodeActor;
    }
    /**
     * @param importCodeActor the importCodeActor to set
     */
    public void setImportCodeActor(String importCodeActor) {
        this.importCodeActor = importCodeActor;
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
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }
    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    /**
     * @return the fileLink
     */
    public String getFileLink() {
        return fileLink;
    }
    /**
     * @param fileLink the fileLink to set
     */
    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }
    /**
     * @param checked the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    
    
}

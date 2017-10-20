package com.rci.omega2.ejb.dto;

public class FileDTO extends BaseDTO{
    
    private static final long serialVersionUID = 6399697464695050149L;
    private String fileId;
    private String fileName;
    private String fileLink;
    private String description;
    private String fileObjectId;
    
    
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
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * @return the fileObjectId
     */
    public String getFileObjectId() {
        return fileObjectId;
    }
    /**
     * @param fileObjectId the fileObjectId to set
     */
    public void setFileObjectId(String fileObjectId) {
        this.fileObjectId = fileObjectId;
    }
    
    
}

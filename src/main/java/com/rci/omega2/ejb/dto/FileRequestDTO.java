package com.rci.omega2.ejb.dto;

public class FileRequestDTO {
    
    private Long fileId;
    private String fileName;
    private String fileLink;
    private String description;
    private Long fileObjectId;
    private byte[] file;
    
    /**
     * @return the fileId
     */
    public Long getFileId() {
        return fileId;
    }
    /**
     * @param fileId the fileId to set
     */
    public void setFileId(Long fileId) {
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
    public Long getFileObjectId() {
        return fileObjectId;
    }
    /**
     * @param fileObjectId the fileObjectId to set
     */
    public void setFileObjectId(Long fileObjectId) {
        this.fileObjectId = fileObjectId;
    }
    /**
     * @return the file
     */
    public byte[] getFile() {
        return file;
    }
    /**
     * @param file the file to set
     */
    public void setFile(byte[] file) {
        this.file = file;
    }
    
}

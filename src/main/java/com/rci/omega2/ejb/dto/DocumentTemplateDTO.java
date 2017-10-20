package com.rci.omega2.ejb.dto;

public class DocumentTemplateDTO extends BaseDTO {

    private static final long serialVersionUID = 1368963027800444133L;
    private String documentTemplateId;
    private String description;
    private String name;
    private String importCode;
    private String fileId;
    private String fileName;
    private String link;

    public String getDocumentTemplateId() {
        return documentTemplateId;
    }

    public void setDocumentTemplateId(String documentTemplateId) {
        this.documentTemplateId = documentTemplateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImportCode() {
        return importCode;
    }

    public void setImportCode(String importCode) {
        this.importCode = importCode;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}

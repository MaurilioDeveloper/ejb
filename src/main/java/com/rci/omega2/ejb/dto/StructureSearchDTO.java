package com.rci.omega2.ejb.dto;

public class StructureSearchDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String structureId;
    private String structureName;
    private String structureCode;

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getStructureCode() {
        return structureCode;
    }

    public void setStructureCode(String structureCode) {
        this.structureCode = structureCode;
    }

}

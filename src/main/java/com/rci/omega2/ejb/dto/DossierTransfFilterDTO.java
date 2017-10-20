package com.rci.omega2.ejb.dto;

import java.util.Date;

import com.rci.omega2.entity.UserEntity;

public class DossierTransfFilterDTO extends BaseDTO{
    
    private static final long serialVersionUID = -9159620015606656182L;
    private Long dossierId;
    private Long personIdFrom;
    private Long personIdTo;
    private Long structureIdFrom;
    private Long structureIdTo;
    private Date includeDate;
    private UserEntity UserEntity;
    
    /**
     * @return the dossierId
     */
    public Long getDossierId() {
        return dossierId;
    }
    /**
     * @param dossierId the dossierId to set
     */
    public void setDossierId(Long dossierId) {
        this.dossierId = dossierId;
    }
    /**
     * @return the personIdFrom
     */
    public Long getPersonIdFrom() {
        return personIdFrom;
    }
    /**
     * @param personIdFrom the personIdFrom to set
     */
    public void setPersonIdFrom(Long personIdFrom) {
        this.personIdFrom = personIdFrom;
    }
    /**
     * @return the personIdTo
     */
    public Long getPersonIdTo() {
        return personIdTo;
    }
    /**
     * @param personIdTo the personIdTo to set
     */
    public void setPersonIdTo(Long personIdTo) {
        this.personIdTo = personIdTo;
    }
    /**
     * @return the structureIdFrom
     */
    public Long getStructureIdFrom() {
        return structureIdFrom;
    }
    /**
     * @param structureIdFrom the structureIdFrom to set
     */
    public void setStructureIdFrom(Long structureIdFrom) {
        this.structureIdFrom = structureIdFrom;
    }
    /**
     * @return the structureIdTo
     */
    public Long getStructureIdTo() {
        return structureIdTo;
    }
    /**
     * @param structureIdTo the structureIdTo to set
     */
    public void setStructureIdTo(Long structureIdTo) {
        this.structureIdTo = structureIdTo;
    }
    /**
     * @return the includeDate
     */
    public Date getIncludeDate() {
        return includeDate == null ? null : new Date(includeDate.getTime());
    }
    /**
     * @param includeDate the includeDate to set
     */
    public void setIncludeDate(Date includeDate) {
        this.includeDate = includeDate == null ? null : new Date(includeDate.getTime());
    }
    /**
     * @return the userEntity
     */
    public UserEntity getUserEntity() {
        return UserEntity;
    }
    /**
     * @param userEntity the userEntity to set
     */
    public void setUserEntity(UserEntity userEntity) {
        UserEntity = userEntity;
    }
    
}

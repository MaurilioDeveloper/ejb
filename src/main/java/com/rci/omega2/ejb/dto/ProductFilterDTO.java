package com.rci.omega2.ejb.dto;


import java.util.Date;
import java.util.List;

public class ProductFilterDTO extends BaseDTO{
    
    private static final long serialVersionUID = -7144261512079028654L;
    private Long financeType;
    private Long saleType;
    private Integer manufactureYear;
    private Integer modelYear;
    private Date currentDate;
    private String personType;
    private Integer idCalculation;
    private Long structureId;
    private Long vehicleVersion;
    private List<Long> vehicleSpecials;
    
    /**9 
     * @return the financeType
     */
    public Long getFinanceType() {
        return financeType;
    }
    /**
     * @param financeType the financeType to set
     */
    public void setFinanceType(Long financeType) {
        this.financeType = financeType;
    }
    /**
     * @return the manufactureYear
     */
    public Integer getManufactureYear() {
        return manufactureYear;
    }
    /**
     * @param manufactureYear the manufactureYear to set
     */
    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }
    /**
     * @return the modelYear
     */
    public Integer getModelYear() {
        return modelYear;
    }
    /**
     * @param modelYear the modelYear to set
     */
    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }
    /**
     * @return the currentDate
     */
    public Date getCurrentDate() {
        return currentDate == null ? null : new Date(currentDate.getTime());
    }
    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate == null ? null : new Date(currentDate.getTime());
    }
    /**
     * @return the personType
     */
    public String getPersonType() {
        return personType;
    }
    /**
     * @param personType the personType to set
     */
    public void setPersonType(String personType) {
        this.personType = personType;
    }
    /**
     * @return the structureId
     */
    public Long getStructureId() {
        return structureId;
    }
    /**
     * @param structureId the structureId to set
     */
    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }
    /**
     * @return the vehicleVersion
     */
    public Long getVehicleVersion() {
        return vehicleVersion;
    }
    /**
     * @param vehicleVersion the vehicleVersion to set
     */
    public void setVehicleVersion(Long vehicleVersion) {
        this.vehicleVersion = vehicleVersion;
    }
    public List<Long> getVehicleSpecials() {
        return vehicleSpecials;
    }
    public void setVehicleSpecials(List<Long> vehicleSpecials) {
        this.vehicleSpecials = vehicleSpecials;
    }
    public Long getSaleType() {
        return saleType;
    }
    public void setSaleType(Long saleType) {
        this.saleType = saleType;
    }
    public Integer getIdCalculation() {
        return idCalculation;
    }
    public void setIdCalculation(Integer idCalculation) {
        this.idCalculation = idCalculation;
    }

    
}

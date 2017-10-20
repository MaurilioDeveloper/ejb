package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

import com.rci.omega2.entity.enumeration.PersonTypeEnum;
/**
 * 
 * @author Ricardo Zandonai (rzandonai@stefanini.com) 
 *
 */
public class SantanderSimulationDTO {
    private Long idVersion;
    private String typeOfFinancing;
    private Boolean isTaxi;
    private Boolean isAdapted;
    private String provinceName;
    private PersonTypeEnum personType;
    private String cpfCnpj;
    private String simulationTable;
    // FLEX or PADRAO
    private String modalityOfFinancing;
    
    private BigDecimal totalValue;
    private BigDecimal inputValue;
    private Integer parcelsNumber;
    
    //ddMMyyyy
    private String firstPaymentDate;
    
    private Boolean taxTab;
    private Boolean taxTc;
    private String frotaCode;
    
    

    public Long getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
    }

    public String getTypeOfFinancing() {
        return typeOfFinancing;
    }

    public void setTypeOfFinancing(String typeOfFinancing) {
        this.typeOfFinancing = typeOfFinancing;
    }

    public Boolean getIsTaxi() {
        return isTaxi;
    }

    public void setIsTaxi(Boolean isTaxi) {
        this.isTaxi = isTaxi;
    }

    public Boolean getIsAdapted() {
        return isAdapted;
    }

    public void setIsAdapted(Boolean isAdapted) {
        this.isAdapted = isAdapted;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getInputValue() {
        return inputValue;
    }

    public void setInputValue(BigDecimal inputValue) {
        this.inputValue = inputValue;
    }

    public Integer getParcelsNumber() {
        return parcelsNumber;
    }

    public void setParcelsNumber(Integer parcelsNumber) {
        this.parcelsNumber = parcelsNumber;
    }

    public PersonTypeEnum getPersonType() {
        return personType;
    }

    public void setPersonType(PersonTypeEnum personType) {
        this.personType = personType;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getSimulationTable() {
        return simulationTable;
    }

    public void setSimulationTable(String simulationTable) {
        this.simulationTable = simulationTable;
    }

    public String getModalityOfFinancing() {
        return modalityOfFinancing;
    }

    public void setModalityOfFinancing(String modalityOfFinancing) {
        this.modalityOfFinancing = modalityOfFinancing;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public void setFirstPaymentDate(String firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    public String getFrotaCode() {
        return frotaCode;
    }

    public void setFrotaCode(String frotaCode) {
        this.frotaCode = frotaCode;
    }

    public Boolean getTaxTc() {
        return taxTc;
    }

    public void setTaxTc(Boolean taxTc) {
        this.taxTc = taxTc;
    }

    public Boolean getTaxTab() {
        return taxTab;
    }

    public void setTaxTab(Boolean taxTab) {
        this.taxTab = taxTab;
    }
}

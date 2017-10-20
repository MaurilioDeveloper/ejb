package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;
import java.util.List;

import com.rci.omega2.ejb.dto.simulation.ServiceDTO;
import com.rci.omega2.entity.RepackagePlanEntity;
import com.rci.omega2.entity.UserEntity;

public class CalculateParametersDTO {
    
    private UserEntity currentUser;
    private Long salesmanId;
    private Long structureId;
    
    private BigDecimal deposit;
    private BigDecimal depositPercent;
    private BigDecimal totalVehicleAmount;
    private Long repackageId;
    private Long commissionId;
    private Long productId;
    private Long saleTypeId;
    private Long vehicleVersionId;
    private Long financeTypeId;
    private String personType;
    private Integer term;
    private Integer delayValue;
    private String province;
    private Boolean tcExempt;
    private List<String> vehiclesSpecial;
    private List<ServiceDTO> services;
    
    private List<RepackagePlanEntity> listRepackagePlan;
    
    public UserEntity getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(UserEntity currentUser) {
        this.currentUser = currentUser;
    }
    public Long getSalesmanId() {
        return salesmanId;
    }
    public void setSalesmanId(Long salesmanId) {
        this.salesmanId = salesmanId;
    }
    public Long getStructureId() {
        return structureId;
    }
    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }
    public List<ServiceDTO> getServices() {
        return services;
    }
    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }
    public BigDecimal getDeposit() {
        return deposit;
    }
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }
    public Long getCommissionId() {
        return commissionId;
    }
    public void setCommissionId(Long commissionId) {
        this.commissionId = commissionId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getPersonType() {
        return personType;
    }
    public void setPersonType(String personType) {
        this.personType = personType;
    }
    public Integer getTerm() {
        return term;
    }
    public void setTerm(Integer term) {
        this.term = term;
    }
    public Integer getDelayValue() {
        return delayValue;
    }
    public void setDelayValue(Integer delayValue) {
        this.delayValue = delayValue;
    }
    public List<String> getVehiclesSpecial() {
        return vehiclesSpecial;
    }
    public void setVehiclesSpecial(List<String> vehiclesSpecial) {
        this.vehiclesSpecial = vehiclesSpecial;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public Boolean getTcExempt() {
        return tcExempt;
    }
    public void setTcExempt(Boolean tcExempt) {
        this.tcExempt = tcExempt;
    }
    public Long getSaleTypeId() {
        return saleTypeId;
    }
    public void setSaleTypeId(Long saleTypeId) {
        this.saleTypeId = saleTypeId;
    }
    public Long getRepackageId() {
        return repackageId;
    }
    public void setRepackageId(Long repackageId) {
        this.repackageId = repackageId;
    }
    public Long getVehicleVersionId() {
        return vehicleVersionId;
    }
    public void setVehicleVersionId(Long vehicleVersionId) {
        this.vehicleVersionId = vehicleVersionId;
    }
    public Long getFinanceTypeId() {
        return financeTypeId;
    }
    public void setFinanceTypeId(Long financeTypeId) {
        this.financeTypeId = financeTypeId;
    }
    public List<RepackagePlanEntity> getListRepackagePlan() {
        return listRepackagePlan;
    }
    public void setListRepackagePlan(List<RepackagePlanEntity> listRepackagePlan) {
        this.listRepackagePlan = listRepackagePlan;
    }
    public BigDecimal getDepositPercent() {
        return depositPercent;
    }
    public void setDepositPercent(BigDecimal depositPercent) {
        this.depositPercent = depositPercent;
    }
    public BigDecimal getTotalVehicleAmount() {
        return totalVehicleAmount;
    }
    public void setTotalVehicleAmount(BigDecimal totalVehicleAmount) {
        this.totalVehicleAmount = totalVehicleAmount;
    }
    
}

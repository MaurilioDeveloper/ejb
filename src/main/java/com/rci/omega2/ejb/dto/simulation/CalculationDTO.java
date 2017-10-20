package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;
import java.util.List;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.FinanceTypeDTO;
import com.rci.omega2.ejb.dto.ProductCoeficientDTO;
import com.rci.omega2.ejb.dto.ProductDTO;
import com.rci.omega2.ejb.dto.RepackageDTO;

public class CalculationDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private FinanceTypeDTO financialType;
    private ProductDTO financialTable;
    private ProductCoeficientDTO coeficiente;
    private BigDecimal entranceValue;
    private Integer delay;
    private List<RateDTO> rates;
    private CommissionDTO commission;
    private Integer term;
    private BigDecimal totalValue;
    private BigDecimal instalmentAmount;
    private BigDecimal montlyRate;
    private BigDecimal financedAmount;
    private List<InstalmentDTO> installments;
    private Boolean selected;
    private List<ServiceDTO> services;
    private List<TermDTO> terms;
    private String creditReport;
    private String storeControl;
    private RepackageDTO repackage;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getEntranceValue() {
        return entranceValue;
    }

    public void setEntranceValue(BigDecimal entranceValue) {
        this.entranceValue = entranceValue;
    }

    public List<RateDTO> getRates() {
        return rates;
    }

    public void setRates(List<RateDTO> rates) {
        this.rates = rates;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public List<InstalmentDTO> getInstallments() {
        return installments;
    }

    public void setInstallments(List<InstalmentDTO> installments) {
        this.installments = installments;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public CommissionDTO getCommission() {
        return commission;
    }

    public void setCommission(CommissionDTO commission) {
        this.commission = commission;
    }

    public FinanceTypeDTO getFinancialType() {
        return financialType;
    }

    public void setFinancialType(FinanceTypeDTO financialType) {
        this.financialType = financialType;
    }

    public ProductDTO getFinancialTable() {
        return financialTable;
    }

    public void setFinancialTable(ProductDTO financialTable) {
        this.financialTable = financialTable;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public BigDecimal getInstalmentAmount() {
        return instalmentAmount;
    }

    public void setInstalmentAmount(BigDecimal instalmentAmount) {
        this.instalmentAmount = instalmentAmount;
    }

    public BigDecimal getMontlyRate() {
        return montlyRate;
    }

    public void setMontlyRate(BigDecimal montlyRate) {
        this.montlyRate = montlyRate;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

    public List<TermDTO> getTerms() {
        return terms;
    }

    public void setTerms(List<TermDTO> terms) {
        this.terms = terms;
    }

    public BigDecimal getFinancedAmount() {
        return financedAmount;
    }

    public void setFinancedAmount(BigDecimal financedAmount) {
        this.financedAmount = financedAmount;
    }

    public String getCreditReport() {
        return creditReport;
    }

    public void setCreditReport(String creditReport) {
        this.creditReport = creditReport;
    }

    public String getStoreControl() {
        return storeControl;
    }

    public void setStoreControl(String storeControl) {
        this.storeControl = storeControl;
    }

    public ProductCoeficientDTO getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(ProductCoeficientDTO coeficiente) {
        this.coeficiente = coeficiente;
    }

    public RepackageDTO getRepackage() {
        return repackage;
    }

    public void setRepackage(RepackageDTO repackage) {
        this.repackage = repackage;
    }

}

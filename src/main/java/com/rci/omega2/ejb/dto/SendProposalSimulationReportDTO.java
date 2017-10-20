package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;

public class SendProposalSimulationReportDTO extends BaseDTO{

    /**serial version*/
    private static final long serialVersionUID = 1L;
    
    private Integer simulationCount;
    private BigDecimal monthlyAmount;
    private String financeTable;
    private BigDecimal entryAmount;
    private BigDecimal financedAmount;
    private BigDecimal clientTax;
    private Integer delay;
    private Integer term;
    private String services;

    public Integer getSimulationCount() {
        return simulationCount;
    }

    public void setSimulationCount(Integer simulationCount) {
        this.simulationCount = simulationCount;
    }

    public BigDecimal getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(BigDecimal monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public String getFinanceTable() {
        return financeTable;
    }

    public void setFinanceTable(String financeTable) {
        this.financeTable = financeTable;
    }

    public BigDecimal getEntryAmount() {
        return entryAmount;
    }

    public void setEntryAmount(BigDecimal entryAmount) {
        this.entryAmount = entryAmount;
    }

    public BigDecimal getFinancedAmount() {
        return financedAmount;
    }

    public void setFinancedAmount(BigDecimal financedAmount) {
        this.financedAmount = financedAmount;
    }

    public BigDecimal getClientTax() {
        return clientTax;
    }

    public void setClientTax(BigDecimal clientTax) {
        this.clientTax = clientTax;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}

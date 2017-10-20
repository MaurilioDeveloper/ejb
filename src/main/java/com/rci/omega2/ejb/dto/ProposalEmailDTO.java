package com.rci.omega2.ejb.dto;

public class ProposalEmailDTO extends BaseDTO{

    /**serial version*/
    private static final long serialVersionUID = 1L;

    private Long simulationNumber;
    private String simulationStatus;
    private String salesmanName;
    private String salesmanEmail;
    private String dealershipName;
    private String clientEmail;
    private SendProposalReportDTO dtoReport;

    public Long getSimulationNumber() {
        return simulationNumber;
    }

    public void setSimulationNumber(Long simulationNumber) {
        this.simulationNumber = simulationNumber;
    }

    public String getSimulationStatus() {
        return simulationStatus;
    }

    public void setSimulationStatus(String simulationStatus) {
        this.simulationStatus = simulationStatus;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanEmail() {
        return salesmanEmail;
    }

    public void setSalesmanEmail(String salesmanEmail) {
        this.salesmanEmail = salesmanEmail;
    }

    public String getDealershipName() {
        return dealershipName;
    }

    public void setDealershipName(String dealershipName) {
        this.dealershipName = dealershipName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public SendProposalReportDTO getDtoReport() {
        return dtoReport;
    }

    public void setDtoReport(SendProposalReportDTO dtoReport) {
        this.dtoReport = dtoReport;
    }
}

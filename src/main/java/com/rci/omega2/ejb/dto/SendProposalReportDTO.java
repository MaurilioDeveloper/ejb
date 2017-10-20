package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SendProposalReportDTO extends BaseDTO {
    /** serial version */
    private static final long serialVersionUID = 1L;

    private String dealership;
    private String phone;
    private String salesman;
    private Date data;
    private String model;
    private String version;
    private Integer modelYear;
    private Integer manufactureYear;
    private String clientType;
    private String state;
    private BigDecimal vehicleValue;
    private BigDecimal optionalValue;
    private BigDecimal accessoriesValue;
    private List<SendProposalSimulationReportDTO> simulations;
    private String footerMessage;

    public String getDealership() {
        return dealership;
    }

    public void setDealership(String dealership) {
        this.dealership = dealership;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public Date getData() {
        return data == null ? null : new Date(data.getTime());
    }

    public void setData(Date data) {
        this.data = data == null ? null : new Date(data.getTime());
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getVehicleValue() {
        return vehicleValue;
    }

    public void setVehicleValue(BigDecimal vehicleValue) {
        this.vehicleValue = vehicleValue;
    }

    public BigDecimal getOptionalValue() {
        return optionalValue;
    }

    public void setOptionalValue(BigDecimal optionalValue) {
        this.optionalValue = optionalValue;
    }

    public BigDecimal getAccessoriesValue() {
        return accessoriesValue;
    }

    public void setAccessoriesValue(BigDecimal accessoriesValue) {
        this.accessoriesValue = accessoriesValue;
    }

    public List<SendProposalSimulationReportDTO> getSimulations() {
        return simulations;
    }

    public void setSimulations(List<SendProposalSimulationReportDTO> simulations) {
        this.simulations = simulations;
    }

    public String getFooterMessage() {
        return footerMessage;
    }

    public void setFooterMessage(String footerMessage) {
        this.footerMessage = footerMessage;
    }
}

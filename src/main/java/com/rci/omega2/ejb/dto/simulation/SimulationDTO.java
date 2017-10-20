package com.rci.omega2.ejb.dto.simulation;

import java.util.List;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.SaleTypeDTO;
import com.rci.omega2.ejb.dto.SpecialTypeDTO;
import com.rci.omega2.ejb.dto.VehicleBrandDTO;

public class SimulationDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private CarDTO car;
    private ClientDTO client;
    private SaleTypeDTO saleType;
    private boolean tc;
    private boolean vizualization;
    private VehicleBrandDTO brand;
    private List<CalculationDTO> calculations;
    private List<SpecialTypeDTO> specialTypes;
    private boolean isShowRoomSemiNews;
    private boolean showNewOnes;
    private String dossierNumber;
    private Long dossierStatus;
    private CalculationDTO calculationSelected;
    private String certifiedAgent;
    private String adp;
    private boolean showDocuments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public SaleTypeDTO getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleTypeDTO saleType) {
        this.saleType = saleType;
    }

    public boolean isTc() {
        return tc;
    }

    public void setTc(boolean tc) {
        this.tc = tc;
    }

    public boolean isVizualization() {
        return vizualization;
    }

    public void setVizualization(boolean vizualization) {
        this.vizualization = vizualization;
    }

    public VehicleBrandDTO getBrand() {
        return brand;
    }

    public void setBrand(VehicleBrandDTO brand) {
        this.brand = brand;
    }

    public List<CalculationDTO> getCalculations() {
        return calculations;
    }

    public void setCalculations(List<CalculationDTO> calculations) {
        this.calculations = calculations;
    }

    public List<SpecialTypeDTO> getSpecialTypes() {
        return specialTypes;
    }

    public void setSpecialTypes(List<SpecialTypeDTO> specialTypes) {
        this.specialTypes = specialTypes;
    }

    public boolean isShowRoomSemiNews() {
        return isShowRoomSemiNews;
    }

    public void setShowRoomSemiNews(boolean isShowRoomSemiNews) {
        this.isShowRoomSemiNews = isShowRoomSemiNews;
    }

    public boolean isShowNewOnes() {
        return showNewOnes;
    }

    public void setShowNewOnes(boolean showNewOnes) {
        this.showNewOnes = showNewOnes;
    }

    public String getDossierNumber() {
        return dossierNumber;
    }

    public void setDossierNumber(String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }

    public Long getDossierStatus() {
        return dossierStatus;
    }

    public void setDossierStatus(Long dossierStatus) {
        this.dossierStatus = dossierStatus;
    }

    public CalculationDTO getCalculationSelected() {
        return calculationSelected;
    }

    public void setCalculationSelected(CalculationDTO calculationSelected) {
        this.calculationSelected = calculationSelected;
    }

    public String getCertifiedAgent() {
        return certifiedAgent;
    }

    public void setCertifiedAgent(String certifiedAgent) {
        this.certifiedAgent = certifiedAgent;
    }

    public String getAdp() {
        return adp;
    }

    public void setAdp(String adp) {
        this.adp = adp;
    }

    public boolean isShowDocuments() {
        return showDocuments;
    }

    public void setShowDocuments(boolean showDocuments) {
        this.showDocuments = showDocuments;
    }

}

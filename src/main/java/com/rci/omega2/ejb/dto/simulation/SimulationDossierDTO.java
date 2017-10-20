package com.rci.omega2.ejb.dto.simulation;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.entity.UserEntity;

public class SimulationDossierDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private Long managerId;
    private String salesmanId;
    private String structureId;
    private UserEntity currentUser;
    private SimulationDTO simulation;

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public UserEntity getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserEntity currentUser) {
        this.currentUser = currentUser;
    }

    public SimulationDTO getSimulation() {
        return simulation;
    }

    public void setSimulation(SimulationDTO simulation) {
        this.simulation = simulation;
    }

}

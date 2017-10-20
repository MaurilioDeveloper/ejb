package com.rci.omega2.ejb.dto.simulation;

import java.util.Date;

import com.rci.omega2.ejb.dto.BaseDTO;

public class CertifiedAgentDTO extends BaseDTO{

    /**serial version */
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String cpf;
    private String numCertificado;
    private Date dtValidadeCert;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumCertificado() {
        return numCertificado;
    }

    public void setNumCertificado(String numCertificado) {
        this.numCertificado = numCertificado;
    }

    public Date getDtValidadeCert() {
        return dtValidadeCert == null ? null : new Date(dtValidadeCert.getTime());
    }

    public void setDtValidadeCert(Date dtValidadeCert) {
        this.dtValidadeCert = dtValidadeCert == null ? null : new Date(dtValidadeCert.getTime());
    }
}

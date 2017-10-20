package com.rci.omega2.ejb.dto.ws;

import java.util.Date;

public class FilterUserDTO {
    private Date dtUpdateStart;
    private Date dtUpdateEnd;
    private Boolean isActive;
    private String loginLike;
    private String cpf;
    private Integer numberOfRegisters;
    private Integer page;

    public Date getDtUpdateStart() {
        return dtUpdateStart;
    }

    public void setDtUpdateStart(Date dtUpdateStart) {
        this.dtUpdateStart = dtUpdateStart;
    }

    public Date getDtUpdateEnd() {
        return dtUpdateEnd;
    }

    public void setDtUpdateEnd(Date dtUpdateEnd) {
        this.dtUpdateEnd = dtUpdateEnd;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getLoginLike() {
        return loginLike;
    }

    public void setLoginLike(String loginLike) {
        this.loginLike = loginLike;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getNumberOfRegisters() {
        return numberOfRegisters;
    }

    public void setNumberOfRegisters(Integer numberOfRegisters) {
        this.numberOfRegisters = numberOfRegisters;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}

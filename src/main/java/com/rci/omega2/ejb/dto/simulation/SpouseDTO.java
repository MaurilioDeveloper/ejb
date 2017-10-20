package com.rci.omega2.ejb.dto.simulation;

import java.util.Date;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.EmissionOrganismDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;

public class SpouseDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String cpf;
    private String sex;
    private Date birthDate;
    private ProvinceDTO province;
    private String numberDocument;
    private EmissionOrganismDTO issuingBody;
    private CompanyDTO company;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate == null ? null : new Date(birthDate.getTime());
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate == null ? null : new Date(birthDate.getTime());
    }

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }

    public String getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
    }

    public EmissionOrganismDTO getIssuingBody() {
        return issuingBody;
    }

    public void setIssuingBody(EmissionOrganismDTO issuingBody) {
        this.issuingBody = issuingBody;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }
}

package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.EconomicAtivityDTO;
import com.rci.omega2.ejb.dto.EmployerSizeDTO;
import com.rci.omega2.ejb.dto.IncomeTypeDTO;
import com.rci.omega2.ejb.dto.IndustrialSegmentDTO;
import com.rci.omega2.ejb.dto.OccupationDTO;
import com.rci.omega2.ejb.dto.ProfessionDTO;
import com.rci.omega2.ejb.dto.ProofIncomeTypeDTO;

public class CompanyDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private OccupationDTO occupation;
    private ProfessionDTO profession;
    private String cnpj;
    private SimulationAdressDTO address;
    private PhoneDTO comercialPhone;
    private IndustrialSegmentDTO economicActivityGroup;
    private EconomicAtivityDTO economicActivity;
    private EmployerSizeDTO size;
    private Date admissionDate;
    private BigDecimal patrimony;
    private IncomeTypeDTO incomeType;
    private ProofIncomeTypeDTO proofIncomeType;
    private BigDecimal monthlyIncome;
    private BigDecimal otherIncomes;
    private boolean ownSeat;

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

    public OccupationDTO getOccupation() {
        return occupation;
    }

    public void setOccupation(OccupationDTO occupation) {
        this.occupation = occupation;
    }

    public ProfessionDTO getProfession() {
        return profession;
    }

    public void setProfession(ProfessionDTO profession) {
        this.profession = profession;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public SimulationAdressDTO getAddress() {
        return address;
    }

    public void setAddress(SimulationAdressDTO address) {
        this.address = address;
    }

    public PhoneDTO getComercialPhone() {
        return comercialPhone;
    }

    public void setComercialPhone(PhoneDTO comercialPhone) {
        this.comercialPhone = comercialPhone;
    }

    public IndustrialSegmentDTO getEconomicActivityGroup() {
        return economicActivityGroup;
    }

    public void setEconomicActivityGroup(IndustrialSegmentDTO economicActivityGroup) {
        this.economicActivityGroup = economicActivityGroup;
    }

    public EconomicAtivityDTO getEconomicActivity() {
        return economicActivity;
    }

    public void setEconomicActivity(EconomicAtivityDTO economicActivity) {
        this.economicActivity = economicActivity;
    }

    public EmployerSizeDTO getSize() {
        return size;
    }

    public void setSize(EmployerSizeDTO size) {
        this.size = size;
    }

    public Date getAdmissionDate() {
        return admissionDate == null ? null : new Date(admissionDate.getTime());
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate == null ? null : new Date(admissionDate.getTime());
    }

    public BigDecimal getPatrimony() {
        return patrimony;
    }

    public void setPatrimony(BigDecimal patrimony) {
        this.patrimony = patrimony;
    }

    public IncomeTypeDTO getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(IncomeTypeDTO incomeType) {
        this.incomeType = incomeType;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public boolean isOwnSeat() {
        return ownSeat;
    }

    public void setOwnSeat(boolean ownSeat) {
        this.ownSeat = ownSeat;
    }

    public BigDecimal getOtherIncomes() {
        return otherIncomes;
    }

    public void setOtherIncomes(BigDecimal otherIncomes) {
        this.otherIncomes = otherIncomes;
    }

    public ProofIncomeTypeDTO getProofIncomeType() {
        return proofIncomeType;
    }

    public void setProofIncomeType(ProofIncomeTypeDTO proofIncomeType) {
        this.proofIncomeType = proofIncomeType;
    }

}

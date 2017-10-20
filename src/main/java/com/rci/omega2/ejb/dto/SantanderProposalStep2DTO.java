package com.rci.omega2.ejb.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.omega2.entity.enumeration.PersonGenderEnum;

/**
 * 
 * @author Ricardo Zandonai (rzandonai@stefanini.com)
 *
 */
public class SantanderProposalStep2DTO extends BaseSantanderProposalDTO{

    private String clientName;
    private String cpfCnpj;
    private Date birthDate;
    private PersonGenderEnum gender;
    private String personalDocumentNumber;
    private String documentType;
    private String documentIssuer;
    private String issuerState;
    private Date documentEmitDate;
    private Date documentValidityDate;
    private String documentCountry;
    private String maritalStatus;
    private String nationality;
    private String placeOfBirth;
    private String placeOfBirthState;
    private String cnpjNumber;
    private boolean ownHeadquarters;
    private String economicActivityGroup;
    private String economicActivityName;
    private String companySize;
    private Date admissionDate;
    private String professionDescription;
    private String profession;
    private String numberOfDependents;
    private BigDecimal monthlyIncomeValue;
    private BigDecimal anotherIncomeValue;
    private String anotherIncomesType;
    private String dddAnotherIncome;
    private String telAnotherIncome;
    private String telBranchAnotherIncome;
    private String degreeKinship;
    private BigDecimal equityValue;
    private String fathersName;
    private String mothersName;
    private String degreeEducation;
    private String santanderFunctionaryType;
    private boolean isHandcapped;
    private String linkTypeNumber;
    private BigDecimal patrimonyValue;
    private String politicallyExposedPerson;
    private String incomeType;
    private String incomeReceiptType;
    private String legalNature;
    private String stateOfNature;
    private String companyName;
    private String clientCode;
    private String relationshipDescription;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Date getBirthDate() {
        return birthDate == null ? null : new Date(birthDate.getTime());
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate == null ? null : new Date(birthDate.getTime());
    }

    public PersonGenderEnum getGender() {
        return gender;
    }

    public void setGender(PersonGenderEnum gender) {
        this.gender = gender;
    }

    public String getPersonalDocumentNumber() {
        return personalDocumentNumber;
    }

    public void setPersonalDocumentNumber(String personalDocumentNumber) {
        this.personalDocumentNumber = personalDocumentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentIssuer() {
        return documentIssuer;
    }

    public void setDocumentIssuer(String documentIssuer) {
        this.documentIssuer = documentIssuer;
    }

    public String getIssuerState() {
        return issuerState;
    }

    public void setIssuerState(String issuerState) {
        this.issuerState = issuerState;
    }

    public Date getDocumentEmitDate() {
        return documentEmitDate == null ? null : new Date(documentEmitDate.getTime());
    }

    public void setDocumentEmitDate(Date documentEmitDate) {
        this.documentEmitDate = documentEmitDate == null ? null : new Date(documentEmitDate.getTime());
    }

    public Date getDocumentValidityDate() {
        return documentValidityDate == null ? null : new Date(documentValidityDate.getTime());
    }

    public void setDocumentValidityDate(Date documentValidityDate) {
        this.documentValidityDate = documentValidityDate == null ? null : new Date(documentValidityDate.getTime());
    }

    public String getDocumentCountry() {
        return documentCountry;
    }

    public void setDocumentCountry(String documentCountry) {
        this.documentCountry = documentCountry;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPlaceOfBirthState() {
        return placeOfBirthState;
    }

    public void setPlaceOfBirthState(String placeOfBirthState) {
        this.placeOfBirthState = placeOfBirthState;
    }

    public String getCnpjNumber() {
        return cnpjNumber;
    }

    public void setCnpjNumber(String cnpjNumber) {
        this.cnpjNumber = cnpjNumber;
    }

    public boolean isOwnHeadquarters() {
        return ownHeadquarters;
    }

    public void setOwnHeadquarters(boolean ownHeadquarters) {
        this.ownHeadquarters = ownHeadquarters;
    }

    public String getEconomicActivityGroup() {
        return economicActivityGroup;
    }

    public void setEconomicActivityGroup(String economicActivityGroup) {
        this.economicActivityGroup = economicActivityGroup;
    }

    public String getEconomicActivityName() {
        return economicActivityName;
    }

    public void setEconomicActivityName(String economicActivityName) {
        this.economicActivityName = economicActivityName;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public Date getAdmissionDate() {
        return admissionDate == null ? null : new Date(admissionDate.getTime());
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate == null ? null : new Date(admissionDate.getTime());
    }

    public String getProfessionDescription() {
        return professionDescription;
    }

    public void setProfessionDescription(String professionDescription) {
        this.professionDescription = professionDescription;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(String numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    public BigDecimal getMonthlyIncomeValue() {
        return monthlyIncomeValue;
    }

    public void setMonthlyIncomeValue(BigDecimal monthlyIncomeValue) {
        this.monthlyIncomeValue = monthlyIncomeValue;
    }

    public BigDecimal getAnotherIncomeValue() {
        return anotherIncomeValue;
    }

    public void setAnotherIncomeValue(BigDecimal anotherIncomeValue) {
        this.anotherIncomeValue = anotherIncomeValue;
    }

    public String getAnotherIncomesType() {
        return anotherIncomesType;
    }

    public void setAnotherIncomesType(String anotherIncomesType) {
        this.anotherIncomesType = anotherIncomesType;
    }

    public String getDddAnotherIncome() {
        return dddAnotherIncome;
    }

    public void setDddAnotherIncome(String dddAnotherIncome) {
        this.dddAnotherIncome = dddAnotherIncome;
    }

    public String getTelAnotherIncome() {
        return telAnotherIncome;
    }

    public void setTelAnotherIncome(String telAnotherIncome) {
        this.telAnotherIncome = telAnotherIncome;
    }

    public String getTelBranchAnotherIncome() {
        return telBranchAnotherIncome;
    }

    public void setTelBranchAnotherIncome(String telBranchAnotherIncome) {
        this.telBranchAnotherIncome = telBranchAnotherIncome;
    }

    public String getDegreeKinship() {
        return degreeKinship;
    }

    public void setDegreeKinship(String degreeKinship) {
        this.degreeKinship = degreeKinship;
    }

    public BigDecimal getEquityValue() {
        return equityValue;
    }

    public void setEquityValue(BigDecimal equityValue) {
        this.equityValue = equityValue;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getDegreeEducation() {
        return degreeEducation;
    }

    public void setDegreeEducation(String degreeEducation) {
        this.degreeEducation = degreeEducation;
    }

    public String getSantanderFunctionaryType() {
        return santanderFunctionaryType;
    }

    public void setSantanderFunctionaryType(String santanderFunctionaryType) {
        this.santanderFunctionaryType = santanderFunctionaryType;
    }

    public boolean isHandcapped() {
        return isHandcapped;
    }

    public void setHandcapped(boolean isHandcapped) {
        this.isHandcapped = isHandcapped;
    }

    public String getLinkTypeNumber() {
        return linkTypeNumber;
    }

    public void setLinkTypeNumber(String linkTypeNumber) {
        this.linkTypeNumber = linkTypeNumber;
    }

    public BigDecimal getPatrimonyValue() {
        return patrimonyValue;
    }

    public void setPatrimonyValue(BigDecimal patrimonyValue) {
        this.patrimonyValue = patrimonyValue;
    }

    public String getPoliticallyExposedPerson() {
        return politicallyExposedPerson;
    }

    public void setPoliticallyExposedPerson(String politicallyExposedPerson) {
        this.politicallyExposedPerson = politicallyExposedPerson;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getIncomeReceiptType() {
        return incomeReceiptType;
    }

    public void setIncomeReceiptType(String incomeReceiptType) {
        this.incomeReceiptType = incomeReceiptType;
    }

    public String getLegalNature() {
        return legalNature;
    }

    public void setLegalNature(String legalNature) {
        this.legalNature = legalNature;
    }

    public String getStateOfNature() {
        return stateOfNature;
    }

    public void setStateOfNature(String stateOfNature) {
        this.stateOfNature = stateOfNature;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getClientCodeRelashionship() {
        return clientCode;
    }

    public void setClientCodeRelashionship(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getRelationshipDescription() {
        return relationshipDescription;
    }

    public void setRelationshipDescription(String relationshipDescription) {
        this.relationshipDescription = relationshipDescription;
    }

}

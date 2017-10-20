package com.rci.omega2.ejb.dto.simulation;

import java.util.Date;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.BusinessRelationshipTypeDTO;
import com.rci.omega2.ejb.dto.CivilStateDTO;
import com.rci.omega2.ejb.dto.CountryDTO;
import com.rci.omega2.ejb.dto.DocumentTypeDTO;
import com.rci.omega2.ejb.dto.EducationDegreeDTO;
import com.rci.omega2.ejb.dto.EmissionOrganismDTO;
import com.rci.omega2.ejb.dto.GuarantorTypeDTO;
import com.rci.omega2.ejb.dto.KinshipTypeDTO;
import com.rci.omega2.ejb.dto.PoliticalExpositionDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;

public class GuarantorDTO extends BaseDTO {

    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private GuarantorTypeDTO guarantorType;
    private KinshipTypeDTO kinshipType;
    private BusinessRelationshipTypeDTO businessRelashionshipType;
    private String name;
    private String cpf;
    private String sex;
    private Date birthDate;
    private CivilStateDTO civilState;
    private String fixPhoneType;
    private PhoneDTO fixPhone;
    private PhoneDTO cellphone;
    private String email;
    private CountryDTO country;
    private ProvinceDTO province;
    private String naturalness;
    private PoliticalExpositionDTO politicalExposition;
    private EducationDegreeDTO educationDegree;
    private boolean handicapped;
    private String mothersName;
    private String fathersName;
    private DocumentTypeDTO documentType;
    private String numberDocument;
    private CountryDTO countryDocument;
    private ProvinceDTO provinceDocument;
    private Date dateIssueDocument;
    private EmissionOrganismDTO issuingBodyDocument;
    private Date validDateDocument;
    
    /** Dados Residenciais */
    private SimulationAdressDTO address;
    /** Cônjuge */
    private SpouseDTO spouse;
    /** Dados Profissionais */
    private CompanyDTO company;
    /** Referências **/
    private ReferenceDTO reference1;
    private ReferenceDTO reference2;
    /** Referências Bancárias */
    private BankDetailsDTO bankDetails;
    
    private boolean isRequired;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GuarantorTypeDTO getGuarantorType() {
        return guarantorType;
    }

    public void setGuarantorType(GuarantorTypeDTO guarantorType) {
        this.guarantorType = guarantorType;
    }

    public KinshipTypeDTO getKinshipType() {
        return kinshipType;
    }

    public void setKinshipType(KinshipTypeDTO kinshipType) {
        this.kinshipType = kinshipType;
    }

    public BusinessRelationshipTypeDTO getBusinessRelashionshipType() {
        return businessRelashionshipType;
    }

    public void setBusinessRelashionshipType(BusinessRelationshipTypeDTO businessRelashionshipType) {
        this.businessRelashionshipType = businessRelashionshipType;
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

    public CivilStateDTO getCivilState() {
        return civilState;
    }

    public void setCivilState(CivilStateDTO civilState) {
        this.civilState = civilState;
    }

    public PhoneDTO getFixPhone() {
        return fixPhone;
    }

    public void setFixPhone(PhoneDTO fixPhone) {
        this.fixPhone = fixPhone;
    }

    public PhoneDTO getCellphone() {
        return cellphone;
    }

    public void setCellphone(PhoneDTO cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO coutry) {
        this.country = coutry;
    }

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }

    public String getNaturalness() {
        return naturalness;
    }

    public void setNaturalness(String naturalness) {
        this.naturalness = naturalness;
    }

    public PoliticalExpositionDTO getPoliticalExposition() {
        return politicalExposition;
    }

    public void setPoliticalExposition(PoliticalExpositionDTO politicalExposition) {
        this.politicalExposition = politicalExposition;
    }

    public EducationDegreeDTO getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(EducationDegreeDTO educationDegree) {
        this.educationDegree = educationDegree;
    }

    public boolean isHandicapped() {
        return handicapped;
    }

    public void setHandicapped(boolean handicapped) {
        this.handicapped = handicapped;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public DocumentTypeDTO getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeDTO documentType) {
        this.documentType = documentType;
    }

    public String getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
    }

    public CountryDTO getCountryDocument() {
        return countryDocument;
    }

    public void setCountryDocument(CountryDTO countryDocument) {
        this.countryDocument = countryDocument;
    }

    public ProvinceDTO getProvinceDocument() {
        return provinceDocument;
    }

    public void setProvinceDocument(ProvinceDTO provinceDocument) {
        this.provinceDocument = provinceDocument;
    }

    public Date getDateIssueDocument() {
        return dateIssueDocument == null ? null : new Date(dateIssueDocument.getTime());
    }

    public void setDateIssueDocument(Date dateIssueDocument) {
        this.dateIssueDocument = dateIssueDocument == null ? null : new Date(dateIssueDocument.getTime());
    }

    public EmissionOrganismDTO getIssuingBodyDocument() {
        return issuingBodyDocument;
    }

    public void setIssuingBodyDocument(EmissionOrganismDTO issuingBodyDocument) {
        this.issuingBodyDocument = issuingBodyDocument;
    }

    public Date getValidDateDocument() {
        return validDateDocument == null ? null : new Date(validDateDocument.getTime());
    }

    public void setValidDateDocument(Date validDateDocument) {
        this.validDateDocument = validDateDocument == null ? null : new Date(validDateDocument.getTime()); 
    }

    public SimulationAdressDTO getAddress() {
        return address;
    }

    public void setAddress(SimulationAdressDTO address) {
        this.address = address;
    }

    public SpouseDTO getSpouse() {
        return spouse;
    }

    public void setSpouse(SpouseDTO spouse) {
        this.spouse = spouse;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public ReferenceDTO getReference1() {
        return reference1;
    }

    public void setReference1(ReferenceDTO reference1) {
        this.reference1 = reference1;
    }

    public ReferenceDTO getReference2() {
        return reference2;
    }

    public void setReference2(ReferenceDTO reference2) {
        this.reference2 = reference2;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getFixPhoneType() {
        return fixPhoneType;
    }

    public void setFixPhoneType(String fixPhoneType) {
        this.fixPhoneType = fixPhoneType;
    }

    public BankDetailsDTO getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetailsDTO bankDetails) {
        this.bankDetails = bankDetails;
    }

}

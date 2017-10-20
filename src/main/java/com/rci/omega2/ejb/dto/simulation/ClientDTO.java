package com.rci.omega2.ejb.dto.simulation;

import java.math.BigDecimal;
import java.util.Date;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.CivilStateDTO;
import com.rci.omega2.ejb.dto.CountryDTO;
import com.rci.omega2.ejb.dto.DocumentTypeDTO;
import com.rci.omega2.ejb.dto.EconomicAtivityDTO;
import com.rci.omega2.ejb.dto.EducationDegreeDTO;
import com.rci.omega2.ejb.dto.EmissionOrganismDTO;
import com.rci.omega2.ejb.dto.EmployerSizeDTO;
import com.rci.omega2.ejb.dto.IndustrialSegmentDTO;
import com.rci.omega2.ejb.dto.LegalNatureDTO;
import com.rci.omega2.ejb.dto.PoliticalExpositionDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;

public class ClientDTO extends BaseDTO {
    /** serial version */
    private static final long serialVersionUID = 1L;

    private String id;
    private String cpfCnpj;
    private String name;
    private String email;
    private String phoneType;
    private PhoneDTO phone;
    private ProvinceDTO province;
    private CountryDTO country;
    private PersonTypeEnum typePerson;
    
    private String sex;
    private Date birthDate;
    private CivilStateDTO civilState;
    private PhoneDTO cellphone;
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
    private Date dateIssue;
    private EmissionOrganismDTO issuingBody;
    private Date dateValid;

    // PJ-DATA
    private LegalNatureDTO legalNature;
    private SimulationAdressDTO address;
    private EmployerSizeDTO companySize;
    private boolean ownSeat;
    private BigDecimal monthlyBilling;
    private IndustrialSegmentDTO economicActivityGroup;
    private EconomicAtivityDTO economicActivity;

    /** Agente Certificado */
    private CertifiedAgentDTO certifiedAgent;
    
    /** Cônjuge */
    private SpouseDTO spouse;
    /** Dados Profissionais */
    private CompanyDTO company;
    /** Referências */
    private ReferenceDTO reference1;
    private ReferenceDTO reference2;
    /** Referências Bancárias */
    private BankDetailsDTO bankDetails;
    /** Dados do veículo */
    private CarDetailsDTO carDetails;
    /** Guarantor 1 **/
    private GuarantorDTO guarantor1;
    /** Guarantor 2 **/
    private GuarantorDTO guarantor2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhoneDTO getPhone() {
        return phone;
    }

    public void setPhone(PhoneDTO phone) {
        this.phone = phone;
    }

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public PersonTypeEnum getTypePerson() {
        return typePerson;
    }

    public void setTypePerson(PersonTypeEnum typePerson) {
        this.typePerson = typePerson;
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

    public PhoneDTO getCellphone() {
        return cellphone;
    }

    public void setCellphone(PhoneDTO cellphone) {
        this.cellphone = cellphone;
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

    public Date getDateIssue() {
        return dateIssue == null ? null : new Date(dateIssue.getTime());
    }

    public void setDateIssue(Date dateIssue) {
        this.dateIssue = dateIssue == null ? null : new Date(dateIssue.getTime());
    }

    public EmissionOrganismDTO getIssuingBody() {
        return issuingBody;
    }

    public void setIssuingBody(EmissionOrganismDTO issuingBody) {
        this.issuingBody = issuingBody;
    }

    public Date getDateValid() {
        return dateValid == null ? null : new Date(dateValid.getTime());
    }

    public void setDateValid(Date dateValid) {
        this.dateValid = dateValid == null ? null : new Date(dateValid.getTime());
    }

    public LegalNatureDTO getLegalNature() {
        return legalNature;
    }

    public void setLegalNature(LegalNatureDTO legalNature) {
        this.legalNature = legalNature;
    }

    public SimulationAdressDTO getAddress() {
        return address;
    }

    public void setAddress(SimulationAdressDTO address) {
        this.address = address;
    }

    public EmployerSizeDTO getCompanySize() {
        return companySize;
    }

    public void setCompanySize(EmployerSizeDTO companySize) {
        this.companySize = companySize;
    }

    public boolean isOwnSeat() {
        return ownSeat;
    }

    public void setOwnSeat(boolean ownSeat) {
        this.ownSeat = ownSeat;
    }

    public BigDecimal getMonthlyBilling() {
        return monthlyBilling;
    }

    public void setMonthlyBilling(BigDecimal monthlyBilling) {
        this.monthlyBilling = monthlyBilling;
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

    public CertifiedAgentDTO getCertifiedAgent() {
        return certifiedAgent;
    }

    public void setCertifiedAgent(CertifiedAgentDTO certifiedAgent) {
        this.certifiedAgent = certifiedAgent;
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

    public CarDetailsDTO getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarDetailsDTO carDetails) {
        this.carDetails = carDetails;
    }

    public GuarantorDTO getGuarantor1() {
        return guarantor1;
    }

    public void setGuarantor1(GuarantorDTO guarantor1) {
        this.guarantor1 = guarantor1;
    }

    public GuarantorDTO getGuarantor2() {
        return guarantor2;
    }

    public void setGuarantor2(GuarantorDTO guarantor2) {
        this.guarantor2 = guarantor2;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public BankDetailsDTO getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetailsDTO bankDetails) {
        this.bankDetails = bankDetails;
    }

}

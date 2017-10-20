package com.rci.omega2.ejb.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.DossierDAO;
import com.rci.omega2.ejb.dao.ProposalDAO;
import com.rci.omega2.ejb.dto.AddressTypeDTO;
import com.rci.omega2.ejb.dto.BankDTO;
import com.rci.omega2.ejb.dto.BusinessRelationshipTypeDTO;
import com.rci.omega2.ejb.dto.CivilStateDTO;
import com.rci.omega2.ejb.dto.ColorDTO;
import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.CountryDTO;
import com.rci.omega2.ejb.dto.DocumentTypeDTO;
import com.rci.omega2.ejb.dto.EconomicAtivityDTO;
import com.rci.omega2.ejb.dto.EducationDegreeDTO;
import com.rci.omega2.ejb.dto.EmissionOrganismDTO;
import com.rci.omega2.ejb.dto.EmployerSizeDTO;
import com.rci.omega2.ejb.dto.FinanceTypeDTO;
import com.rci.omega2.ejb.dto.GuarantorTypeDTO;
import com.rci.omega2.ejb.dto.IncomeTypeDTO;
import com.rci.omega2.ejb.dto.IndustrialSegmentDTO;
import com.rci.omega2.ejb.dto.KinshipTypeDTO;
import com.rci.omega2.ejb.dto.LegalNatureDTO;
import com.rci.omega2.ejb.dto.OccupationDTO;
import com.rci.omega2.ejb.dto.PoliticalExpositionDTO;
import com.rci.omega2.ejb.dto.ProductCoeficientDTO;
import com.rci.omega2.ejb.dto.ProductDTO;
import com.rci.omega2.ejb.dto.ProfessionDTO;
import com.rci.omega2.ejb.dto.ProofIncomeTypeDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;
import com.rci.omega2.ejb.dto.RepackageDTO;
import com.rci.omega2.ejb.dto.ResidenceTypeDTO;
import com.rci.omega2.ejb.dto.SaleTypeDTO;
import com.rci.omega2.ejb.dto.SpecialTypeDTO;
import com.rci.omega2.ejb.dto.VehicleBrandDTO;
import com.rci.omega2.ejb.dto.simulation.BankDetailsDTO;
import com.rci.omega2.ejb.dto.simulation.CalculationDTO;
import com.rci.omega2.ejb.dto.simulation.CarDTO;
import com.rci.omega2.ejb.dto.simulation.CarDetailsDTO;
import com.rci.omega2.ejb.dto.simulation.ClientDTO;
import com.rci.omega2.ejb.dto.simulation.CompanyDTO;
import com.rci.omega2.ejb.dto.simulation.GuarantorDTO;
import com.rci.omega2.ejb.dto.simulation.InstalmentDTO;
import com.rci.omega2.ejb.dto.simulation.PhoneDTO;
import com.rci.omega2.ejb.dto.simulation.RateDTO;
import com.rci.omega2.ejb.dto.simulation.ReferenceDTO;
import com.rci.omega2.ejb.dto.simulation.ServiceDTO;
import com.rci.omega2.ejb.dto.simulation.SimulationAdressDTO;
import com.rci.omega2.ejb.dto.simulation.SimulationDTO;
import com.rci.omega2.ejb.dto.simulation.SimulationVehicleVersionDTO;
import com.rci.omega2.ejb.dto.simulation.SpouseDTO;
import com.rci.omega2.ejb.dto.simulation.VehicleAccessoriesDTO;
import com.rci.omega2.ejb.dto.simulation.VehicleOptionsDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.AppDtoUtils;
import com.rci.omega2.ejb.utils.AppOrderUtils;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.entity.AddressEntity;
import com.rci.omega2.entity.AddressTypeEntity;
import com.rci.omega2.entity.BankDetailEntity;
import com.rci.omega2.entity.BusinessRelationshipTypeEntity;
import com.rci.omega2.entity.CivilStateEntity;
import com.rci.omega2.entity.ColorEntity;
import com.rci.omega2.entity.CommissionEntity;
import com.rci.omega2.entity.CountryEntity;
import com.rci.omega2.entity.CustomerEntity;
import com.rci.omega2.entity.CustomerReferenceEntity;
import com.rci.omega2.entity.CustomerSpouseEntity;
import com.rci.omega2.entity.DocumentTypeEntity;
import com.rci.omega2.entity.DossierEntity;
import com.rci.omega2.entity.DossierVehicleAccessoryEntity;
import com.rci.omega2.entity.DossierVehicleEntity;
import com.rci.omega2.entity.DossierVehicleOptionEntity;
import com.rci.omega2.entity.EconomicActivityEntity;
import com.rci.omega2.entity.EducationDegreeEntity;
import com.rci.omega2.entity.EmissionOrganismEntity;
import com.rci.omega2.entity.EmployerEntity;
import com.rci.omega2.entity.EmployerSizeEntity;
import com.rci.omega2.entity.FinanceTypeEntity;
import com.rci.omega2.entity.GuarantorEntity;
import com.rci.omega2.entity.GuarantorReferenceEntity;
import com.rci.omega2.entity.GuarantorSpouseEntity;
import com.rci.omega2.entity.GuarantorTypeEntity;
import com.rci.omega2.entity.IncomeTypeEntity;
import com.rci.omega2.entity.IndustrialSegmentEntity;
import com.rci.omega2.entity.InstalmentEntity;
import com.rci.omega2.entity.KinshipTypeEntity;
import com.rci.omega2.entity.LegalNatureEntity;
import com.rci.omega2.entity.OccupationEntity;
import com.rci.omega2.entity.PhoneEntity;
import com.rci.omega2.entity.PoliticalExpositionEntity;
import com.rci.omega2.entity.ProductEntity;
import com.rci.omega2.entity.ProfessionEntity;
import com.rci.omega2.entity.ProofIncomeTypeEntity;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalServiceEntity;
import com.rci.omega2.entity.ProposalTaxEntity;
import com.rci.omega2.entity.RepackageEntity;
import com.rci.omega2.entity.ResidenceTypeEntity;
import com.rci.omega2.entity.SaleTypeEntity;
import com.rci.omega2.entity.SpecialVehicleTypeEntity;
import com.rci.omega2.entity.VehicleBrandEntity;
import com.rci.omega2.entity.VehicleVersionEntity;
import com.rci.omega2.entity.enumeration.AccountTypeEnum;
import com.rci.omega2.entity.enumeration.PersonGenderEnum;
import com.rci.omega2.entity.enumeration.PersonalPhoneTypeEnum;
import com.rci.omega2.entity.enumeration.PhoneTypeEnum;
import com.rci.omega2.entity.enumeration.VehicleTypeEnum;

@Stateless
public class MyProposalBO extends BaseBO {
    private static final Logger LOGGER = LogManager.getLogger(MyProposalBO.class);
    
    public String findAdp(Long dossierId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAdp ");
            
            DossierDAO dao = daoFactory(DossierDAO.class);
            String temp = dao.findAdp(dossierId);
            
            LOGGER.debug(" >> END findAdp ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public SimulationDTO findDossier(Long dossierId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findDossier ");
            
            DossierDAO dossierDAO = daoFactory(DossierDAO.class);
            DossierEntity entity = dossierDAO.findDossierRootFetch(dossierId);
            
            ProposalDAO proposalDAO = daoFactory(ProposalDAO.class);
            List<ProposalEntity> proposals = proposalDAO.findProposalByDossierAndAdp(entity.getId(), entity.getAdp());
            proposals = AppOrderUtils.ordinateProposalList(proposals);
            
            SimulationDTO dto = populateSimulationDTO(entity, proposals);
            
            LOGGER.debug(" >> END findDossier ");
            return dto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private SimulationDTO populateSimulationDTO(DossierEntity entity, List<ProposalEntity> proposals) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateSimulationDTO ");
        
        SimulationDTO dto = new SimulationDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setCar(populateCarDTO(entity));
        dto.setClient(populateClientDTO(entity));
        dto.setSaleType(populateSaleTypeDTO(entity.getSaleType()));
        dto.setTc(entity.getTcExempt());
        dto.setVizualization(Boolean.TRUE);
        dto.setBrand(populateVehicleBrandDTO(entity));
        dto.setCalculations(populateListCalculationDTO(entity, proposals, dto));
        dto.setSpecialTypes(populateListSpecialTypeDTO(entity));
        dto.setShowRoomSemiNews(entity.getUsedVehicleShowroom());
        dto.setShowNewOnes(!GeneralUtils.isSaleTypeNewVehicle(entity.getSaleType()));
        dto.setDossierNumber(adjustDossierNumber(entity));
        dto.setDossierStatus(entity.getDossierStatus().getId());
        dto.setAdp(entity.getAdp());
        dto.setShowDocuments(GeneralUtils.showDocumentList(entity.getDossierStatus()));
        
        if(!AppUtil.isNullOrEmpty(entity.getCertifiedAgent())){
            dto.setCertifiedAgent(CriptoUtilsOmega2.encrypt(entity.getCertifiedAgent()));
        }
        
        LOGGER.debug(" >> END populateSimulationDTO ");
        return dto;
    }
    
    private VehicleBrandDTO populateVehicleBrandDTO(DossierEntity dossier) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateVehicleBrandDTO ");
        
        VehicleBrandEntity entity = dossier.getStructure().getVehicleBrand();
        
        VehicleBrandDTO dto = new VehicleBrandDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateVehicleBrandDTO ");
        return dto;
    }

    private List<SpecialTypeDTO> populateListSpecialTypeDTO(DossierEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateListSpecialTypeDTO ");
        
        List<SpecialTypeDTO> lista = new ArrayList<>();
        for (SpecialVehicleTypeEntity item : entity.getListSpecialVehicleType()) {
            SpecialTypeDTO dto = new SpecialTypeDTO();
            dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
            dto.setDescription(item.getDescription());
            
            lista.add(dto);
        }
        
        LOGGER.debug(" >> END populateListSpecialTypeDTO ");
        return lista;
    }
    
    private CarDTO populateCarDTO(DossierEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateCarDTO ");
        VehicleVersionEntity vehicleVersion = entity.getDossierVehicle().getVehicleVersion();
        
        CarDTO dto = new CarDTO();
        
        dto.setId(CriptoUtilsOmega2.encrypt(vehicleVersion.getVehicleModel().getId()));
        dto.setDescription(vehicleVersion.getVehicleModel().getDescription());
        dto.setSelected(Boolean.TRUE);
        dto.setGender(vehicleVersion.getVehicleGender().name());
        dto.setVehicleType(VehicleTypeEnum.NOVO.name());
        
        if(!GeneralUtils.isSaleTypeNewVehicle(entity.getSaleType())){
            dto.setVehicleType(VehicleTypeEnum.USADO.name());
        }
        
        dto.setVersion(populateSimulationVehicleVersionDTO(entity));

        LOGGER.debug(" >> END populateCarDTO ");
        return dto;
    }
    
    
    private ClientDTO populateClientDTO(DossierEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateClientDTO ");
        CustomerEntity customer = entity.getCustomer();
        
        ClientDTO dto = new ClientDTO();
        
        dto.setId(CriptoUtilsOmega2.encrypt(customer.getId()));
        dto.setTypePerson(customer.getPersonType());
        
        dto.setName(customer.getNameClient());
        dto.setCpfCnpj(customer.getCpfCnpj());
        
        dto.setSex(PersonGenderEnum.MASCULINO.name());
        if(!AppUtil.isNullOrEmpty(customer.getPersonGender())){
            dto.setSex(customer.getPersonGender().name());
        }
        
        dto.setBirthDate(customer.getDateBirth());
        dto.setCivilState(populateCivilStateDTO(customer.getCivilState()));
        
        dto.setPhoneType(PersonalPhoneTypeEnum.PROPRIO.name());
        if(!AppUtil.isNullOrEmpty(customer.getPersonalPhoneType())){
            dto.setPhoneType(customer.getPersonalPhoneType().name());
        }
        
        dto.setEmail(customer.getEmail());
        dto.setCountry(populateCountryDTO(customer.getCountry()));
        dto.setProvince(AppDtoUtils.populateProvinceDTO(customer.getProvince()));
        dto.setNaturalness(customer.getNaturalness());
        dto.setPoliticalExposition(populatePoliticalExpositionDTO(customer.getPoliticalExposition()));
        dto.setEducationDegree(populateEducationDegreeDTO(customer.getEducationDegree()));
        dto.setHandicapped(customer.getHandicapped());
        dto.setFathersName(customer.getFatherName());
        dto.setMothersName(customer.getMotherName());
        dto.setDocumentType(populateDocumentTypeDTO(customer.getDocumentType()));
        dto.setNumberDocument(customer.getDocumentNumber());
        dto.setCountryDocument(populateCountryDTO(customer.getDocumentCountry()));
        dto.setProvinceDocument(AppDtoUtils.populateProvinceDTO(customer.getDocumentProvince()));
        dto.setDateIssue(customer.getEmissionDate());
        dto.setIssuingBody(populateEmissionOrganismDTO(customer.getEmissionOrganism()));
        dto.setDateValid(customer.getDocumentValidate());
        dto.setAddress(populateSimulationAdressDTO(customer.getAddress(), customer.getResidenceType(), customer.getAddressSince(), customer.getMailingAddressType()));
        
        if(AppUtil.isNullOrEmpty(dto.getAddress())){
            dto.setAddress(new SimulationAdressDTO());
            dto.getAddress().setProvince(new ProvinceDTO());
        } else if(AppUtil.isNullOrEmpty(dto.getAddress().getProvince())){
            dto.getAddress().setProvince(new ProvinceDTO());
        }
        
        // PJ
        dto.setCompanySize(populateEmployerSizeDTO(customer.getCustomerSize()));
        dto.setLegalNature(populateLegalNatureDTO(customer.getLegalNature()));
        dto.setEconomicActivity(populateEconomicAtivityDTO(customer.getCustomerEconomicActivity()));
        dto.setEconomicActivityGroup(populateIndustrialSegmentDTO(customer.getCustomerIndustrialSegment()));
        dto.setOwnSeat(customer.getBuildingOwner());
        dto.setMonthlyBilling(customer.getIncome());
        // PJ
        
        dto.setSpouse(populateSpouseDTO(customer.getCustomerSpouse()));
        dto.setCompany(populateCustomerCompanyDTO(customer));
        
        populateCustomerReference(customer, dto);
        dto.setBankDetails(populateBankDetailsDTO(customer.getBankDetail()));
        
        dto.setCarDetails(populateCarDetailsDTO(entity.getDossierVehicle()));
        
        populateGuarantors(entity, dto);
        populatePhones(customer, dto);
        
        LOGGER.debug(" >> END populateClientDTO ");
        return dto;
    }

    private SpouseDTO populateSpouseDTO(CustomerSpouseEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateSpouseDTO ");
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        SpouseDTO dto = new SpouseDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setName(entity.getNameClient());
        dto.setCpf(entity.getCpf());
        
        dto.setSex(PersonGenderEnum.MASCULINO.name());
        if(!AppUtil.isNullOrEmpty(entity.getPersonGender())){
            dto.setSex(entity.getPersonGender().name());
        }
        
        dto.setBirthDate(entity.getDateBirth());
        dto.setProvince(AppDtoUtils.populateProvinceDTO(entity.getProvince()));
        dto.setNumberDocument(entity.getDocumentNumber());
        dto.setIssuingBody(populateEmissionOrganismDTO(entity.getEmissionOrganism()));
        dto.setCompany(new CompanyDTO());
        dto.getCompany().setName(entity.getEmployerName());
        dto.getCompany().setOccupation(populateOccupationDTO(entity.getOccupation()));
        dto.getCompany().setProfession(populateProfessionDTO(entity.getProfession()));
        dto.getCompany().setAdmissionDate(entity.getAdmissionDate());
        dto.getCompany().setMonthlyIncome(entity.getIncome());
        dto.getCompany().setComercialPhone(new PhoneDTO());
        dto.getCompany().getComercialPhone().setNumber(GeneralUtils.stringNotNull(entity.getDdd()) + GeneralUtils.stringNotNull(entity.getPhone()));
        dto.getCompany().getComercialPhone().setExtension(entity.getExtensionLine());
        
        LOGGER.debug(" >> END populateSpouseDTO ");
        return dto;
    }
    
    private SpouseDTO populateSpouseDTO(GuarantorSpouseEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateSpouseDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        SpouseDTO dto = new SpouseDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setName(entity.getNameClient());
        dto.setCpf(entity.getCpf());
        
        dto.setSex(PersonGenderEnum.MASCULINO.name());
        if(!AppUtil.isNullOrEmpty(entity.getPersonGender())){
            dto.setSex(entity.getPersonGender().name());
        }
        
        dto.setBirthDate(entity.getDateBirth());
        dto.setProvince(AppDtoUtils.populateProvinceDTO(entity.getProvince()));
        dto.setNumberDocument(entity.getDocumentNumber());
        dto.setIssuingBody(populateEmissionOrganismDTO(entity.getEmissionOrganism()));
        dto.setCompany(new CompanyDTO());
        dto.getCompany().setName(entity.getEmployerName());
        dto.getCompany().setOccupation(populateOccupationDTO(entity.getOccupation()));
        dto.getCompany().setProfession(populateProfessionDTO(entity.getProfession()));
        dto.getCompany().setAdmissionDate(entity.getAdmissionDate());
        dto.getCompany().setMonthlyIncome(entity.getIncome());
        dto.getCompany().setComercialPhone(new PhoneDTO());
        dto.getCompany().getComercialPhone().setNumber(GeneralUtils.stringNotNull(entity.getDdd()) + GeneralUtils.stringNotNull(entity.getPhone()));
        dto.getCompany().getComercialPhone().setExtension(entity.getExtensionLine());
        
        LOGGER.debug(" >> END populateSpouseDTO ");
        return dto;
    }

    private CarDetailsDTO populateCarDetailsDTO(DossierVehicleEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateCarDetailsDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        CarDetailsDTO dto = new CarDetailsDTO();
        dto.setChassiNumber(entity.getChassi());
        dto.setRegisterNumber(entity.getRegisterNumber());
        dto.setRegistrationProvince(AppDtoUtils.populateProvinceDTO(entity.getRegisterProvince()));
        dto.setLicensingProvince(AppDtoUtils.populateProvinceDTO(entity.getLicenseProvince()));
        dto.setVehicleColor(populateColorDTO(entity.getColor()));
        dto.setRenavam(entity.getRenavam());
        dto.setManufactureYear(entity.getManufactureYear());
        
        dto.setVehicleOrigin("N");
        if(!AppUtil.isNullOrEmpty(entity.getNationalCar())){
            dto.setVehicleOrigin(entity.getNationalCar() ? "N" : "I");
        }
        
        LOGGER.debug(" >> END populateCarDetailsDTO ");
        return dto;
    }

    private ColorDTO populateColorDTO(ColorEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateColorDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        ColorDTO dto = new ColorDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateColorDTO ");
        return dto;
    }
    
    private void populateGuarantors(DossierEntity entity, ClientDTO dto) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateGuarantors ");
        
        for (GuarantorEntity guarantorEntity : AppOrderUtils.ordinateGuarantorList(entity.getListGuarantor())) {
            if(AppUtil.isNullOrEmpty(dto.getGuarantor1())){
                dto.setGuarantor1(populateGuarantorDTO(guarantorEntity));
            } else {
                dto.setGuarantor2(populateGuarantorDTO(guarantorEntity));
            }
        }
        
        LOGGER.debug(" >> END populateGuarantors ");
    }

    private EconomicAtivityDTO populateEconomicAtivityDTO(EconomicActivityEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateEconomicAtivityDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        EconomicAtivityDTO dto = new EconomicAtivityDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateEconomicAtivityDTO ");
        return dto;
    }

    private IndustrialSegmentDTO populateIndustrialSegmentDTO(IndustrialSegmentEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateIndustrialSegmentDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        IndustrialSegmentDTO dto = new IndustrialSegmentDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateIndustrialSegmentDTO ");
        return dto;
    }

    private EmployerSizeDTO populateEmployerSizeDTO(EmployerSizeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateEmployerSizeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        EmployerSizeDTO dto = new EmployerSizeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateEmployerSizeDTO ");
        return dto;
    }

    private SimulationAdressDTO populateSimulationAdressDTO(AddressEntity entity, ResidenceTypeEntity residenceType, Date addressSince, AddressTypeEntity mailingAddressType) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateSimulationAdressDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        SimulationAdressDTO dto = new SimulationAdressDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setCep(entity.getPostCode());
        dto.setNumber(entity.getNumber());
        dto.setAddress(entity.getAddress());
        dto.setComplement(entity.getComplement());
        dto.setNeighborhood(entity.getNeighborhood());
        dto.setCity(entity.getCity());        
        dto.setProvince(AppDtoUtils.populateProvinceDTO(entity.getProvince()));
        dto.setResidenceType(populateResidenceTypeDTO(residenceType));
        dto.setResidesInAddressSince(addressSince);
        dto.setMailingAddress(populateAddressTypeDTO(mailingAddressType));
        
        LOGGER.debug(" >> END populateSimulationAdressDTO ");
        return dto;
    }

    private AddressTypeDTO populateAddressTypeDTO(AddressTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateAddressTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        AddressTypeDTO dto = new AddressTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateAddressTypeDTO ");
        return dto;
    }

    private ResidenceTypeDTO populateResidenceTypeDTO(ResidenceTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateResidenceTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        ResidenceTypeDTO dto = new ResidenceTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateResidenceTypeDTO ");
        return dto;
    }

    private LegalNatureDTO populateLegalNatureDTO(LegalNatureEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateLegalNatureDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        LegalNatureDTO dto = new LegalNatureDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        dto.setImportCode(CriptoUtilsOmega2.encrypt(entity.getImportCode()));
        
        LOGGER.debug(" >> END populateLegalNatureDTO ");
        return dto;
    }
    
    private GuarantorDTO populateGuarantorDTO(GuarantorEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateGuarantorDTO ");
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        
        GuarantorDTO dto = new GuarantorDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(String.valueOf(entity.getId())));
        dto.setGuarantorType(populateGuarantorTypeDTO(entity.getGuarantorType()));
        dto.setKinshipType(populateKinshipTypeDTO(entity.getKinshipType()));
        dto.setBusinessRelashionshipType(populateBusinessRelationshipTypeDTO(entity.getBusinessRelationshipType()));
        dto.setName(entity.getNameClient());
        dto.setCpf(entity.getCpfCnpj());
        
        dto.setSex(PersonGenderEnum.MASCULINO.name());
        if(!AppUtil.isNullOrEmpty(entity.getPersonGender())){
            dto.setSex(entity.getPersonGender().name());
        }

        dto.setBirthDate(entity.getDateBirth());
        dto.setCivilState(populateCivilStateDTO(entity.getCivilState()));
        
        dto.setFixPhoneType(PersonalPhoneTypeEnum.PROPRIO.name());
        if(!AppUtil.isNullOrEmpty(entity.getPersonalPhoneType())){
            dto.setFixPhoneType(entity.getPersonalPhoneType().name());
        }

        dto.setEmail(entity.getEmail());
        dto.setCountry(populateCountryDTO(entity.getCountry()));
        dto.setProvince(AppDtoUtils.populateProvinceDTO(entity.getProvince()));
        dto.setNaturalness(entity.getNaturalness());
        dto.setPoliticalExposition(populatePoliticalExpositionDTO(entity.getPoliticalExposition()));
        dto.setEducationDegree(populateEducationDegreeDTO(entity.getEducationDegree()));
        dto.setHandicapped(entity.getHandicapped());
        dto.setFathersName(entity.getFatherName());
        dto.setMothersName(entity.getMotherName());
        dto.setDocumentType(populateDocumentTypeDTO(entity.getDocumentType()));
        dto.setNumberDocument(entity.getDocumentNumber());
        dto.setCountryDocument(populateCountryDTO(entity.getDocumentCountry()));
        dto.setProvinceDocument(AppDtoUtils.populateProvinceDTO(entity.getDocumentProvince()));
        dto.setDateIssueDocument(entity.getEmissionDate());
        dto.setIssuingBodyDocument(populateEmissionOrganismDTO(entity.getEmissionOrganism()));
        dto.setAddress(populateSimulationAdressDTO(entity.getAddress(), entity.getResidenceType(), entity.getAddressSince(), entity.getMailingAddressType()));
        dto.setCompany(populateGuarantorCompanyDTO(entity));
        dto.setBankDetails(populateBankDetailsDTO(entity.getBankDetail()));
        dto.setValidDateDocument(entity.getDocumentValidate());
        
        dto.setSpouse(populateSpouseDTO(entity.getGuarantorSpouse()));
        
        populateGuarantorReference(entity, dto);
        populatePhones(entity, dto);
        
        LOGGER.debug(" >> END populateGuarantorDTO ");
        return dto;
    }

    private CompanyDTO populateCustomerCompanyDTO(CustomerEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateCustomerCompanyDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        CompanyDTO dto = populateCompanyDTO(entity.getEmployer());
        if(AppUtil.isNullOrEmpty(dto)){
            dto = new CompanyDTO();
        }
        
        dto.setAddress(populateSimulationAdressDTO(entity.getEmployerAddress(), null, null, null));
        dto.setMonthlyIncome(entity.getIncome());
        dto.setOtherIncomes(entity.getOtherIncome());
        
        LOGGER.debug(" >> END populateCustomerCompanyDTO ");
        return dto;
    }
    
    private CompanyDTO populateGuarantorCompanyDTO(GuarantorEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateGuarantorCompanyDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        CompanyDTO dto = populateCompanyDTO(entity.getEmployer());
        if(AppUtil.isNullOrEmpty(dto)){
            dto = new CompanyDTO();
        }
        dto.setAddress(populateSimulationAdressDTO(entity.getEmployerAddress(), null, null, null));
        dto.setMonthlyIncome(entity.getIncome());
        dto.setOtherIncomes(entity.getOtherIncome());
        
        LOGGER.debug(" >> END populateGuarantorCompanyDTO ");
        return dto;
    }
    
    private CompanyDTO populateCompanyDTO(EmployerEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateCompanyDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        CompanyDTO dto = new CompanyDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setName(entity.getEmployerName());
        dto.setOccupation(populateOccupationDTO(entity.getOccupation()));
        dto.setProfession(populateProfessionDTO(entity.getProfession()));
        dto.setCnpj(entity.getCnpj());
        dto.setEconomicActivityGroup(populateIndustrialSegmentDTO(entity.getIndustrialSegment()));
        dto.setEconomicActivity(populateEconomicAtivityDTO(entity.getEconomicActivity()));
        dto.setSize(populateEmployerSizeDTO(entity.getEmployerSize()));
        dto.setAdmissionDate(entity.getAdmissionDate());
        dto.setPatrimony(entity.getValueAssets());
        dto.setIncomeType(populateIncomeTypeDTO(entity.getIncomeType()));
        dto.setProofIncomeType(populateProofIncomeTypeDTO(entity.getProofIncomeType()));
        
        LOGGER.debug(" >> END populateCompanyDTO ");
        return dto;
    }

    private ProofIncomeTypeDTO populateProofIncomeTypeDTO(ProofIncomeTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateProofIncomeTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        ProofIncomeTypeDTO dto = new ProofIncomeTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateProofIncomeTypeDTO ");
        return dto;
    }

    private IncomeTypeDTO populateIncomeTypeDTO(IncomeTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateIncomeTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        IncomeTypeDTO dto = new IncomeTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateIncomeTypeDTO ");
        return dto;
    }

    private ProfessionDTO populateProfessionDTO(ProfessionEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateProfessionDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        ProfessionDTO dto = new ProfessionDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateProfessionDTO ");
        return dto;
    }

    private OccupationDTO populateOccupationDTO(OccupationEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateOccupationDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        OccupationDTO dto = new OccupationDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateOccupationDTO ");
        return dto;
    }

    private EmissionOrganismDTO populateEmissionOrganismDTO(EmissionOrganismEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateEmissionOrganismDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        EmissionOrganismDTO dto = new EmissionOrganismDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateEmissionOrganismDTO ");
        return dto; 
    }

    private DocumentTypeDTO populateDocumentTypeDTO(DocumentTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateDocumentTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        DocumentTypeDTO dto = new DocumentTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> INIT populateDocumentTypeDTO ");
        return dto; 
    }

    private EducationDegreeDTO populateEducationDegreeDTO(EducationDegreeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateEducationDegreeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        EducationDegreeDTO dto = new EducationDegreeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateEducationDegreeDTO ");
        return dto; 
    }

    private PoliticalExpositionDTO populatePoliticalExpositionDTO(PoliticalExpositionEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populatePoliticalExpositionDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        PoliticalExpositionDTO dto = new PoliticalExpositionDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populatePoliticalExpositionDTO ");
        return dto; 
    }

    private CountryDTO populateCountryDTO(CountryEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateCountryDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        CountryDTO dto = new CountryDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateCountryDTO ");
        return dto; 
    }

    private CivilStateDTO populateCivilStateDTO(CivilStateEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateCivilStateDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        CivilStateDTO dto = new CivilStateDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateCivilStateDTO ");
        return dto;    
    }

    private BusinessRelationshipTypeDTO populateBusinessRelationshipTypeDTO(BusinessRelationshipTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateBusinessRelationshipTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        BusinessRelationshipTypeDTO dto = new BusinessRelationshipTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        dto.setImportCode(entity.getImportCode());
        
        LOGGER.debug(" >> END populateBusinessRelationshipTypeDTO ");
        return dto;    
    }

    private KinshipTypeDTO populateKinshipTypeDTO(KinshipTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateKinshipTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        KinshipTypeDTO dto = new KinshipTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        dto.setImportCode(entity.getImportCode());
        
        LOGGER.debug(" >> END populateKinshipTypeDTO ");
        return dto;
    }

    private GuarantorTypeDTO populateGuarantorTypeDTO(GuarantorTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateGuarantorTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        GuarantorTypeDTO dto = new GuarantorTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateGuarantorTypeDTO ");
        return dto;
    }

    private BankDetailsDTO populateBankDetailsDTO(BankDetailEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateBankDetailsDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        
        BankDetailsDTO dto = new BankDetailsDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        
        if(!AppUtil.isNullOrEmpty(entity.getBank()) && !AppUtil.isNullOrEmpty(entity.getBank().getId())){
            dto.setBank(new BankDTO());
            dto.getBank().setId(CriptoUtilsOmega2.encrypt(entity.getBank().getId()));
            dto.getBank().setImportCode(entity.getBank().getImportCode());
        }
        
        dto.setBranch(GeneralUtils.stringNotNull(entity.getBranch()));
        dto.setBranchDigit(GeneralUtils.stringNotNull(entity.getBranchDigit()));
        
        dto.setAccount(GeneralUtils.stringNotNull(entity.getAccount()));
        dto.setAccountDigit(GeneralUtils.stringNotNull(entity.getAccountDigit()));
        
        dto.setCustomerSince(entity.getAccountOpeningDate());
        dto.setPhoneBranch(new PhoneDTO());
        dto.getPhoneBranch().setNumber(GeneralUtils.stringNotNull(entity.getDdd()) + GeneralUtils.stringNotNull(entity.getPhone()));
        
        dto.setAccountType(AccountTypeEnum.CORRENTE.name());
        if(!AppUtil.isNullOrEmpty(entity.getAccountType())){
            dto.setAccountType(entity.getAccountType().name());
        }
        
        LOGGER.debug(" >> END populateBankDetailsDTO ");
        return dto;
    }

    private SaleTypeDTO populateSaleTypeDTO(SaleTypeEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateSaleTypeDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        
        SaleTypeDTO dto = new SaleTypeDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateSaleTypeDTO ");
        return dto;
        
    }
    
    private List<CalculationDTO> populateListCalculationDTO(DossierEntity entity, List<ProposalEntity> proposals, SimulationDTO simulation) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateListCalculationDTO ");
        
        ProposalDAO proposalDAO = daoFactory(ProposalDAO.class);
        
        List<CalculationDTO> lista = new ArrayList<>();
        
        try {
            for (ProposalEntity itemTemp : proposals) {
                
                ProposalEntity item = proposalDAO.findProposalRootFetch(itemTemp.getId());
                
                CalculationDTO dto = new CalculationDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setFinancialType(populateFinanceTypeDTO(item.getFinanceType()));
                dto.setFinancialTable(populateProductDTO(item.getProduct()));
                dto.setCoeficiente(populateProductCoeficientDTO(item));
                dto.setEntranceValue(item.getDeposit());
                dto.setDelay(item.getDelayValue());
                dto.setRates(populateListRateDTO(item.getListProposalTax()));
                dto.setCommission(populateCommissionDTO(item.getCommission()));
                dto.setTerm(item.getInstalmentQuantity());
                dto.setFinancedAmount(item.getFinancedAmount());
                dto.setInstalmentAmount(item.getInstalmentAmount());
                if(AppUtil.isNullOrEmpty(item.getMontlyRate())){
                    dto.setMontlyRate(BigDecimal.ZERO);
                } else {
                    dto.setMontlyRate(item.getMontlyRate().movePointRight(2));
                }
                
                dto.setServices(populateServiceDTO(item.getListProposalService()));
                dto.setInstallments(populateInstalmentDTO(item.getListInstalment()));
                dto.setRepackage(populateRepackageDTO(item.getRepackage()));
                dto.setCreditReport(item.getCreditReport());
                dto.setStoreControl(item.getStoreControl());
                
                lista.add(dto);
                
                dto.setSelected(verifySelected(item));
                if(dto.getSelected()){
                    simulation.setCalculationSelected(dto);
                }
                
                totalValueCalculate(dto, entity, item);
            }
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
        LOGGER.debug(" >> END populateListCalculationDTO ");
        return lista;
    }
    
    private Boolean verifySelected(ProposalEntity item){
        
        LOGGER.debug(" >> INIT verifySelected ");
        
        if(!AppUtil.isNullOrEmpty(item.getAdp())){
            return Boolean.TRUE;
        }
        
        LOGGER.debug(" >> INIT verifySelected ");
        return item.getExibitionMain();
    }
    
    private RepackageDTO populateRepackageDTO(RepackageEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateRepackageDTO ");
        
        if(AppUtil.isNullOrEmpty(entity)){
            return null;
        }
        
        RepackageDTO dto = new RepackageDTO();
        
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setImportCode(entity.getImportCode());
        dto.setDuration(entity.getDuration());
        
        LOGGER.debug(" >> END populateRepackageDTO ");
        return dto;
    }

    private List<RateDTO> populateListRateDTO(Set<ProposalTaxEntity> listProposalTax) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateListRateDTO ");
        
        List<RateDTO> lista = new ArrayList<>();
        for (ProposalTaxEntity item : listProposalTax) {
            RateDTO dto = new RateDTO();
            dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
            dto.setValue(item.getAmount());
            dto.setTaxType(item.getTaxType().name());
            
            if(!AppUtil.isNullOrEmpty(item.getProvince())){
                dto.setProvince(new ProvinceDTO());
                dto.getProvince().setId(CriptoUtilsOmega2.encrypt(item.getProvince().getId()));
                dto.getProvince().setDescription(item.getProvince().getDescription());
            }
            
            lista.add(dto);
        }

        LOGGER.debug(" >> END populateListRateDTO ");
        return lista;
    }

    private void totalValueCalculate(CalculationDTO dto, DossierEntity entity, ProposalEntity proposal) {
        
        LOGGER.debug(" >> INIT totalValueCalculate ");
        
        BigDecimal totalValue = BigDecimal.ZERO;
        
        if(!AppUtil.isNullOrEmpty(entity.getVehiclePrice())){
            totalValue = totalValue.add(entity.getVehiclePrice());
        }
        
        for (DossierVehicleAccessoryEntity item : entity.getListDossierVehicleAccessory()) {
            if(!AppUtil.isNullOrEmpty(item.getAmount())){
                totalValue = totalValue.add(item.getAmount());
            }
        }
        
        for (DossierVehicleOptionEntity item : entity.getListDossierVehicleOption()) {
            if(!AppUtil.isNullOrEmpty(item.getAmount())){
                totalValue = totalValue.add(item.getAmount());
            }
        }
        
        for (ProposalTaxEntity item : proposal.getListProposalTax()) {
            if(!AppUtil.isNullOrEmpty(item.getAmount())){
                totalValue = totalValue.add(item.getAmount());
            }
        }
        
        for (ProposalServiceEntity item : proposal.getListProposalService()) {
            if(!AppUtil.isNullOrEmpty(item.getAmount())){
                totalValue = totalValue.add(item.getAmount());
            }
        }
        
        dto.setTotalValue(totalValue);
        
        LOGGER.debug(" >> END totalValueCalculate ");
    }

    private List<InstalmentDTO> populateInstalmentDTO(Set<InstalmentEntity> instalments){
        
        LOGGER.debug(" >> INIT populateInstalmentDTO ");
        
        List<InstalmentDTO> lista = new ArrayList<>();
        
        for (InstalmentEntity item : instalments) {
            InstalmentDTO dto = new InstalmentDTO();
            dto.setNumberInstallment(item.getInstalment());
            dto.setDueDate(item.getInstalmentDate());
            dto.setAmount(item.getAmount());

            lista.add(dto);
        }
        
        LOGGER.debug(" >> END populateInstalmentDTO ");
        return AppOrderUtils.ordinateInstalmentDTOList(lista);
    }
    
    private List<ServiceDTO> populateServiceDTO(Set<ProposalServiceEntity> services) throws UnexpectedException{
        
        LOGGER.debug(" >> INIT populateServiceDTO ");
        
        List<ServiceDTO> lista = new ArrayList<>();
        
        for (ProposalServiceEntity item : services) {
            ServiceDTO dto = new ServiceDTO();
            dto.setId(CriptoUtilsOmega2.encrypt(item.getService().getId()));
            dto.setDescription(item.getService().getDescription());
            dto.setAmount(item.getAmount());
            dto.setServiceTypeId(item.getService().getServiceType().getId());
            
            lista.add(dto);
        }
        
        LOGGER.debug(" >> END populateServiceDTO ");
        return lista;
    }
    
    private CommissionDTO populateCommissionDTO(CommissionEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateCommissionDTO ");
        
        CommissionDTO dto = new CommissionDTO();
        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());
        
        LOGGER.debug(" >> END populateCommissionDTO ");
        return dto;
    }

    private ProductCoeficientDTO populateProductCoeficientDTO(ProposalEntity entity) {
        
        LOGGER.debug(" >> INIT populateProductCoeficientDTO ");
        
        ProductCoeficientDTO dto = new ProductCoeficientDTO();

        dto.setCoefficient(entity.getCoefficient());
        dto.setTaxCoefficient(entity.getMontlyRate());
        dto.setDepositPercent(entity.getDeposit());
        
        LOGGER.debug(" >> END populateProductCoeficientDTO ");
        return dto;
    }

    private ProductDTO populateProductDTO(ProductEntity product) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateProductDTO ");
        
        ProductDTO dto = new ProductDTO();
        dto.setProductId(CriptoUtilsOmega2.encrypt(product.getId()));
        dto.setDescription(product.getDescription());
        dto.setProductInformation(product.getProductInformation());
        
        LOGGER.debug(" >> END populateProductDTO ");
        return dto;
    }

    private FinanceTypeDTO populateFinanceTypeDTO(FinanceTypeEntity entity) throws UnexpectedException{
        
        LOGGER.debug(" >> INIT populateFinanceTypeDTO ");
        
        FinanceTypeDTO dto = new FinanceTypeDTO();
        dto.setFinanceTypeId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());

        LOGGER.debug(" >> END populateFinanceTypeDTO ");
        return dto;
    }
    
    private String adjustDossierNumber(DossierEntity entity){
        
        LOGGER.debug(" >> INIT adjustDossierNumber ");
        
        StringBuilder dossierNumber = new StringBuilder();
        if(AppUtil.isNullOrEmpty(entity.getImportCodeOmega())){
            dossierNumber.append(entity.getId());
        } else {
            dossierNumber.append(entity.getImportCodeOmega());
        }
        
        LOGGER.debug(" >> END adjustDossierNumber ");
        return dossierNumber.toString();
    }
    
    private SimulationVehicleVersionDTO populateSimulationVehicleVersionDTO(DossierEntity entity) throws UnexpectedException{
        
        LOGGER.debug(" >> INIT populateSimulationVehicleVersionDTO ");
        
        VehicleVersionEntity vehicleVersion = entity.getDossierVehicle().getVehicleVersion();
        
        SimulationVehicleVersionDTO dto = new SimulationVehicleVersionDTO();
        
        dto.setId(CriptoUtilsOmega2.encrypt(vehicleVersion.getId()));
        dto.setDescription(vehicleVersion.getDescription());
        dto.setFipe(vehicleVersion.getFipe());
        dto.setYearManufacture(vehicleVersion.getManufactureYear());
        dto.setYearModel(vehicleVersion.getModelYear());
        dto.setPrice(entity.getVehiclePrice());
        dto.setOptions(populateListVehicleOptionsDTO(entity));
        dto.setAcessories(populateListVehicleAccessoriesDTO(entity));
        
        LOGGER.debug(" >> END populateSimulationVehicleVersionDTO ");
        return dto;
    }
    
    private List<VehicleOptionsDTO> populateListVehicleOptionsDTO(DossierEntity entity) throws UnexpectedException{
        
        LOGGER.debug(" >> INIT populateListVehicleOptionsDTO ");
        
        List<VehicleOptionsDTO> lista = new ArrayList<>();
        for (DossierVehicleOptionEntity item : entity.getListDossierVehicleOption()) {
            VehicleOptionsDTO dto = new VehicleOptionsDTO();
            dto.setId(CriptoUtilsOmega2.encrypt(item.getVehicleOptions().getId()));
            dto.setDescription(item.getVehicleOptions().getDescription());
            dto.setAmount(item.getAmount());
            
            lista.add(dto);
        }
        
        LOGGER.debug(" >> END populateListVehicleOptionsDTO ");
        return lista;
    }
    
    private List<VehicleAccessoriesDTO> populateListVehicleAccessoriesDTO(DossierEntity entity) throws UnexpectedException{
        
        LOGGER.debug(" >> INIT populateListVehicleAccessoriesDTO ");
        
        List<VehicleAccessoriesDTO> lista = new ArrayList<>();
        for (DossierVehicleAccessoryEntity item : entity.getListDossierVehicleAccessory()) {
            VehicleAccessoriesDTO dto = new VehicleAccessoriesDTO();
            dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
            dto.setDescription(item.getDescription());
            dto.setAmount(item.getAmount());
            
            lista.add(dto);
        }

        LOGGER.debug(" >> END populateListVehicleAccessoriesDTO ");
        return lista; 
    }

    private void populatePhones(CustomerEntity customer, ClientDTO dto) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populatePhones ");
        
        dto.setPhone(new PhoneDTO());
        for (PhoneEntity phoneEntity : customer.getListPhone()) {
            PhoneDTO phoneDto = new PhoneDTO();

            phoneDto.setId(CriptoUtilsOmega2.encrypt(phoneEntity.getId()));
            phoneDto.setNumber(GeneralUtils.stringNotNull(phoneEntity.getDdd()) + GeneralUtils.stringNotNull(phoneEntity.getPhone()));
            phoneDto.setExtension(phoneEntity.getExtensionLine());

            if(phoneEntity.getPhoneType().equals(PhoneTypeEnum.CELULAR)){
                dto.setCellphone(phoneDto);
            }
            if(phoneEntity.getPhoneType().equals(PhoneTypeEnum.FIXO)){
                dto.setPhone(phoneDto);
            }
            if(phoneEntity.getPhoneType().equals(PhoneTypeEnum.COMERCIAL)){
                dto.getCompany().setComercialPhone(phoneDto);
            }
        }
        
        LOGGER.debug(" >> END populatePhones ");
    }

    private void populatePhones(GuarantorEntity entity, GuarantorDTO dto) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populatePhones ");
        
        for (PhoneEntity phoneEntity : entity.getListPhone()) {
            PhoneDTO phoneDto = new PhoneDTO();

            phoneDto.setId(CriptoUtilsOmega2.encrypt(phoneEntity.getId()));
            phoneDto.setNumber(GeneralUtils.stringNotNull(phoneEntity.getDdd()) + GeneralUtils.stringNotNull(phoneEntity.getPhone()));
            phoneDto.setExtension(phoneEntity.getExtensionLine());

            if(phoneEntity.getPhoneType().equals(PhoneTypeEnum.CELULAR)){
                dto.setCellphone(phoneDto);
            }
            if(phoneEntity.getPhoneType().equals(PhoneTypeEnum.FIXO)){
                dto.setFixPhone(phoneDto);
            }
            if(phoneEntity.getPhoneType().equals(PhoneTypeEnum.COMERCIAL)){
                dto.getCompany().setComercialPhone(phoneDto);
            }
        }
        
        LOGGER.debug(" >> END populatePhones ");
    }

    private void populateCustomerReference(CustomerEntity entity, ClientDTO dto) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateCustomerReference ");
        
        for (CustomerReferenceEntity item : AppOrderUtils.ordinateCustomerReferenceList(entity.getListCustomerReference())) {
            ReferenceDTO temp = new ReferenceDTO();
            temp.setId(String.valueOf(item.getId()));
            temp.setName(item.getDescription());
            temp.setPhone(GeneralUtils.stringNotNull(item.getDdd()) + GeneralUtils.stringNotNull(item.getPhone()));
            
            if(AppUtil.isNullOrEmpty(dto.getReference1())){
                dto.setReference1(temp);
            } else {
                dto.setReference2(temp);
            }
        }
        
        LOGGER.debug(" >> END populateCustomerReference ");
        
    }
    
    private void populateGuarantorReference(GuarantorEntity entity, GuarantorDTO dto) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populateGuarantorReference ");
        
        for (GuarantorReferenceEntity item : AppOrderUtils.ordinateGuarantorReferenceList(entity.getListGuarantorReference())) {
            ReferenceDTO temp = new ReferenceDTO();
            temp.setId(CriptoUtilsOmega2.encrypt(String.valueOf(item.getId())));
            temp.setName(item.getDescription());
            temp.setPhone(GeneralUtils.stringNotNull(item.getDdd()) + GeneralUtils.stringNotNull(item.getPhone()));
            
            if(AppUtil.isNullOrEmpty(dto.getReference1())){
                dto.setReference1(temp);
            } else {
                dto.setReference2(temp);
            }
            
        }
        
        LOGGER.debug(" >> END populateGuarantorReference ");
    }
    
}

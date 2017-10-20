package com.rci.omega2.ejb.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.crypt.decrypt.CriptoUtils;
import com.rci.omega2.common.exception.CryptoException;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.CustomerReferenceDAO;
import com.rci.omega2.ejb.dao.DossierVehicleAccessoryDAO;
import com.rci.omega2.ejb.dao.DossierVehicleDAO;
import com.rci.omega2.ejb.dao.DossierVehicleOptionDAO;
import com.rci.omega2.ejb.dao.FinancialBrandDAO;
import com.rci.omega2.ejb.dao.GenericDAO;
import com.rci.omega2.ejb.dao.GuarantorReferenceDAO;
import com.rci.omega2.ejb.dao.InstalmentDAO;
import com.rci.omega2.ejb.dao.PersonDAO;
import com.rci.omega2.ejb.dao.PhoneDAO;
import com.rci.omega2.ejb.dao.ProposalServiceDAO;
import com.rci.omega2.ejb.dao.ProposalTaxDAO;
import com.rci.omega2.ejb.dto.AddressTypeDTO;
import com.rci.omega2.ejb.dto.BankDTO;
import com.rci.omega2.ejb.dto.BusinessRelationshipTypeDTO;
import com.rci.omega2.ejb.dto.CalculateDTO;
import com.rci.omega2.ejb.dto.CalculateParametersDTO;
import com.rci.omega2.ejb.dto.CivilStateDTO;
import com.rci.omega2.ejb.dto.ColorDTO;
import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.CountryDTO;
import com.rci.omega2.ejb.dto.DocumentTypeDTO;
import com.rci.omega2.ejb.dto.EconomicAtivityDTO;
import com.rci.omega2.ejb.dto.EducationDegreeDTO;
import com.rci.omega2.ejb.dto.EmissionOrganismDTO;
import com.rci.omega2.ejb.dto.EmployerSizeDTO;
import com.rci.omega2.ejb.dto.GuarantorTypeDTO;
import com.rci.omega2.ejb.dto.IncomeTypeDTO;
import com.rci.omega2.ejb.dto.IndustrialSegmentDTO;
import com.rci.omega2.ejb.dto.KinshipTypeDTO;
import com.rci.omega2.ejb.dto.LegalNatureDTO;
import com.rci.omega2.ejb.dto.OccupationDTO;
import com.rci.omega2.ejb.dto.PoliticalExpositionDTO;
import com.rci.omega2.ejb.dto.ProfessionDTO;
import com.rci.omega2.ejb.dto.ProofIncomeTypeDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;
import com.rci.omega2.ejb.dto.RepackageDTO;
import com.rci.omega2.ejb.dto.ResidenceTypeDTO;
import com.rci.omega2.ejb.dto.SpecialTypeDTO;
import com.rci.omega2.ejb.dto.simulation.BankDetailsDTO;
import com.rci.omega2.ejb.dto.simulation.CalculationDTO;
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
import com.rci.omega2.ejb.dto.simulation.SimulationDossierDTO;
import com.rci.omega2.ejb.dto.simulation.SpouseDTO;
import com.rci.omega2.ejb.dto.simulation.VehicleAccessoriesDTO;
import com.rci.omega2.ejb.dto.simulation.VehicleOptionsDTO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.ejb.utils.PhoneDetail;
import com.rci.omega2.ejb.utils.ValidateConstants;
import com.rci.omega2.entity.AddressEntity;
import com.rci.omega2.entity.AddressTypeEntity;
import com.rci.omega2.entity.BankDetailEntity;
import com.rci.omega2.entity.BankEntity;
import com.rci.omega2.entity.BaseEntity;
import com.rci.omega2.entity.BusinessRelationshipTypeEntity;
import com.rci.omega2.entity.CivilStateEntity;
import com.rci.omega2.entity.ColorEntity;
import com.rci.omega2.entity.CommissionEntity;
import com.rci.omega2.entity.CountryEntity;
import com.rci.omega2.entity.CustomerEntity;
import com.rci.omega2.entity.CustomerReferenceEntity;
import com.rci.omega2.entity.CustomerSpouseEntity;
import com.rci.omega2.entity.DealershipEntity;
import com.rci.omega2.entity.DocumentTypeEntity;
import com.rci.omega2.entity.DossierEntity;
import com.rci.omega2.entity.DossierStatusEntity;
import com.rci.omega2.entity.DossierVehicleAccessoryEntity;
import com.rci.omega2.entity.DossierVehicleEntity;
import com.rci.omega2.entity.DossierVehicleOptionEntity;
import com.rci.omega2.entity.EconomicActivityEntity;
import com.rci.omega2.entity.EducationDegreeEntity;
import com.rci.omega2.entity.EmissionOrganismEntity;
import com.rci.omega2.entity.EmployerEntity;
import com.rci.omega2.entity.EmployerSizeEntity;
import com.rci.omega2.entity.EventEntity;
import com.rci.omega2.entity.EventTypeEntity;
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
import com.rci.omega2.entity.PersonEntity;
import com.rci.omega2.entity.PhoneEntity;
import com.rci.omega2.entity.PoliticalExpositionEntity;
import com.rci.omega2.entity.ProfessionEntity;
import com.rci.omega2.entity.ProofIncomeTypeEntity;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.ProposalServiceEntity;
import com.rci.omega2.entity.ProposalTaxEntity;
import com.rci.omega2.entity.ProvinceEntity;
import com.rci.omega2.entity.RepackageEntity;
import com.rci.omega2.entity.ResidenceTypeEntity;
import com.rci.omega2.entity.SaleTypeEntity;
import com.rci.omega2.entity.ServiceEntity;
import com.rci.omega2.entity.SpecialVehicleTypeEntity;
import com.rci.omega2.entity.StructureEntity;
import com.rci.omega2.entity.UserEntity;
import com.rci.omega2.entity.VehicleOptionsEntity;
import com.rci.omega2.entity.VehicleVersionEntity;
import com.rci.omega2.entity.enumeration.AccountTypeEnum;
import com.rci.omega2.entity.enumeration.PersonGenderEnum;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;
import com.rci.omega2.entity.enumeration.PersonalPhoneTypeEnum;
import com.rci.omega2.entity.enumeration.PhoneTypeEnum;
import com.rci.omega2.entity.enumeration.TaxTypeEnum;

@Stateless
public class SaveSimulationBO extends BaseBO {
    
    private static final Logger LOGGER = LogManager.getLogger(SaveSimulationBO.class);

    @EJB
    private SimulationCalcBO simulationCalcBO;
    @EJB
    private RepackageBO repackageBO;
    @EJB
    private ProductBO productBO;
    @EJB
    private FinanceTypeBO financeTypeBO;
    
    private GenericDAO genericDAO;
    private DossierVehicleDAO dossierVehicleDAO;
    private DossierVehicleAccessoryDAO dossierVehicleAccessoryDAO;
    private DossierVehicleOptionDAO dossierVehicleOptionDAO;
    private PersonDAO personDAO;
    private FinancialBrandDAO financialBrandDAO;
    private PhoneDAO phoneDAO;
    private InstalmentDAO instalmentDAO;
    private GuarantorReferenceDAO guarantorReferenceDAO;
    private CustomerReferenceDAO customerReferenceDAO;
    private ProposalServiceDAO proposalServiceDAO;
    private ProposalTaxDAO proposalTaxDAO;
    
    public String saveDossier(SimulationDossierDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveDossier ");
            initializeDaos();
            
            DossierEntity dossierEntity = saveDossierEntity(dto);
            String temp = CriptoUtilsOmega2.encrypt(dossierEntity.getId());
            
            LOGGER.debug(" >> END saveDossier ");
            return temp;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private DossierEntity saveDossierEntity(SimulationDossierDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveDossierEntity ");
            Boolean newSimulation = AppUtil.isNullOrEmpty(dto.getSimulation().getId());
            
            DossierEntity dossierEntity = new DossierEntity();
            
            if(!newSimulation){
                Long dossierId = CriptoUtilsOmega2.decryptIdToLong(dto.getSimulation().getId());
                dossierEntity = genericDAO.find(DossierEntity.class, dossierId);
                validateDossierStatus(dossierEntity);
            } else {
                DossierStatusEntity dossierStatusEntity = genericDAO.find(DossierStatusEntity.class, AppConstants.DOSSIER_STATUS_ANDAMENTO);
                dossierEntity.setDossierStatus(dossierStatusEntity);
            }
            
            if(dossierEntity.getDossierStatus().getId().equals(AppConstants.DOSSIER_STATUS_APROVADA)){
                return saveAprovada(dossierEntity, dto);
            }
            
            DossierEntity temp = saveFullDossier(dossierEntity, dto, newSimulation);
            
            LOGGER.debug(" >> END saveDossierEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private DossierEntity saveFullDossier(DossierEntity dossierEntity, SimulationDossierDTO dto, Boolean newSimulation) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveFullDossier ");
            
            dossierEntity.setStructure(genericDAO.find(StructureEntity.class, CriptoUtilsOmega2.decryptIdToLong(dto.getStructureId())));
            dossierEntity.setFinancialBrand(financialBrandDAO.findByStructureId(dossierEntity.getStructure().getId()));
            expiryDateCalculate(dossierEntity);
            dossierEntity.setVehiclePrice(dto.getSimulation().getCar().getVersion().getPrice());
            
            dossierEntity.setSaleType(genericDAO.find(SaleTypeEntity.class, CriptoUtilsOmega2.decryptIdToLong(dto.getSimulation().getSaleType().getId())));
            if(!AppUtil.isNullOrEmpty(dto.getSimulation().getCertifiedAgent())){
                dossierEntity.setCertifiedAgent(CriptoUtilsOmega2.decrypt(dto.getSimulation().getCertifiedAgent()));
            }
            
            dossierEntity.setTcExempt(dto.getSimulation().isTc());            
            dossierEntity.setSalesman(personDAO.findPersonByUserId(CriptoUtilsOmega2.decryptIdToLong(dto.getSalesmanId())));
            dossierEntity.setDossierManager(personDAO.findPersonByUserId(dto.getManagerId()));
            dossierEntity.setUsedVehicleShowroom(dto.getSimulation().isShowRoomSemiNews());
            dossierEntity.setDossierVehicle(saveDossierVehicleEntity(dto.getSimulation()));
            
            dossierEntity.setListSpecialVehicleType(prepareListSpecialVehicleType(dto.getSimulation().getSpecialTypes()));
            
            dossierEntity.setCustomer(saveCustomer(dto.getSimulation().getClient()));
            
            dossierEntity = genericDAO.update(dossierEntity);
            
            dossierEntity.setListProposal(new HashSet<>());
            saveListProposal(dto, dossierEntity);
            saveListGuarantor(dto, dossierEntity);
            saveListDossierVehicleAccessory(dto, dossierEntity);
            saveListDossierVehicleOption(dto, dossierEntity);
            
            
            LOGGER.debug(" >> END saveFullDossier ");
            return dossierEntity;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListProposal(SimulationDossierDTO dto, DossierEntity dossierEntity) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveListProposal ");
            int count = 0;
            for (CalculationDTO calculation : dto.getSimulation().getCalculations()) {
                Boolean newProposal = AppUtil.isNullOrEmpty(calculation.getId());
                
                ProposalEntity proposalEntity = new ProposalEntity();
                
                if(!newProposal){
                    Long id = CriptoUtilsOmega2.decryptIdToLong(calculation.getId());
                    proposalEntity = genericDAO.find(ProposalEntity.class, id);
                }
                
                proposalEntity.setDossier(dossierEntity);
                
                proposalEntity.setUser(genericDAO.find(UserEntity.class, dto.getCurrentUser().getId()));
                proposalEntity.setExibition("0" + count++);
                proposalEntity.setExibitionMain(calculation.getSelected());
                proposalEntity.setProduct(productBO.findProductEntity(calculation.getFinancialTable()));
                proposalEntity.setFinanceType(financeTypeBO.findFinanceTypeEntity(calculation.getFinancialType()));
                proposalEntity.setCommission(findCommissionEntity(calculation.getCommission()));
                proposalEntity.setDelayValue(calculation.getDelay());
                proposalEntity.setInstalmentQuantity(calculation.getTerm());
                
                CalculateParametersDTO parameters = populateSimulationCalcParametersDTO(calculation, proposalEntity, dossierEntity);
                
                CalculateDTO calculateDto = simulationCalcBO.calculate(parameters);
                if(!calculateDto.getCalculateOk()){
                    throw new BusinessException(ValidateConstants.SAVE_PROPOSAL_ERROR_CALCULATE);
                }
                
                proposalEntity.setRepackage(findRepackageEntity(calculation.getRepackage()));
                proposalEntity.setDeposit(calculateDto.getDeposit());
                proposalEntity.setFinancedAmount(calculateDto.getTotalAmountFinanced());
                proposalEntity.setInstalmentAmount(calculateDto.getInstalmentAmount());
                
                proposalEntity.setCoefficient(calculateDto.getCoefficient());
                proposalEntity.setMontlyRate(calculateDto.getTaxCoefficient());
                
                List<InstalmentDTO> listInstalmentDTO = calculateDto.getListInstalment();
                proposalEntity.setInitialPeriod(listInstalmentDTO.get(0).getDueDate());
                proposalEntity.setFinalPeriod(listInstalmentDTO.get(listInstalmentDTO.size()-1).getDueDate());
                
                proposalEntity = genericDAO.update(proposalEntity);
                
                saveListProposalTax(proposalEntity, calculation.getRates());
                saveListProposalService(proposalEntity, calculation.getServices());
                saveListInstalment(proposalEntity, listInstalmentDTO);
                
                dossierEntity.getListProposal().add(proposalEntity);
                
                if(newProposal){
                    saveEventEntity(proposalEntity);
                }
            }
            LOGGER.debug(" >> END saveListProposal ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private void saveListProposalService(ProposalEntity proposalEntity, List<ServiceDTO> services) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveListProposalService ");
            proposalServiceDAO.deleteAllProposalService(proposalEntity);
            
            proposalEntity.setListProposalService(new HashSet<>());
            if(!AppUtil.isNullOrEmpty(services)){
                for (ServiceDTO dto : services) {
                    ProposalServiceEntity entity = new ProposalServiceEntity();
                    entity.setProposal(proposalEntity);
                    
                    Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                    entity.setService(genericDAO.find(ServiceEntity.class, id));
                    
                    entity.setAmount(dto.getAmount());
                    
                    entity = genericDAO.update(entity);
                    proposalEntity.getListProposalService().add(entity);
                }
            }
            
            LOGGER.debug(" >> END saveListProposalService ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListProposalTax(ProposalEntity proposalEntity, List<RateDTO> rates) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveListProposalTax ");
            proposalTaxDAO.deleteAllProposalTax(proposalEntity);
            
            proposalEntity.setListProposalTax(new HashSet<>());
            if(!AppUtil.isNullOrEmpty(rates)){
                for (RateDTO dto : rates) {
                    ProposalTaxEntity entity = new ProposalTaxEntity();
                    
                    entity.setProposal(proposalEntity);
                    
                    entity.setTaxType(TaxTypeEnum.valueOf(dto.getTaxType()));
                    entity.setAmount(dto.getValue());
                    entity.setProvince(findProvinceEntity(dto.getProvince()));
                    
                    entity = genericDAO.update(entity);
                    proposalEntity.getListProposalTax().add(entity);
                }
            }
            
            LOGGER.debug(" >> END saveListProposalTax ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListInstalment(ProposalEntity proposalEntity, List<InstalmentDTO> listInstalmentDTO) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveListInstalment ");
            instalmentDAO.deleteAllInstalmentProposal(proposalEntity);
            
            proposalEntity.setListInstalment(new HashSet<>());
            
            if(!AppUtil.isNullOrEmpty(listInstalmentDTO)){
                for (InstalmentDTO parcelDTO : listInstalmentDTO) {
                    InstalmentEntity instalment = new InstalmentEntity();
                    instalment.setProposal(proposalEntity);
                    instalment.setAmount(parcelDTO.getAmount());
                    instalment.setInstalment(parcelDTO.getNumberInstallment());
                    instalment.setInstalmentDate(parcelDTO.getDueDate());
                    
                    instalment = genericDAO.update(instalment);
                    proposalEntity.getListInstalment().add(instalment);
                }
            }
            LOGGER.debug(" >> END saveListInstalment ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private CustomerEntity saveCustomer(ClientDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveCustomer ");
            CustomerEntity customerEntity = new CustomerEntity();
            if(!AppUtil.isNullOrEmpty(dto.getId())){
                Long customerId = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                customerEntity = genericDAO.find(CustomerEntity.class, customerId);
            }
            
            if(!AppUtil.isNullOrEmpty(dto.getCpfCnpj())){
                customerEntity.setCpfCnpj(AppUtil.onlyNumbers(dto.getCpfCnpj()));
                customerEntity.setPersonType(customerEntity.getCpfCnpj().length() == 11 ? PersonTypeEnum.PF : PersonTypeEnum.PJ);
            }
            
            customerEntity.setNameClient(dto.getName());
            customerEntity.setEmail(dto.getEmail());
            
            customerEntity.setPersonGender(null);
            if(!AppUtil.isNullOrEmpty(dto.getSex())){
                customerEntity.setPersonGender(PersonGenderEnum.valueOf(dto.getSex()));
            }
            customerEntity.setCivilState(findCivilStateEntity(dto.getCivilState()));
            customerEntity.setCountry(findCountryEntity(dto.getCountry()));
            customerEntity.setProvince(findProvinceEntity(dto.getProvince()));
            customerEntity.setNaturalness(dto.getNaturalness());
            customerEntity.setEducationDegree(findEducationDegreeEntity(dto.getEducationDegree()));
            customerEntity.setHandicapped(dto.isHandicapped());
            customerEntity.setFatherName(dto.getFathersName());
            customerEntity.setMotherName(dto.getMothersName());
            
            validateDateToday(dto.getBirthDate(), ValidateConstants.CUSTOMER_ERROR_BIRTH);
            customerEntity.setDateBirth(dto.getBirthDate());

            customerEntity.setPersonalPhoneType(null);
            if(!AppUtil.isNullOrEmpty(dto.getPhoneType())){
                customerEntity.setPersonalPhoneType(PersonalPhoneTypeEnum.valueOf(dto.getPhoneType()));
            }
            
            customerEntity.setIncome(null);
            customerEntity.setOtherIncome(null);
            if(!AppUtil.isNullOrEmpty(dto.getCompany())){
                customerEntity.setIncome(dto.getCompany().getMonthlyIncome());
                customerEntity.setOtherIncome(dto.getCompany().getOtherIncomes());
            }
            
            customerEntity.setPoliticalExposition(findPoliticalExpositionEntity(dto.getPoliticalExposition()));

            customerEntity.setAddressSince(null);
            customerEntity.setResidenceType(null);
            customerEntity.setMailingAddressType(null);
            if(!AppUtil.isNullOrEmpty(dto.getAddress())){
                validateDateToday(dto.getAddress().getResidesInAddressSince(), ValidateConstants.CUSTOMER_ERROR_ADDRESS_SINCE);
                customerEntity.setAddressSince(dto.getAddress().getResidesInAddressSince());
                customerEntity.setResidenceType(findResidenceTypeEntity(dto.getAddress().getResidenceType()));
                customerEntity.setMailingAddressType(findAddressTypeEntity(dto.getAddress().getMailingAddress()));
            }
            
            // DOCUMENTOS - INIT
            customerEntity.setEmissionDate(dto.getDateIssue());
            customerEntity.setDocumentNumber(dto.getNumberDocument());
            
            customerEntity.setEmissionOrganism(findEmissionOrganismEntity(dto.getIssuingBody()));
            customerEntity.setDocumentProvince(findProvinceEntity(dto.getProvinceDocument()));
            
            customerEntity.setDocumentCountry(null);
            if(!AppUtil.isNullOrEmpty(dto.getCountryDocument()) && !AppUtil.isNullOrEmpty(dto.getCountryDocument().getId())){
                Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getCountryDocument().getId());
                customerEntity.setDocumentCountry(genericDAO.find(CountryEntity.class, id));
            }
            
            customerEntity.setDocumentValidate(dto.getDateValid());
            customerEntity.setDocumentType(findDocumentTypeEntity(dto.getDocumentType()));
            // DOCUMENTOS - END
            

            // CLIENTE PJ - INIT
            customerEntity.setCustomerSize(findEmployerSizeEntity(dto.getCompanySize()));
            customerEntity.setLegalNature(findLegalNatureEntity(dto.getLegalNature()));
            customerEntity.setCustomerEconomicActivity(findEconomicActivityEntity(dto.getEconomicActivity()));
            customerEntity.setCustomerIndustrialSegment(findIndustrialSegmentEntity(dto.getEconomicActivityGroup()));
            customerEntity.setBuildingOwner(dto.isOwnSeat());
            // CLIENTE PJ - END

            customerEntity.setEmployer(saveEmployerEntity(dto.getCompany()));
            
            customerEntity.setEmployerAddress(null);
            if(!AppUtil.isNullOrEmpty(dto.getCompany())){
                customerEntity.setEmployerAddress(saveAddressEntity(dto.getCompany().getAddress()));
            }
            
            customerEntity.setAddress(saveAddressEntity(dto.getAddress()));
            
            customerEntity.setBankDetail(saveBankDetail(dto.getBankDetails(), ValidateConstants.CUSTOMER_ERROR_ACCOUNT_BIRTH));
            
            customerEntity = genericDAO.update(customerEntity);
            
            saveListPhoneCustomer(dto, customerEntity);
            
            customerEntity.setCustomerSpouse(saveCustomerSpouseEntity(dto.getSpouse(), customerEntity, dto.getCivilState()));
            
            saveListCustomerReference(dto, customerEntity);

            LOGGER.debug(" >> END saveCustomer ");
            return customerEntity;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListCustomerReference(ClientDTO dto, CustomerEntity customerEntity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT saveListCustomerReference ");
        customerReferenceDAO.deleteAllCustomerReference(customerEntity);
        
        customerEntity.setListCustomerReference(new HashSet<>());
        
        if(isValidReference(dto.getReference1())){
            CustomerReferenceEntity entity = saveCustomerReferenceEntity(dto.getReference1(), customerEntity);
            customerEntity.getListCustomerReference().add(entity);
        }
        
        if(isValidReference(dto.getReference2())){
            CustomerReferenceEntity entity = saveCustomerReferenceEntity(dto.getReference2(), customerEntity);
            customerEntity.getListCustomerReference().add(entity);
        }
        
        LOGGER.debug(" >> END saveListCustomerReference ");
    }

    private CustomerReferenceEntity saveCustomerReferenceEntity(ReferenceDTO dto, CustomerEntity customerEntity) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT saveCustomerReferenceEntity ");
            CustomerReferenceEntity entity = new CustomerReferenceEntity();
            
            entity.setCustomer(customerEntity);
            entity.setDescription(dto.getName());
            
            if(!AppUtil.isNullOrEmpty(dto.getPhone())){
                PhoneDetail detail = GeneralUtils.splitPhone(dto.getPhone());
                entity.setDdd(detail.getDdd());
                entity.setPhone(detail.getNumber());
            }
            
            entity = genericDAO.update(entity);
            LOGGER.debug(" >> END saveCustomerReferenceEntity ");
            
            return entity;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private CustomerSpouseEntity saveCustomerSpouseEntity(SpouseDTO dto, CustomerEntity customer, CivilStateDTO civilState) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveCustomerSpouseEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getCpf())){
                if(!AppUtil.isNullOrEmpty(customer.getCustomerSpouse())){
                    genericDAO.remove(customer.getCustomerSpouse());
                }
                return null;
            }
            
            CustomerSpouseEntity entity = new CustomerSpouseEntity();
            if(!AppUtil.isNullOrEmpty(dto.getId())){
                Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                entity = genericDAO.find(CustomerSpouseEntity.class, id);
            }

            entity.setCustomer(customer);
            entity.setNameClient(dto.getName());
            entity.setCpf(AppUtil.onlyNumbers(dto.getCpf()));
            
            entity.setPersonGender(null);
            if(!AppUtil.isNullOrEmpty(dto.getSex())){
                entity.setPersonGender(PersonGenderEnum.valueOf(dto.getSex()));
            }
            
            validateDateToday(dto.getBirthDate(), ValidateConstants.CUSTOMER_ERROR_SPOUSE_BIRTH);
            entity.setDateBirth(dto.getBirthDate());
            entity.setProvince(findProvinceEntity(dto.getProvince()));
            entity.setDocumentNumber(dto.getNumberDocument());
            entity.setEmissionOrganism(findEmissionOrganismEntity(dto.getIssuingBody()));
            
            entity.setEmployerName(null);
            entity.setOccupation(null);
            entity.setProfession(null);
            entity.setAdmissionDate(null);
            entity.setIncome(null);
            entity.setDdd(null);
            entity.setPhone(null);
            entity.setExtensionLine(null);
            if(!AppUtil.isNullOrEmpty(dto.getCompany())){
                entity.setEmployerName(dto.getCompany().getName());
                entity.setOccupation(findOccupationEntity(dto.getCompany().getOccupation()));
                entity.setProfession(findProfessionEntity(dto.getCompany().getProfession()));
                entity.setAdmissionDate(dto.getCompany().getAdmissionDate());
                entity.setIncome(dto.getCompany().getMonthlyIncome());
                
                if(!AppUtil.isNullOrEmpty(dto.getCompany().getComercialPhone())){
                    entity.setExtensionLine(dto.getCompany().getComercialPhone().getExtension());
                    
                    if(!AppUtil.isNullOrEmpty(dto.getCompany().getComercialPhone().getNumber())){
                        PhoneDetail detail = GeneralUtils.splitPhone(dto.getCompany().getComercialPhone().getNumber());
                        entity.setDdd(detail.getDdd());
                        entity.setPhone(detail.getNumber());
                    }
                    
                }
            }
            
            entity = genericDAO.update(entity);
            
            LOGGER.debug(" >> END saveCustomerSpouseEntity ");
            return entity;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListPhoneCustomer(ClientDTO dto, CustomerEntity customerEntity) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveListPhoneCustomer ");
            customerEntity.setListPhone(new HashSet<>());
            
            List<PhoneEntity> lista = new ArrayList<>();
            
            if(!AppUtil.isNullOrEmpty(dto.getCellphone())){
                PhoneEntity phone = populatePhoneEntity(dto.getCellphone(), PhoneTypeEnum.CELULAR, customerEntity);
                phone.setExtensionLine(dto.getCellphone().getExtension());
                lista.add(phone);
            }
            
            if(!AppUtil.isNullOrEmpty(dto.getPhone())){
                PhoneEntity phone = populatePhoneEntity(dto.getPhone(), PhoneTypeEnum.FIXO, customerEntity);
                phone.setExtensionLine(dto.getPhone().getExtension());
                lista.add(phone);
            }
            
            if(!AppUtil.isNullOrEmpty(dto.getCompany()) && !AppUtil.isNullOrEmpty(dto.getCompany().getComercialPhone())){
                PhoneEntity phone = populatePhoneEntity(dto.getCompany().getComercialPhone(), PhoneTypeEnum.COMERCIAL, customerEntity);
                phone.setExtensionLine(dto.getCompany().getComercialPhone().getExtension());
                lista.add(phone);
            }
            
            phoneDAO.deleteAllPhoneCustomer(customerEntity);
            
            for (PhoneEntity phoneEntity : lista) {
                phoneEntity = phoneDAO.update(phoneEntity);
                customerEntity.getListPhone().add(phoneEntity);
            }
            
            LOGGER.debug(" >> END saveListPhoneCustomer ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private PhoneEntity populatePhoneEntity(PhoneDTO dto, PhoneTypeEnum type, BaseEntity entity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT populatePhoneEntity ");
        PhoneEntity phoneEntity = new PhoneEntity();
        
        phoneEntity.setDdd(null);
        phoneEntity.setPhone(null);
        if(!AppUtil.isNullOrEmpty(dto.getNumber())){
            PhoneDetail detail = GeneralUtils.splitPhone(dto.getNumber());
            phoneEntity.setDdd(detail.getDdd());
            phoneEntity.setPhone(detail.getNumber());
        }
        
        phoneEntity.setExtensionLine(dto.getExtension());
        phoneEntity.setPhoneType(type);

        if(entity instanceof CustomerEntity){
            phoneEntity.setCustomer( (CustomerEntity) entity );
        }
        if(entity instanceof DealershipEntity){
            phoneEntity.setDealership( (DealershipEntity) entity );
        }
        if(entity instanceof GuarantorEntity){
            phoneEntity.setGuarantor( (GuarantorEntity) entity );
        }
        if(entity instanceof PersonEntity){
            phoneEntity.setPerson( (PersonEntity) entity );
        }
        
        
        LOGGER.debug(" >> END populatePhoneEntity ");
        return phoneEntity;

    }
    
    private BankDetailEntity saveBankDetail(BankDetailsDTO dto, String msgDetail) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveBankDetail ");
            if(AppUtil.isNullOrEmpty(dto)){
                return null; 
            }
            
            BankDetailEntity entity = new BankDetailEntity();
            if(!AppUtil.isNullOrEmpty(dto.getId())){
                Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                entity = genericDAO.find(BankDetailEntity.class, id);
            }
            
            entity.setBank(findBankEntity(dto.getBank()));
            
            entity.setDdd(null);
            entity.setPhone(null);
            if(!AppUtil.isNullOrEmpty(dto.getPhoneBranch()) && !AppUtil.isNullOrEmpty(dto.getPhoneBranch().getNumber())){
                PhoneDetail detail = GeneralUtils.splitPhone(dto.getPhoneBranch().getNumber());
                entity.setDdd(detail.getDdd());
                entity.setPhone(detail.getNumber());
            }

            entity.setBranch(dto.getBranch());
            entity.setBranchDigit(dto.getBranchDigit());
            
            entity.setAccount(dto.getAccount());
            entity.setAccountDigit(dto.getAccountDigit());
            
            entity.setAccountOpeningDate(dto.getCustomerSince());
            validateDateToday(dto.getCustomerSince(), msgDetail);
            
            entity.setAccountType(null);
            if(!AppUtil.isNullOrEmpty(dto.getAccountType())){
                entity.setAccountType(AccountTypeEnum.valueOf(dto.getAccountType()));
            }
            
            entity = genericDAO.update(entity);
            
            LOGGER.debug(" >> END saveBankDetail ");
            return entity;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private AddressEntity saveAddressEntity(SimulationAdressDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveAddressEntity ");
            if(AppUtil.isNullOrEmpty(dto)){
                return null; 
            }
            
            AddressEntity addressEntity = new AddressEntity();
            if(!AppUtil.isNullOrEmpty(dto.getId())){
                Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                addressEntity = genericDAO.find(AddressEntity.class, id);
            }
            
            addressEntity.setPostCode(dto.getCep());
            addressEntity.setAddress(dto.getAddress());
            addressEntity.setNumber(dto.getNumber());
            addressEntity.setComplement(dto.getComplement());
            addressEntity.setNeighborhood(dto.getNeighborhood());
            addressEntity.setProvince(null);
            addressEntity.setCity(dto.getCity());
            addressEntity.setProvince(findProvinceEntity(dto.getProvince()));
            
            addressEntity = genericDAO.update(addressEntity);
            
            LOGGER.debug(" >> END saveAddressEntity ");
            return addressEntity;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    private EmployerEntity saveEmployerEntity(CompanyDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveEmployerEntity ");
            if(AppUtil.isNullOrEmpty(dto)){
                return null; 
            }
            
            EmployerEntity entity = new EmployerEntity();
            if(!AppUtil.isNullOrEmpty(dto.getId())){
                Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                entity = genericDAO.find(EmployerEntity.class, id);
            }
            
            entity.setCnpj(AppUtil.onlyNumbers(dto.getCnpj()));
            entity.setEmployerName(dto.getName());
            entity.setOccupation(findOccupationEntity(dto.getOccupation()));
            entity.setProfession(findProfessionEntity(dto.getProfession()));
            entity.setAdmissionDate(dto.getAdmissionDate());
            entity.setValueAssets(dto.getPatrimony());
            entity.setEconomicActivity(findEconomicActivityEntity(dto.getEconomicActivity()));
            entity.setIncomeType(findIncomeTypeEntity(dto.getIncomeType()));
            entity.setProofIncomeType(findProofIncomeTypeEntity(dto.getProofIncomeType()));
            
            entity.setEmployerSize(findEmployerSizeEntity(dto.getSize()));
            entity.setIndustrialSegment(findIndustrialSegmentEntity(dto.getEconomicActivityGroup()));
            
            entity = genericDAO.update(entity);
            
            LOGGER.debug(" >> END saveEmployerEntity ");
            return entity;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

    private void saveListGuarantor(SimulationDossierDTO dto, DossierEntity dossierEntity) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT saveListGuarantor ");
        dossierEntity.setListGuarantor(new HashSet<>());
        if(!AppUtil.isNullOrEmpty(dto.getSimulation().getClient())){
            if(!AppUtil.isNullOrEmpty(dto.getSimulation().getClient().getGuarantor1()) && !AppUtil.isNullOrEmpty(dto.getSimulation().getClient().getGuarantor1().getCpf())){
                saveGuarantor(dto.getSimulation().getClient().getGuarantor1(), dossierEntity);
            }
            if(!AppUtil.isNullOrEmpty(dto.getSimulation().getClient().getGuarantor2()) && !AppUtil.isNullOrEmpty(dto.getSimulation().getClient().getGuarantor2().getCpf())){
                saveGuarantor(dto.getSimulation().getClient().getGuarantor2(), dossierEntity);
            }
        }
        LOGGER.debug(" >> END saveListGuarantor ");
    }

    private void saveGuarantor(GuarantorDTO dto, DossierEntity dossierEntity) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveGuarantor ");
            GuarantorEntity entity = new GuarantorEntity();
            
            if(!AppUtil.isNullOrEmpty(dto.getId())){
                Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                entity = genericDAO.find(GuarantorEntity.class, id);
            }
            
            if(!AppUtil.isNullOrEmpty(dto.getCpf())){
                entity.setCpfCnpj(AppUtil.onlyNumbers(dto.getCpf()));
                entity.setPersonType(entity.getCpfCnpj().length() == 11 ? PersonTypeEnum.PF : PersonTypeEnum.PJ);
            }
            
            entity.setNameClient(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setPersonGender(PersonGenderEnum.MASCULINO);
            if(!AppUtil.isNullOrEmpty(dto.getSex())){
                entity.setPersonGender(PersonGenderEnum.valueOf(dto.getSex()));
            }
            
            entity.setCivilState(findCivilStateEntity(dto.getCivilState()));
            entity.setCountry(findCountryEntity(dto.getCountry()));
            entity.setProvince(findProvinceEntity(dto.getProvince()));
            entity.setNaturalness(dto.getNaturalness());
            entity.setEducationDegree(findEducationDegreeEntity(dto.getEducationDegree()));
            entity.setHandicapped(dto.isHandicapped());
            entity.setFatherName(dto.getFathersName());
            entity.setMotherName(dto.getMothersName());
            
            validateDateToday(dto.getBirthDate(), ValidateConstants.GUARANTOR_ERROR_BIRTH);
            entity.setDateBirth(dto.getBirthDate());
            entity.setPersonalPhoneType(null);
            
            if(!AppUtil.isNullOrEmpty(dto.getFixPhoneType())){
                entity.setPersonalPhoneType(PersonalPhoneTypeEnum.valueOf(dto.getFixPhoneType()));
            }
            
            entity.setIncome(null);
            entity.setOtherIncome(null);
            if(!AppUtil.isNullOrEmpty(dto.getCompany())){
                CompanyDTO company = dto.getCompany();
                
                entity.setIncome(company.getMonthlyIncome());
                entity.setOtherIncome(company.getOtherIncomes());
            }
            
            entity.setPoliticalExposition(findPoliticalExpositionEntity(dto.getPoliticalExposition()));
            
            entity.setAddressSince(null);
            entity.setResidenceType(null);
            entity.setMailingAddressType(null);
            if(!AppUtil.isNullOrEmpty(dto.getAddress())){
                SimulationAdressDTO tempDto = dto.getAddress();
                
                entity.setAddressSince(tempDto.getResidesInAddressSince());
                validateDateToday(tempDto.getResidesInAddressSince(), ValidateConstants.GUARANTOR_ERROR_ADDRESS_SINCE);
                
                entity.setResidenceType(findResidenceTypeEntity(tempDto.getResidenceType()));
                entity.setMailingAddressType(findAddressTypeEntity(tempDto.getMailingAddress()));
            }
            
            entity.setKinshipType(findKinshipTypeEntity(dto.getKinshipType()));
            entity.setBusinessRelationshipType(findBusinessRelationshipTypeEntity(dto.getBusinessRelashionshipType()));

            // DOCUMENTOS - INIT
            entity.setEmissionDate(dto.getDateIssueDocument());
            entity.setDocumentNumber(dto.getNumberDocument());
            entity.setEmissionOrganism(findEmissionOrganismEntity(dto.getIssuingBodyDocument()));
            entity.setDocumentProvince(findProvinceEntity(dto.getProvinceDocument()));
            entity.setDocumentCountry(findCountryEntity(dto.getCountryDocument()));
            entity.setDocumentValidate(dto.getValidDateDocument());
            entity.setDocumentType(findDocumentTypeEntity(dto.getDocumentType()));
            // DOCUMENTOS - END

            entity.setEmployer(saveEmployerEntity(dto.getCompany()));
            entity.setEmployerAddress(null);
            if(!AppUtil.isNullOrEmpty(dto.getCompany())){
                entity.setEmployerAddress(saveAddressEntity(dto.getCompany().getAddress()));
            }
            
            entity.setDossier(dossierEntity);
            
            entity.setAddress(saveAddressEntity(dto.getAddress()));
            entity.setGuarantorType(findGuarantorTypeEntity(dto.getGuarantorType()));
            entity.setBankDetail(saveBankDetail(dto.getBankDetails(), ValidateConstants.GUARANTOR_ERROR_ACCOUNT_BIRTH));
            
            entity = genericDAO.update(entity);
            
            saveListPhoneGuarantor(dto, entity);
            
            saveListGuarantorReference(dto, entity);
            
            entity.setGuarantorSpouse(saveGuarantorSpouseEntity(dto.getSpouse(), entity, dto.getCivilState()));
            
            dossierEntity.getListGuarantor().add(entity);
            
            LOGGER.debug(" >> END saveGuarantor ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListGuarantorReference(GuarantorDTO dto, GuarantorEntity guarantor) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT saveListGuarantorReference ");
        guarantorReferenceDAO.deleteAllGuarantorReference(guarantor);
        
        guarantor.setListGuarantorReference(new HashSet<>());
        
        if(isValidReference(dto.getReference1())){
            GuarantorReferenceEntity entity = saveGuarantorReferenceEntity(dto.getReference1(), guarantor);
            guarantor.getListGuarantorReference().add(entity);
        }
        
        if(isValidReference(dto.getReference2())){
            GuarantorReferenceEntity entity = saveGuarantorReferenceEntity(dto.getReference2(), guarantor);
            guarantor.getListGuarantorReference().add(entity);
        }
        
        LOGGER.debug(" >> END saveListGuarantorReference ");
    }
    
    private GuarantorReferenceEntity saveGuarantorReferenceEntity(ReferenceDTO dto, GuarantorEntity guarantor) throws UnexpectedException{
        try {
            LOGGER.debug(" >> INIT saveGuarantorReferenceEntity ");
            GuarantorReferenceEntity entity = new GuarantorReferenceEntity();
            
            entity.setGuarantor(guarantor);
            entity.setDescription(dto.getName());
            
            if(!AppUtil.isNullOrEmpty(dto.getPhone())){
                PhoneDetail detail = GeneralUtils.splitPhone(dto.getPhone());
                entity.setDdd(detail.getDdd());
                entity.setPhone(detail.getNumber());
            }
            
            entity = genericDAO.update(entity);
            
            LOGGER.debug(" >> END saveGuarantorReferenceEntity ");
            return entity;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private GuarantorSpouseEntity saveGuarantorSpouseEntity(SpouseDTO dto, GuarantorEntity guarantor, CivilStateDTO civilState) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveGuarantorSpouseEntity ");
            if(AppUtil.isNullOrEmpty(dto)){
                return null;
            }

            GuarantorSpouseEntity entity = new GuarantorSpouseEntity();
            if(!AppUtil.isNullOrEmpty(dto.getId())){
                Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                entity = genericDAO.find(GuarantorSpouseEntity.class, id);
            }

            entity.setGuarantor(guarantor);
            entity.setNameClient(dto.getName());
            entity.setCpf(AppUtil.onlyNumbers(dto.getCpf()));
            
            entity.setPersonGender(null);
            if(!AppUtil.isNullOrEmpty(dto.getSex())){
                entity.setPersonGender(PersonGenderEnum.valueOf(dto.getSex()));
            }
            
            validateDateToday(dto.getBirthDate(), ValidateConstants.GUARANTOR_ERROR_SPOUSE_BIRTH);
            entity.setDateBirth(dto.getBirthDate());
            entity.setProvince(findProvinceEntity(dto.getProvince()));
            entity.setDocumentNumber(dto.getNumberDocument());
            entity.setEmissionOrganism(findEmissionOrganismEntity(dto.getIssuingBody()));
            
            entity.setEmployerName(null);
            entity.setOccupation(null);
            entity.setProfession(null);
            entity.setAdmissionDate(null);
            entity.setIncome(null);
            entity.setDdd(null);
            entity.setPhone(null);
            entity.setExtensionLine(null);
            if(!AppUtil.isNullOrEmpty(dto.getCompany())){
                entity.setEmployerName(dto.getCompany().getName());
                entity.setOccupation(findOccupationEntity(dto.getCompany().getOccupation()));
                entity.setProfession(findProfessionEntity(dto.getCompany().getProfession()));
                entity.setAdmissionDate(dto.getCompany().getAdmissionDate());
                entity.setIncome(dto.getCompany().getMonthlyIncome());
                
                if(!AppUtil.isNullOrEmpty(dto.getCompany().getComercialPhone())){
                    entity.setExtensionLine(dto.getCompany().getComercialPhone().getExtension());
                    
                    if(!AppUtil.isNullOrEmpty(dto.getCompany().getComercialPhone().getNumber())){
                        PhoneDetail detail = GeneralUtils.splitPhone(dto.getCompany().getComercialPhone().getNumber());
                        entity.setDdd(detail.getDdd());
                        entity.setPhone(detail.getNumber());
                    }
                    
                }
            }
            
            entity = genericDAO.update(entity);
            
            LOGGER.debug(" >> END saveGuarantorSpouseEntity ");
            return entity;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListPhoneGuarantor(GuarantorDTO dto, GuarantorEntity guarantorEntity) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveListPhoneGuarantor ");
            guarantorEntity.setListPhone(new HashSet<>());
            
            List<PhoneEntity> lista = new ArrayList<>();
            
            if(!AppUtil.isNullOrEmpty(dto.getCellphone())){
                PhoneEntity phone = populatePhoneEntity(dto.getCellphone(), PhoneTypeEnum.CELULAR, guarantorEntity);
                phone.setExtensionLine(dto.getCellphone().getExtension());
                lista.add(phone);
            }
            
            if(!AppUtil.isNullOrEmpty(dto.getFixPhone())){
                PhoneEntity phone = populatePhoneEntity(dto.getFixPhone(), PhoneTypeEnum.FIXO, guarantorEntity);
                phone.setExtensionLine(dto.getFixPhone().getExtension());
                lista.add(phone);
            }
            
            if(!AppUtil.isNullOrEmpty(dto.getCompany()) && !AppUtil.isNullOrEmpty(dto.getCompany().getComercialPhone())){
                PhoneEntity phone = populatePhoneEntity(dto.getCompany().getComercialPhone(), PhoneTypeEnum.COMERCIAL, guarantorEntity);
                phone.setExtensionLine(dto.getCompany().getComercialPhone().getExtension());
                lista.add(phone);
            }
            
            phoneDAO.deleteAllPhoneGuarantor(guarantorEntity);
            
            for (PhoneEntity phoneEntity : lista) {
                phoneEntity = phoneDAO.update(phoneEntity);
                guarantorEntity.getListPhone().add(phoneEntity);
            }
            LOGGER.debug(" >> END saveListPhoneGuarantor ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListDossierVehicleOption(SimulationDossierDTO dto, DossierEntity dossierEntity) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveListDossierVehicleOption ");
            dossierEntity.setListDossierVehicleOption(new HashSet<>());
            
            dossierVehicleOptionDAO.deleteByDossier(dossierEntity.getId());
            
            if(AppUtil.isNullOrEmpty(dto.getSimulation().getCar().getVersion().getOptions())){
                return;
            }
            
            for (VehicleOptionsDTO itemDto : dto.getSimulation().getCar().getVersion().getOptions()) {
                DossierVehicleOptionEntity entity = new DossierVehicleOptionEntity();
                entity.setDossier(dossierEntity);
                
                Long id = CriptoUtilsOmega2.decryptIdToLong(itemDto.getId());
                entity.setVehicleOptions(genericDAO.find(VehicleOptionsEntity.class, id));
                entity.setAmount(itemDto.getAmount());
                
                entity = genericDAO.update(entity);
                
                dossierEntity.getListDossierVehicleOption().add(entity);
            }
            LOGGER.debug(" >> END saveListDossierVehicleOption ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void saveListDossierVehicleAccessory(SimulationDossierDTO dto, DossierEntity dossierEntity) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveListDossierVehicleAccessory ");
            dossierEntity.setListDossierVehicleAccessory(new HashSet<>());
            
            dossierVehicleAccessoryDAO.deleteByDossier(dossierEntity.getId());
            
            if(AppUtil.isNullOrEmpty(dto.getSimulation().getCar().getVersion().getAcessories())){
                return;
            }
            
            List<DossierVehicleAccessoryEntity> lista = new ArrayList<>();
            
            for (VehicleAccessoriesDTO itemDto : dto.getSimulation().getCar().getVersion().getAcessories()) {
                DossierVehicleAccessoryEntity entity = new DossierVehicleAccessoryEntity();
                entity.setDossier(dossierEntity);
                entity.setDescription(itemDto.getDescription());
                entity.setAmount(itemDto.getAmount());
                
                lista.add(entity);
            }
            
            for (DossierVehicleAccessoryEntity entity : lista) {
                entity = genericDAO.update(entity);
                dossierEntity.getListDossierVehicleAccessory().add(entity);
            }
            LOGGER.debug(" >> END saveListDossierVehicleAccessory ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }      
    }

    private Set<SpecialVehicleTypeEntity> prepareListSpecialVehicleType(List<SpecialTypeDTO> specialTypes) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT prepareListSpecialVehicleType ");
            if(!AppUtil.isNullOrEmpty(specialTypes)){
                Set<SpecialVehicleTypeEntity> lista = new HashSet<>();
                for (SpecialTypeDTO specialTypeDTO : specialTypes) {
                    Long id = CriptoUtilsOmega2.decryptIdToLong(specialTypeDTO.getId());
                    SpecialVehicleTypeEntity entity = genericDAO.find(SpecialVehicleTypeEntity.class, id);
                    lista.add(entity);
                }
                
                return lista;
            }
            
            LOGGER.debug(" >> END prepareListSpecialVehicleType ");
            return null;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }       
    }

    private void expiryDateCalculate(DossierEntity dossierEntity) {
        LOGGER.debug(" >> INIT expiryDateCalculate ");
        if(AppUtil.isNullOrEmpty(dossierEntity.getAdp())){
            Calendar today = AppUtil.getToday();
            today.add(Calendar.DAY_OF_MONTH, 1);
            dossierEntity.setExpiryDate(today.getTime());
        }
        LOGGER.debug(" >> END expiryDateCalculate ");
    }

    private DossierVehicleEntity saveDossierVehicleEntity(SimulationDTO dto) throws UnexpectedException {
        LOGGER.debug(" >> INIT saveDossierVehicleEntity ");
        try {
            DossierVehicleEntity entity = null;
            if(!AppUtil.isNullOrEmpty(dto.getId())){
                Long dossierId = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                entity = dossierVehicleDAO.findByDossierId(dossierId);
            }
            
            if(AppUtil.isNullOrEmpty(entity)){
                entity = new DossierVehicleEntity();
            }
            
            entity.setVehicleVersion(genericDAO.find(VehicleVersionEntity.class, CriptoUtilsOmega2.decryptIdToLong(dto.getCar().getVersion().getId())));
            
            entity.setChassi(null);
            entity.setRegisterNumber(null);
            entity.setRegisterProvince(null);
            entity.setLicenseProvince(null);
            entity.setColor(null);
            entity.setRenavam(null);
            entity.setNationalCar(Boolean.TRUE);
            if(!AppUtil.isNullOrEmpty(dto.getClient().getCarDetails()) && !AppUtil.isNullOrEmpty(dto.getClient().getCarDetails().getVehicleOrigin())){
                CarDetailsDTO carDto = dto.getClient().getCarDetails();
                
                entity.setChassi(carDto.getChassiNumber());
                entity.setRegisterNumber(carDto.getRegisterNumber());
                entity.setRegisterProvince(findProvinceEntity(carDto.getRegistrationProvince()));
                entity.setLicenseProvince(findProvinceEntity(carDto.getLicensingProvince()));
                entity.setColor(findColorEntity(carDto.getVehicleColor()));
                entity.setRenavam(carDto.getRenavam());
                entity.setNationalCar("N".equals(carDto.getVehicleOrigin()));
                entity.setManufactureYear(carDto.getManufactureYear());
            }
            
            entity = genericDAO.update(entity);
            
            LOGGER.debug(" >> END prepareListSpecialVehicleType ");
            return entity;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void initializeDaos() throws UnexpectedException {
        LOGGER.debug(" >> INIT initializeDaos ");
        genericDAO = daoFactory(GenericDAO.class);
        dossierVehicleDAO = daoFactory(DossierVehicleDAO.class);
        dossierVehicleOptionDAO = daoFactory(DossierVehicleOptionDAO.class);
        dossierVehicleAccessoryDAO = daoFactory(DossierVehicleAccessoryDAO.class);
        personDAO = daoFactory(PersonDAO.class);
        financialBrandDAO = daoFactory(FinancialBrandDAO.class);
        phoneDAO = daoFactory(PhoneDAO.class);
        instalmentDAO = daoFactory(InstalmentDAO.class);
        guarantorReferenceDAO = daoFactory(GuarantorReferenceDAO.class);
        customerReferenceDAO = daoFactory(CustomerReferenceDAO.class);
        proposalServiceDAO = daoFactory(ProposalServiceDAO.class);
        proposalTaxDAO = daoFactory(ProposalTaxDAO.class);
        LOGGER.debug(" >> END initializeDaos ");
    }
    
    private void saveEventEntity(ProposalEntity proposal) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveEventEntity ");
            EventTypeEntity eventType = genericDAO.find(EventTypeEntity.class, AppConstants.EVENT_TYPE_CHANGE_STATUS);
            EventEntity entity = new EventEntity();
            
            entity.setEventType(eventType);
            entity.setDossierStatus(proposal.getDossier().getDossierStatus());
            entity.setUser(proposal.getUser());
            entity.setProposal(proposal);
            
            genericDAO.save(entity);
            
            LOGGER.debug(" >> END saveEventEntity ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private EmissionOrganismEntity findEmissionOrganismEntity(EmissionOrganismDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findEmissionOrganismEntity ");
            
            if(!AppUtil.isNullOrEmpty(dto) && !AppUtil.isNullOrEmpty(dto.getId())){
                Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
                EmissionOrganismEntity temp = genericDAO.find(EmissionOrganismEntity.class, id);
                
                LOGGER.debug(" >> END findEmissionOrganismEntity ");
                return temp;
            }
            
            LOGGER.debug(" >> END findEmissionOrganismEntity ");
            return null;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private ProfessionEntity findProfessionEntity(ProfessionDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProfessionEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                LOGGER.debug(" >> END findProfessionEntity ");
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            
            ProfessionEntity temp =  genericDAO.find(ProfessionEntity.class, id);
            LOGGER.debug(" >> END findProfessionEntity ");
            
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private OccupationEntity findOccupationEntity(OccupationDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findOccupationEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            
            OccupationEntity temp = genericDAO.find(OccupationEntity.class, id);
            
            LOGGER.debug(" >> END findOccupationEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private ProvinceEntity findProvinceEntity(ProvinceDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProvinceEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            ProvinceEntity temp = genericDAO.find(ProvinceEntity.class, id);
            
            LOGGER.debug(" >> END findProvinceEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private IndustrialSegmentEntity findIndustrialSegmentEntity(IndustrialSegmentDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findIndustrialSegmentEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            IndustrialSegmentEntity temp = genericDAO.find(IndustrialSegmentEntity.class, id);
            
            LOGGER.debug(" >> END findIndustrialSegmentEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private EmployerSizeEntity findEmployerSizeEntity(EmployerSizeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findEmployerSizeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            EmployerSizeEntity temp = genericDAO.find(EmployerSizeEntity.class, id);
            
            LOGGER.debug(" >> END findEmployerSizeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private ProofIncomeTypeEntity findProofIncomeTypeEntity(ProofIncomeTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProofIncomeTypeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            ProofIncomeTypeEntity temp = genericDAO.find(ProofIncomeTypeEntity.class, id);
            
            LOGGER.debug(" >> INIT findProofIncomeTypeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private IncomeTypeEntity findIncomeTypeEntity(IncomeTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findIncomeTypeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            IncomeTypeEntity temp = genericDAO.find(IncomeTypeEntity.class, id);
            
            LOGGER.debug(" >> END findIncomeTypeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private EconomicActivityEntity findEconomicActivityEntity(EconomicAtivityDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findEconomicActivityEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            EconomicActivityEntity temp = genericDAO.find(EconomicActivityEntity.class, id);
            
            LOGGER.debug(" >> END findEconomicActivityEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private LegalNatureEntity findLegalNatureEntity(LegalNatureDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findLegalNatureEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            LegalNatureEntity temp = genericDAO.find(LegalNatureEntity.class, id);
            
            LOGGER.debug(" >> END findLegalNatureEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private BankEntity findBankEntity(BankDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findBankEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            BankEntity temp = genericDAO.find(BankEntity.class, id);
            
            LOGGER.debug(" >> END findBankEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    

    private ColorEntity findColorEntity(ColorDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findColorEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            ColorEntity temp = genericDAO.find(ColorEntity.class, id);

            LOGGER.debug(" >> END findColorEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private CountryEntity findCountryEntity(CountryDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCountryEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            CountryEntity temp = genericDAO.find(CountryEntity.class, id);
            
            LOGGER.debug(" >> END findCountryEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private CivilStateEntity findCivilStateEntity(CivilStateDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCivilStateEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            CivilStateEntity temp = genericDAO.find(CivilStateEntity.class, id);
            
            LOGGER.debug(" >> END findCivilStateEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private EducationDegreeEntity findEducationDegreeEntity(EducationDegreeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findEducationDegreeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            EducationDegreeEntity temp = genericDAO.find(EducationDegreeEntity.class, id);
            
            LOGGER.debug(" >> END findEducationDegreeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private PoliticalExpositionEntity findPoliticalExpositionEntity(PoliticalExpositionDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findPoliticalExpositionEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            PoliticalExpositionEntity temp = genericDAO.find(PoliticalExpositionEntity.class, id);
            
            LOGGER.debug(" >> END findPoliticalExpositionEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private AddressTypeEntity findAddressTypeEntity(AddressTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAddressTypeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            AddressTypeEntity temp = genericDAO.find(AddressTypeEntity.class, id);
            
            LOGGER.debug(" >> END findAddressTypeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private ResidenceTypeEntity findResidenceTypeEntity(ResidenceTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findResidenceTypeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            ResidenceTypeEntity temp = genericDAO.find(ResidenceTypeEntity.class, id);
            
            LOGGER.debug(" >> END findResidenceTypeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    private KinshipTypeEntity findKinshipTypeEntity(KinshipTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findKinshipTypeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            KinshipTypeEntity temp = genericDAO.find(KinshipTypeEntity.class, id);
            
            LOGGER.debug(" >> END findKinshipTypeEntity ");
            return temp; 
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private BusinessRelationshipTypeEntity findBusinessRelationshipTypeEntity(BusinessRelationshipTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findBusinessRelationshipTypeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            BusinessRelationshipTypeEntity temp = genericDAO.find(BusinessRelationshipTypeEntity.class, id);
            
            LOGGER.debug(" >> END findBusinessRelationshipTypeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private DocumentTypeEntity findDocumentTypeEntity(DocumentTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findDocumentTypeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            DocumentTypeEntity temp = genericDAO.find(DocumentTypeEntity.class, id);
            
            LOGGER.debug(" >> END findDocumentTypeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private GuarantorTypeEntity findGuarantorTypeEntity(GuarantorTypeDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findGuarantorTypeEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            GuarantorTypeEntity temp = genericDAO.find(GuarantorTypeEntity.class, id);
            
            LOGGER.debug(" >> END findGuarantorTypeEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private CommissionEntity findCommissionEntity(CommissionDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCommissionEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            CommissionEntity temp = genericDAO.find(CommissionEntity.class, id);
            
            LOGGER.debug(" >> END findCommissionEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private RepackageEntity findRepackageEntity(RepackageDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findRepackageEntity ");
            if(AppUtil.isNullOrEmpty(dto) || AppUtil.isNullOrEmpty(dto.getId())){
                return null;
            }
            Long id = CriptoUtilsOmega2.decryptIdToLong(dto.getId());
            RepackageEntity temp = genericDAO.find(RepackageEntity.class, id);
            
            LOGGER.debug(" >> END findRepackageEntity ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private CalculateParametersDTO populateSimulationCalcParametersDTO(CalculationDTO dto, ProposalEntity proposalEntity, DossierEntity dossierEntity) throws UnexpectedException, CryptoException {
        
        LOGGER.debug(" >> INIT populateSimulationCalcParametersDTO ");
        
        CalculateParametersDTO parameters = new CalculateParametersDTO();
        parameters.setCurrentUser(proposalEntity.getUser());
        parameters.setSalesmanId(dossierEntity.getSalesman().getId());
        parameters.setStructureId(dossierEntity.getStructure().getId());
        
        parameters.setCommissionId(proposalEntity.getCommission().getId());
        parameters.setDelayValue(proposalEntity.getDelayValue());
        parameters.setTerm(proposalEntity.getInstalmentQuantity());
        parameters.setSaleTypeId(dossierEntity.getSaleType().getId());
        parameters.setFinanceTypeId(proposalEntity.getFinanceType().getId());
        parameters.setPersonType(dossierEntity.getCustomer().getPersonType().name());
        parameters.setVehicleVersionId(dossierEntity.getDossierVehicle().getVehicleVersion().getId());
        parameters.setProductId(proposalEntity.getProduct().getId());
        parameters.setTotalVehicleAmount(calculateTotalVehicle(dossierEntity));
        parameters.setDeposit(proposalEntity.getDeposit());
        parameters.setProvince(String.valueOf(dossierEntity.getCustomer().getAddress().getProvince().getId()));
        parameters.setTcExempt(dossierEntity.getTcExempt());
        
        parameters.setServices(dto.getServices());
        
        if(!AppUtil.isNullOrEmpty(dossierEntity.getListSpecialVehicleType())){
            parameters.setVehiclesSpecial(new ArrayList<String>());
            for (SpecialVehicleTypeEntity entity : dossierEntity.getListSpecialVehicleType()) {
                parameters.getVehiclesSpecial().add(CriptoUtils.encrypt(entity.getId()));
            }
        }

        if(!AppUtil.isNullOrEmpty(proposalEntity.getRepackage())){
            parameters.setRepackageId(proposalEntity.getRepackage().getId());
        }
        
        LOGGER.debug(" >> END populateSimulationCalcParametersDTO ");
        return parameters;
    }

    private BigDecimal calculateTotalVehicle(DossierEntity dossierEntity) {
        
        LOGGER.debug(" >> INIT calculateTotalVehicle ");
        
        BigDecimal totalVehicle = BigDecimal.ZERO;
        totalVehicle = GeneralUtils.sum(totalVehicle, dossierEntity.getVehiclePrice());
        
        if(!AppUtil.isNullOrEmpty(dossierEntity.getListDossierVehicleAccessory())){
            for (DossierVehicleAccessoryEntity entity : dossierEntity.getListDossierVehicleAccessory()) {
                totalVehicle = GeneralUtils.sum(totalVehicle, entity.getAmount());
            }
        }
        
        if(!AppUtil.isNullOrEmpty(dossierEntity.getListDossierVehicleOption())){
            for (DossierVehicleOptionEntity entity : dossierEntity.getListDossierVehicleOption()) {
                totalVehicle = GeneralUtils.sum(totalVehicle, entity.getAmount());
            }
        }
        
        LOGGER.debug(" >> END calculateTotalVehicle ");
        return totalVehicle;
    }

    private void validateDateToday(Date dt, String msg) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT validateDateToday ");
            if (AppUtil.isNullOrEmpty(dt)) {
                return;
            }
            Date today = AppUtil.truncateDate(AppUtil.getToday().getTime());
            
            if (!today.after(AppUtil.truncateDate(dt))) {
                throw new BusinessException(msg);
            }
            LOGGER.debug(" >> END validateDateToday ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private boolean isValidReference(ReferenceDTO reference) {
        LOGGER.debug(" >> INIT isValidReference ");
        if(!AppUtil.isNullOrEmpty(reference) && !AppUtil.isNullOrEmpty(reference.getName()) && !AppUtil.isNullOrEmpty(reference.getPhone())){
            return true;
        }
        LOGGER.debug(" >> END isValidReference ");
        return false;
    }
    
    private void validateDossierStatus(DossierEntity dossierEntity) throws BusinessException {       
        LOGGER.debug(" >> INIT validateDossierStatus ");
        
        List<Long> bloqued = new ArrayList<>();
        bloqued.add(AppConstants.DOSSIER_STATUS_DESISTENCIA);
        bloqued.add(AppConstants.DOSSIER_STATUS_EFETIVADA);
        bloqued.add(AppConstants.DOSSIER_STATUS_EM_ANALISE);
        bloqued.add(AppConstants.DOSSIER_STATUS_NEGADA);
        bloqued.add(AppConstants.DOSSIER_STATUS_CANCELADA);
        bloqued.add(AppConstants.DOSSIER_STATUS_VENCIDA);
        bloqued.add(AppConstants.DOSSIER_STATUS_CANCELADA_SIN);
        
        if(bloqued.contains(dossierEntity.getDossierStatus().getId())){
            throw new BusinessException(ValidateConstants.ERROR_PROPOSAL_NOT_EDIT);
        }
        
        LOGGER.debug(" >> END validateDossierStatus ");
    }
    
    
    private DossierEntity saveAprovada(DossierEntity entity, SimulationDossierDTO dto) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT saveAprovada ");
            
            DossierVehicleEntity dossierVehicle = dossierVehicleDAO.findByDossierId(entity.getId());
            
            CarDetailsDTO carDto = dto.getSimulation().getClient().getCarDetails();
            
            dossierVehicle.setChassi(carDto.getChassiNumber());
            dossierVehicle.setRegisterNumber(carDto.getRegisterNumber());
            dossierVehicle.setRegisterProvince(findProvinceEntity(carDto.getRegistrationProvince()));
            dossierVehicle.setLicenseProvince(findProvinceEntity(carDto.getLicensingProvince()));
            dossierVehicle.setColor(findColorEntity(carDto.getVehicleColor()));
            dossierVehicle.setRenavam(carDto.getRenavam());
            
            dossierVehicleDAO.save(dossierVehicle);
            
            entity.setDossierVehicle(dossierVehicle);
            
            LOGGER.debug(" >> END saveAprovada ");
            return entity;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
   
    
}

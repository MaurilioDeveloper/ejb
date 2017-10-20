package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.AppOrderUtils;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.ejb.utils.SantanderServicesUtils;
import com.rci.omega2.ejb.utils.ValidateConstants;
import com.rci.omega2.entity.AddressEntity;
import com.rci.omega2.entity.BankDetailEntity;
import com.rci.omega2.entity.CivilStateEntity;
import com.rci.omega2.entity.CustomerSpouseEntity;
import com.rci.omega2.entity.DossierEntity;
import com.rci.omega2.entity.GuarantorEntity;
import com.rci.omega2.entity.GuarantorSpouseEntity;
import com.rci.omega2.entity.PhoneEntity;
import com.rci.omega2.entity.ProposalEntity;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;
import com.rci.omega2.entity.enumeration.PhoneTypeEnum;

@Stateless
public class SantanderSubmitValidateBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(SantanderSubmitValidateBO.class);
    
    public void submitValidate(DossierEntity dossier, ProposalEntity proposal) throws UnexpectedException {
        LOGGER.debug("INIT submitValidate");
        validateDossierStatus(dossier);
        
        commomValidate(dossier, proposal);
        
        if(dossier.getCustomer().getPersonType().equals(PersonTypeEnum.PF)){
            submitValidatePf(dossier, proposal);
        } else {
            submitValidatePj(dossier, proposal);
        }
        
        guarantorValidate(dossier);
        
        LOGGER.debug("END submitValidate");
    }

    private void validateDossierStatus(DossierEntity dossierEntity) throws BusinessException {        
        
        LOGGER.debug(" >> INIT validateDossierStatus ");
        
        List<Long> bloqued = new ArrayList<>();
        bloqued.add(AppConstants.DOSSIER_STATUS_EM_ANALISE);
        bloqued.add(AppConstants.DOSSIER_STATUS_NEGADA);
        bloqued.add(AppConstants.DOSSIER_STATUS_CANCELADA);
        bloqued.add(AppConstants.DOSSIER_STATUS_APROVADA);
        bloqued.add(AppConstants.DOSSIER_STATUS_EFETIVADA);
        bloqued.add(AppConstants.DOSSIER_STATUS_DESISTENCIA);
        bloqued.add(AppConstants.DOSSIER_STATUS_VENCIDA);
        bloqued.add(AppConstants.DOSSIER_STATUS_CANCELADA_SIN);
        
        if(bloqued.contains(dossierEntity.getDossierStatus().getId())){
            throw new BusinessException(ValidateConstants.ERROR_PROPOSAL_NOT_SEND);
        }
        LOGGER.debug(" >> END validateDossierStatus ");
    }
    
    private void commomValidate(DossierEntity dossier, ProposalEntity proposal) throws BusinessException {
        
        LOGGER.debug(" >> INIT commomValidate ");
        
        if(AppUtil.isNullOrEmpty(proposal)){
            throw new BusinessException(ValidateConstants.ERROR_PROPOSAL_NOT_FOUND);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCertifiedAgent())){
            errorCertifiedAgentFactory();
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getNameClient())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_NAME);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getCpfCnpj())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_CPF_CNPJ);
        }
        
        validateAddressEntity(dossier.getCustomer().getAddress(), ValidateConstants.KEY_CUSTOMER);
        
        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getBankDetail()) && !AppUtil.isNullOrEmpty(dossier.getCustomer().getBankDetail().getBank())){
            bankValidate(dossier.getCustomer().getBankDetail(), ValidateConstants.KEY_CUSTOMER, null);
        }

        if(AppUtil.isNullOrEmpty(dossier.getDossierVehicle().getNationalCar())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_NATIONAL_CAR);
        }
        
        LOGGER.debug(" >> END commomValidate ");
    }
    
    private void submitValidatePf(DossierEntity dossier, ProposalEntity proposal) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT submitValidatePf ");
        
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getDocumentNumber())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_DOCUMENT_NUMBER);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getDocumentType())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_DOCUMENT_TYPE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getDocumentProvince())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_DOCUMENT_PROVINCE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmissionOrganism())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_DOCUMENT_EMISSION_ORGANISM);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getDocumentCountry())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_DOCUMENT_COUNTRY);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmissionDate())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_DOCUMENT_EMISSION_DATE);
        }
        
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getNaturalness())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_NATURALNESS);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getProvince())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PROVINCE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getCountry())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_COUNTRY);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getPersonGender())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PERSON_GENDER);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getDateBirth())){
            throw new BusinessException(ValidateConstants.CUSTOMER_ERROR_BIRTH);
        } else if(GeneralUtils.calculateAge(dossier.getCustomer().getDateBirth()) <= 18){
            throw new BusinessException(ValidateConstants.CUSTOMER_ERROR_BIRTH_18);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getCivilState())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_CIVIL_STATE);
        }
        if(conjugeRequired(dossier.getCustomer().getCivilState())){
            validateCustomerSpouseEntity(dossier.getCustomer().getCustomerSpouse());
        }
        if(!haveContactInformation(dossier)){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_CONTACT);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getPersonalPhoneType())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PERSONAL_PHONE_TYPE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getPoliticalExposition())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_POLITICAL_EXPOSITION);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEducationDegree())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EDUCATION_DEGREE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getMotherName())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_MOTHER_NAME);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getIncome())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_INCOME);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getMailingAddressType())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_MAILING_ADDRESS_TYPE);
        }
        if(!haveValidPhone(dossier.getCustomer().getListPhone(), PhoneTypeEnum.COMERCIAL)){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_COMERCIAL_PHONE_TYPE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getAddressSince())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_ADDRESS_SINCE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getResidenceType())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_RESIDENCE_TYPE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getListCustomerReference())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_REFERENCE);
        }

        // EMPLOYER
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getIncomeType())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_INCOME_TYPE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getProofIncomeType())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PROOF_INCOME_TYPE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getEmployerName())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EMPLOYER_NAME);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getProfession())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EMPLOYER_PROFESSION);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getAdmissionDate())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_ADMISSION_DATE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getOccupation())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_OCCUPATION);
        }
        
        // EMPLOYER ADDRESS
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployerAddress().getProvince())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_PROVINCE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployerAddress().getCity())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_CITY);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployerAddress().getNeighborhood())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_NEIGHBORHOOD);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployerAddress().getPostCode())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_POST_CODE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployerAddress().getNumber())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EMPLOYER_ADDRESS_NUMBER);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployerAddress().getAddress())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_EMPLOYER_ADDRESS);
        }
        
        LOGGER.debug(" >> END submitValidatePf ");
        
    }
    
    private void submitValidatePj(DossierEntity dossier, ProposalEntity proposal) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT submitValidatePj ");
        
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getDateBirth())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PJ_BIRTH);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getEmployer().getEmployerSize())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PJ_EMPLOYER_SIZE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getIncome())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PJ_INCOME);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getLegalNature())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PJ_LEGAL_NATURE);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getBuildingOwner())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PJ_BUILDING_OWNER);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getCustomerEconomicActivity())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PJ_ECONOMIC_ACTIVITY);
        }
        if(AppUtil.isNullOrEmpty(dossier.getCustomer().getCustomerIndustrialSegment())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_PJ_INDUSTRIAL_SEGMENT);
        }
        
        LOGGER.debug(" >> END submitValidatePj ");
    }
    
    private void guarantorValidate(DossierEntity dossier) throws BusinessException {
        
        LOGGER.debug(" >> INIT guarantorValidate ");
        
        if(AppUtil.isNullOrEmpty(dossier.getListGuarantor())){
            return;
        }
        
        ArrayList<GuarantorEntity> lista = AppOrderUtils.ordinateGuarantorList(dossier.getListGuarantor());
        int count = 0;
        for (GuarantorEntity guarantorEntity : lista) {
            String msg = count++ == 0 ? ValidateConstants.GUARANTOR_01 : ValidateConstants.GUARANTOR_02;
            
            if(!AppUtil.isNullOrEmpty(guarantorEntity.getBankDetail()) && !AppUtil.isNullOrEmpty(guarantorEntity.getBankDetail().getBank())){
                bankValidate(guarantorEntity.getBankDetail(), ValidateConstants.KEY_GUARANTOR, msg);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getNameClient())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_NAME);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getCpfCnpj())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_CPF);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getMotherName())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_MOTHER_NAME);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getDateBirth())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_DATE_BIRTH);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getProvince())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_PROVINCE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getCountry())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_COUNTRY);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getNaturalness())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_NATURALNESS);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getPersonGender())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_PERSON_GENDER);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmissionOrganism())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMISSION_ORGANISM);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getCivilState())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_CIVIL_STATE);
            }
            if(conjugeRequired(guarantorEntity.getCivilState())){
                validateGuarantorSpouseEntity(guarantorEntity.getGuarantorSpouse(), msg);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getKinshipType())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_KINSHIP_TYPE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEducationDegree())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EDUCATION_DEGREE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getPoliticalExposition())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_POLITICAL_EXPOSITION);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getIncome())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_INCOME);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getMailingAddressType())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_MAILING_ADDRESS_TYPE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getResidenceType())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_RESIDENCE_TYPE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getAddressSince())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_ADDRESS_SINCE);
            }
            try {
                validateAddressEntity(guarantorEntity.getAddress(), ValidateConstants.KEY_GUARANTOR);
            } catch (BusinessException e) {
                throw new BusinessException(msg + e.getMessage());
            }
            if(!haveValidPhone(guarantorEntity.getListPhone(), PhoneTypeEnum.FIXO)){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_FIXO_PHONE_TYPE);
            }
            if(!haveValidPhone(guarantorEntity.getListPhone(), PhoneTypeEnum.COMERCIAL)){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_COMERCIAL_PHONE_TYPE);
            }
            
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployer())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getEmployerName())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_NAME);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getProfession())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_PROFESSION);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getOccupation())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_OCCUPATION);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getIncomeType())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_INCOME_TYPE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployer().getProofIncomeType())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_PROOF_INCOME_TYPE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployerAddress().getProvince())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_PROVINCE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployerAddress().getPostCode())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_POST_CODE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployerAddress().getNumber())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_NUMBER);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployerAddress().getAddress())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_ADDRESS);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployerAddress().getNeighborhood())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_NEIGHBORHOOD);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmployerAddress().getCity())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_EMPLOYER_ADDRESS_CITY);
            }
            
            if(AppUtil.isNullOrEmpty(guarantorEntity.getDocumentType())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_DOCUMENT_TYPE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getDocumentNumber())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_DOCUMENT_NUMBER);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getEmissionDate())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_DOCUMENT_EMISSION_DATE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getDocumentProvince())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_DOCUMENT_PROVINCE);
            }
            if(AppUtil.isNullOrEmpty(guarantorEntity.getDocumentCountry())){
                throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_DOCUMENT_COUNTRY);
            }
            
        }
        
        LOGGER.debug(" >> END guarantorValidate ");
    }
    
    private void bankValidate(BankDetailEntity bankDetail, Integer key, String msgGuarantor) throws BusinessException {
        
        LOGGER.debug(" >> INIT bankValidate ");
        
        String msg = ValidateConstants.CUSTOMER_REQUIRED_BANK_DETAILS;
        if(key.equals(ValidateConstants.KEY_GUARANTOR)){
            msg = msgGuarantor + ValidateConstants.GUARANTOR_REQUIRED_BANK_DETAILS;
        }
        
        if(!AppUtil.isNullOrEmpty(bankDetail) && !AppUtil.isNullOrEmpty(bankDetail.getBank())){
            if(AppUtil.isNullOrEmpty(bankDetail.getBranch())){
                throw new BusinessException(msg + ValidateConstants.BANK_DETAILS_BRANCH);
            }
            if(AppUtil.isNullOrEmpty(bankDetail.getAccount())){
                throw new BusinessException(msg + ValidateConstants.BANK_DETAILS_ACCOUNT);
            }
            if(AppUtil.isNullOrEmpty(bankDetail.getAccountOpeningDate())){
                throw new BusinessException(msg + ValidateConstants.BANK_DETAILS_ACCOUNT_OPENING_DATE);
            }
        }
        
        LOGGER.debug(" >> END bankValidate ");
    }

    private boolean conjugeRequired(CivilStateEntity civilState) {
        
        LOGGER.debug(" >> INIT conjugeRequired ");
        
        boolean temp = civilState.getId().equals(AppConstants.CIVIL_STATE_CASADO) || civilState.getId().equals(AppConstants.CIVIL_STATE_COMPANHEIRO);
        
        LOGGER.debug(" >> END conjugeRequired ");
        return temp;
    }
    
    private boolean haveValidPhone(Set<PhoneEntity> phones, PhoneTypeEnum phoneType) {
        
        LOGGER.debug(" >> INIT haveValidPhone ");
        if(AppUtil.isNullOrEmpty(phones) || AppUtil.isNullOrEmpty(phoneType)){
            return false;
        }
        for (PhoneEntity phone : phones) {
            if(!isValidPhone(phone)){
                continue;
            }
            
            if(phone.getPhoneType().equals(phoneType)){
                return true;
            }
        }
        
        LOGGER.debug(" >> END haveValidPhone ");
        return false;
    }
    
    private boolean haveContactInformation(DossierEntity dossier) {
        
        LOGGER.debug(" >> INIT haveContactInformation ");
        if(!AppUtil.isNullOrEmpty(dossier.getCustomer().getEmail())){
            return true;
        }
        
        for (PhoneEntity phone : dossier.getCustomer().getListPhone()) {
            if(!isValidPhone(phone)){
                continue;
            }
            if(phone.getPhoneType().equals(PhoneTypeEnum.FIXO)){
                return true;
            }
            if(phone.getPhoneType().equals(PhoneTypeEnum.CELULAR)){
                return true;
            }
        }
        
        LOGGER.debug(" >> END haveContactInformation ");
        return false;
    }
    
    public Boolean isValidPhone(PhoneEntity phone){
        return !AppUtil.isNullOrEmpty(phone) && 
               !AppUtil.isNullOrEmpty(SantanderServicesUtils.formatDDD(phone.getDdd())) && 
               !AppUtil.isNullOrEmpty(SantanderServicesUtils.formatPhone(phone.getPhone()));
    }
    
    private void validateCustomerSpouseEntity(CustomerSpouseEntity spouse) throws BusinessException {
        
        LOGGER.debug(" >> INIT validateCustomerSpouseEntity ");
        
        if(AppUtil.isNullOrEmpty(spouse)){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_SPOUSE);
        }
        if(AppUtil.isNullOrEmpty(spouse.getNameClient())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_SPOUSE_NAME);
        }
        if(AppUtil.isNullOrEmpty(spouse.getCpf())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_SPOUSE_CPF);
        }
        if(AppUtil.isNullOrEmpty(spouse.getPersonGender())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_SPOUSE_PERSON_GENDER);
        }
        if(AppUtil.isNullOrEmpty(spouse.getDateBirth())){
            throw new BusinessException(ValidateConstants.CUSTOMER_REQUIRED_SPOUSE_DATE_BIRTH);
        }
        
        LOGGER.debug(" >> END validateCustomerSpouseEntity ");
    }
    
    private void validateGuarantorSpouseEntity(GuarantorSpouseEntity spouse, String msg) throws BusinessException {
        
        LOGGER.debug(" >> INIT validateGuarantorSpouseEntity ");
        if(AppUtil.isNullOrEmpty(spouse)){
            throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_SPOUSE);
        }
        if(AppUtil.isNullOrEmpty(spouse.getNameClient())){
            throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_SPOUSE_NAME);
        }
        if(AppUtil.isNullOrEmpty(spouse.getCpf())){
            throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_SPOUSE_CPF);
        }
        if(AppUtil.isNullOrEmpty(spouse.getPersonGender())){
            throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_SPOUSE_PERSON_GENDER);
        }
        if(AppUtil.isNullOrEmpty(spouse.getDateBirth())){
            throw new BusinessException(msg + ValidateConstants.GUARANTOR_REQUIRED_SPOUSE_DATE_BIRTH);
        }
        
        LOGGER.debug(" >> END validateGuarantorSpouseEntity ");
    }
    
    private void validateAddressEntity(AddressEntity entity, Integer key) throws BusinessException {
        
        LOGGER.debug(" >> INIT validateAddressEntity ");
        
        StringBuilder msg = new StringBuilder();
        
        if(AppUtil.isNullOrEmpty(entity.getPostCode())){
            if(key.equals(ValidateConstants.KEY_CUSTOMER)){
                msg.append(ValidateConstants.CUSTOMER_REQUIRED_ADDRESS_POST_CODE);
            } else {
                msg.append(ValidateConstants.GUARANTOR_REQUIRED_ADDRESS_POST_CODE);
            }
            throw new BusinessException(msg.toString());
        }
        if(AppUtil.isNullOrEmpty(entity.getNumber())){
            if(key.equals(ValidateConstants.KEY_CUSTOMER)){
                msg.append(ValidateConstants.CUSTOMER_REQUIRED_ADDRESS_NUMBER);
            } else {
                msg.append(ValidateConstants.GUARANTOR_REQUIRED_ADDRESS_NUMBER);
            }
            throw new BusinessException(msg.toString());
        }
        if(AppUtil.isNullOrEmpty(entity.getAddress())){
            if(key.equals(ValidateConstants.KEY_CUSTOMER)){
                msg.append(ValidateConstants.CUSTOMER_REQUIRED_ADDRESS);
            } else {
                msg.append(ValidateConstants.GUARANTOR_REQUIRED_ADDRESS);
            }
            throw new BusinessException(msg.toString());
        }
        if(AppUtil.isNullOrEmpty(entity.getNeighborhood())){
            if(key.equals(ValidateConstants.KEY_CUSTOMER)){
                msg.append(ValidateConstants.CUSTOMER_REQUIRED_ADDRESS_NEIGHBORHOOD);
            } else {
                msg.append(ValidateConstants.GUARANTOR_REQUIRED_ADDRESS_NEIGHBORHOOD);
            }
            throw new BusinessException(msg.toString());
        }
        if(AppUtil.isNullOrEmpty(entity.getCity())){
            if(key.equals(ValidateConstants.KEY_CUSTOMER)){
                msg.append(ValidateConstants.CUSTOMER_REQUIRED_ADDRESS_CITY);
            } else {
                msg.append(ValidateConstants.GUARANTOR_REQUIRED_ADDRESS_CITY);
            }
            throw new BusinessException(msg.toString());
        }
        if(AppUtil.isNullOrEmpty(entity.getProvince())){
            if(key.equals(ValidateConstants.KEY_CUSTOMER)){
                msg.append(ValidateConstants.CUSTOMER_REQUIRED_ADDRESS_PROVINCE);
            } else {
                msg.append(ValidateConstants.GUARANTOR_REQUIRED_ADDRESS_PROVINCE);
            }
            throw new BusinessException(msg.toString());
        }
        
        LOGGER.debug(" >> END validateAddressEntity ");
    }
    
    private void errorCertifiedAgentFactory() throws BusinessException {
        throw new BusinessException(ValidateConstants.REQUIRED_CERTIFIED_AGENT);
    }
    
}

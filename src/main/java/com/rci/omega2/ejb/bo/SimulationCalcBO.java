package com.rci.omega2.ejb.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.GenericDAO;
import com.rci.omega2.ejb.dao.RepackageDAO;
import com.rci.omega2.ejb.dto.CalculateDTO;
import com.rci.omega2.ejb.dto.CalculateParametersDTO;
import com.rci.omega2.ejb.dto.InstalmentGroupDTO;
import com.rci.omega2.ejb.dto.ProductCoeficientDTO;
import com.rci.omega2.ejb.dto.TaxDTO;
import com.rci.omega2.ejb.dto.simulation.InstalmentDTO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.ejb.utils.GeneralUtils;
import com.rci.omega2.ejb.utils.ValidateConstants;
import com.rci.omega2.entity.RepackagePlanEntity;
import com.rci.omega2.entity.SaleTypeEntity;
import com.rci.omega2.entity.ServiceEntity;
import com.rci.omega2.entity.VehicleVersionEntity;

@Stateless
public class SimulationCalcBO extends BaseBO {
    
    private static final Logger LOGGER = LogManager.getLogger(SimulationCalcBO.class);
    
    @EJB
    private ProductCoefficientBO productCoefficientBO;
    @EJB
    private TaxBO taxBO;
    @EJB
    private ServiceBO serviceBO;
    
    public CalculateDTO calculate(CalculateParametersDTO parameters) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT calculate ");
            CalculateDTO dto = new CalculateDTO();
            dto.setServices(parameters.getServices());
            dto.setVehiclePrice(parameters.getTotalVehicleAmount());

            // Valida campos obrigatórios
            requestValidate(parameters);
            
            BigDecimal spf = null;
            BigDecimal totalService = BigDecimal.ZERO;
            if(!AppUtil.isNullOrEmpty(parameters.getServices())){
                for (com.rci.omega2.ejb.dto.simulation.ServiceDTO service : parameters.getServices()) {
                    if(!service.getServiceTypeId().equals(AppConstants.SPF_KEY_ID)){
                        totalService = GeneralUtils.sum(totalService, service.getAmount());
                    } else {
                        ServiceEntity temp = serviceBO.findById(CriptoUtilsOmega2.decryptIdToLong(service.getId()));
                        spf = temp.getPercentage();
                    }
                }
            }
            
            // Calcula o percentual de entrada
            BigDecimal entryPercent = GeneralUtils.percentualCalculate(parameters.getDeposit(), GeneralUtils.sum(totalService, parameters.getTotalVehicleAmount()));
            entryPercent = GeneralUtils.roundPrice(entryPercent);
            
            // Consulta o coeficiente
            ProductCoeficientDTO productCoeficientDTO = productCoefficientBO.findCoefficientAndCoefficientTax(parameters.getCommissionId(), parameters.getProductId(), parameters.getPersonType(), parameters.getTerm(), parameters.getDelayValue(), entryPercent);
            if(AppUtil.isNullOrEmpty(productCoeficientDTO)){
                dto.setCalculateOk(Boolean.FALSE);
                return dto;
            }
            dto.setDepositRecalculate(productCoeficientDTO.getDepositRecalculate());
            dto.setCoeffcientId(productCoeficientDTO.getCoeffcientId());
            dto.setTaxCoefficient(productCoeficientDTO.getTaxCoefficient());
            dto.setCoefficient(productCoeficientDTO.getCoefficient());
            
            // Ajusta o valor de entrada pelo percentual
            dto.setDeposit(parameters.getDeposit());
            dto.setDepositPercent(entryPercent);
            
            if(productCoeficientDTO.getDepositRecalculate()){
                // Foi ajustado o percentual de entrada e por este motivo muda o valor de entrada
                dto.setDepositPercent(productCoeficientDTO.getDepositPercent());
                dto.setDeposit(BigDecimal.ZERO);
                if(productCoeficientDTO.getDepositPercent().compareTo(BigDecimal.ZERO) > 0){
                    dto.setDeposit(GeneralUtils.sum(totalService, parameters.getTotalVehicleAmount()).multiply(productCoeficientDTO.getDepositPercent().movePointLeft(AppConstants.DECIMAL_2)));
                }
            }
            
            // Busca as taxas
            BigDecimal totalTax = BigDecimal.ZERO;
            List<TaxDTO> taxes = retrieveTaxes(parameters);
            dto.setTaxes(taxes);
            for (TaxDTO taxDTO : taxes) {
                totalTax = GeneralUtils.sum(totalTax, taxDTO.getValue());
            }
            
            // Calcula o valor total. (RN06).
            BigDecimal totalAmount = GeneralUtils.sum(totalTax, parameters.getTotalVehicleAmount());
            dto.setTotalAmount(totalAmount);
            
            // Calcula o valor financiado. (RN07).
            BigDecimal totalFinanced = GeneralUtils.sum(totalAmount, totalService).subtract(dto.getDeposit());
            dto.setTotalAmountFinanced(totalFinanced);
            
            // Calcula o valor das Parcelas. (RN08).
            BigDecimal monthlyAmount = productCoeficientDTO.getCoefficient().multiply(totalFinanced);
            if(!AppUtil.isNullOrEmpty(spf)){
                monthlyAmount = monthlyAmount.multiply(spf);
            }
            
            monthlyAmount = GeneralUtils.roundPrice(monthlyAmount);
            dto.setInstalmentAmount(monthlyAmount);
            
            populateParcels(dto, parameters);
            
            dto.setCalculateOk(Boolean.TRUE);
            
            LOGGER.debug(" >> END calculate ");
            return dto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private void populateParcels(CalculateDTO dto, CalculateParametersDTO parameters) throws UnexpectedException {
        LOGGER.debug(" >> INIT populateParcels ");
        dto.setListInstalmentGroup(new ArrayList<>());
        List<InstalmentDTO> parcels = new ArrayList<InstalmentDTO>();
        if (parameters.getFinanceTypeId().equals(AppConstants.TYPE_FINANCING_CDC_FLEX)) {
            // CDC FLEXXX
            RepackageDAO daorep = daoFactory(RepackageDAO.class);
            List<RepackagePlanEntity> list = daorep.getRepackagePlanByPackageId(parameters.getRepackageId());
            if(list.size() != parameters.getTerm()){
                throw new BusinessException(ValidateConstants.CALCULATE_ERROR_REPACKAGE);
            }
            
            for (RepackagePlanEntity item : list) {
                InstalmentDTO parcelDTO = new InstalmentDTO();
                parcelDTO.setNumberInstallment(item.getInstalment());
                parcelDTO.setAmount(dto.getInstalmentAmount().multiply(item.getFactor()));
                parcelDTO.setFactor(item.getFactor());

                parcels.add(parcelDTO);
            }
        } else {
            // NORMAL
            for (int i = 0; i < parameters.getTerm(); i++) {
                InstalmentDTO parcelDTO = new InstalmentDTO();
                parcelDTO.setNumberInstallment(i + 1);
                parcelDTO.setAmount(dto.getInstalmentAmount());
                parcelDTO.setFactor(BigDecimal.ONE);
                
                parcels.add(parcelDTO);
            }
        }
        
        Map<BigDecimal, InstalmentGroupDTO> mapParcelGroup = new HashMap<>();
        
        Calendar calInstalment = Calendar.getInstance();
        calInstalment.add(Calendar.DATE, parameters.getDelayValue());
        
        List<Date> dates = GeneralUtils.calculateInstalmentDate(calInstalment, parameters.getTerm());
        for (int i = 0; i < parcels.size(); i++) {
            InstalmentDTO instalment = parcels.get(i);
            
            if(!mapParcelGroup.containsKey(instalment.getAmount())){
                InstalmentGroupDTO temp = new InstalmentGroupDTO();
                temp.setInstalmentAmount(instalment.getAmount());
                temp.setQuantity(0);
                dto.getListInstalmentGroup().add(temp);
                
                mapParcelGroup.put(instalment.getAmount(), temp);
            }
            
            InstalmentGroupDTO temp = mapParcelGroup.get(instalment.getAmount());
            temp.setQuantity(temp.getQuantity() + 1);
            
            if(AppUtil.isNullOrEmpty(instalment.getDueDate())){
                instalment.setDueDate(dates.get(i));
            }
        }
        
        ordinateListParcelGroup(dto);
        
        dto.setListInstalment(parcels);
        
        LOGGER.debug(" >> END populateParcels ");
    }
    
    private void requestValidate(CalculateParametersDTO parameters) throws UnexpectedException {
        LOGGER.debug(" >> INIT requestValidate ");
        if(parameters.getFinanceTypeId().equals(AppConstants.TYPE_FINANCING_CDC_FLEX)){
            if(AppUtil.isNullOrEmpty(parameters.getRepackageId())){
                throw new BusinessException(ValidateConstants.CALCULATE_REQUIRED_REPACKAGE);
            }
            
            RepackageDAO daorep = daoFactory(RepackageDAO.class);
            parameters.setListRepackagePlan(daorep.getRepackagePlanByPackageId(parameters.getRepackageId()));
            if(parameters.getListRepackagePlan().size() != parameters.getTerm()){
                throw new BusinessException(ValidateConstants.CALCULATE_ERROR_REPACKAGE);
            }
        }
        
        LOGGER.debug(" >> END requestValidate ");
    }

    private List<TaxDTO> retrieveTaxes(CalculateParametersDTO parameters) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT retrieveTaxes ");
            GenericDAO genericDAO = daoFactory(GenericDAO.class);
            SaleTypeEntity saleType = genericDAO.find(SaleTypeEntity.class, parameters.getSaleTypeId());
            
            List<TaxDTO> taxes = new ArrayList<>();
            TaxDTO tax = null;
            
            if(!parameters.getTcExempt()){
                tax = taxBO.findTaxTC(parameters.getPersonType());
                if(!AppUtil.isNullOrEmpty(tax)){
                    taxes.add(tax);
                }
            }
            
            if(GeneralUtils.isSaleTypeNewVehicle(saleType)){
                VehicleVersionEntity vehicleVersion = genericDAO.find(VehicleVersionEntity.class, parameters.getSaleTypeId());
                
                tax = taxBO.findTaxTR(CriptoUtilsOmega2.encrypt(parameters.getProvince()), vehicleVersion.getVehicleGender().name(), parameters.getVehiclesSpecial());
                if(!AppUtil.isNullOrEmpty(tax)){
                    taxes.add(tax);
                }
            } else {
                tax = taxBO.findTaxTAB();
                if(!AppUtil.isNullOrEmpty(tax)){ 
                    taxes.add(tax);
                }
            }
            
            LOGGER.debug(" >> END retrieveTaxes ");
            return taxes; 
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    private void ordinateListParcelGroup(CalculateDTO dto) {
        Collections.sort(dto.getListInstalmentGroup(), new Comparator<InstalmentGroupDTO>() {
            @Override
            public int compare(InstalmentGroupDTO ob1, InstalmentGroupDTO ob2) {
                return ob2.getQuantity().compareTo(ob1.getQuantity());
            }
        });
    }
    
}

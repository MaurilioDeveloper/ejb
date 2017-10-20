package com.rci.omega2.ejb.bo;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dao.ProductCoefficientDAO;
import com.rci.omega2.ejb.dto.CommissionDTO;
import com.rci.omega2.ejb.dto.ProductCoeficientDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.enumeration.PersonTypeEnum;

@Stateless
public class ProductCoefficientBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(ProductCoefficientBO.class);

    public ProductCoeficientDTO findCoefficientAndCoefficientTax(Long commissionId, Long productId, String personType, Integer term, Integer delayValue, BigDecimal entryPercent) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findCoefficientAndCoefficientTax ");
            ProductCoefficientDAO dao = daoFactory(ProductCoefficientDAO.class);
            
            List<ProductCoeficientDTO> list = dao.findCoefficientAndTaxCoefficient(commissionId, productId, personType, term, delayValue);
            
            ProductCoeficientDTO dto = findCoefficientFromList(entryPercent, list);
            
            LOGGER.debug(" >> END findCoefficientAndCoefficientTax ");
            return dto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public BigDecimal findMinDepositPercentFromCoefcient(Long commissionId,Long productId, String personType, Integer term, Integer delayValue) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findMinDepositPercentFromCoefcient ");
            
            ProductCoefficientDAO dao = daoFactory(ProductCoefficientDAO.class);
            BigDecimal temp = dao.findMinDepositPercent(commissionId, productId, personType, term, delayValue);
            
            LOGGER.debug(" >> END findMinDepositPercentFromCoefcient ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    private ProductCoeficientDTO findCoefficientFromList(BigDecimal entryPercent, List<ProductCoeficientDTO> list) {
        
        LOGGER.debug(" >> INIT findCoefficientFromList ");
        if(AppUtil.isNullOrEmpty(list)){
            return null;
        }
        
        ProductCoeficientDTO coefficient = null;
        
        BigDecimal downPercent = list.get(0).getDepositPercent();
        if(downPercent.compareTo(entryPercent) >= 0){
            coefficient = list.get(0);
            coefficient.setDepositRecalculate(Boolean.TRUE);
            return coefficient;
        }
        
        BigDecimal topPercent = list.get(list.size() - 1).getDepositPercent();
        if(topPercent.compareTo(entryPercent) <= 0){
            coefficient = list.get(list.size() - 1);
            coefficient.setDepositRecalculate(Boolean.TRUE);
            return coefficient;
        }
        
        for (ProductCoeficientDTO entity : list) {
            if(entity.getDepositPercent().compareTo(entryPercent) < 0){
                coefficient = entity;
                entity.setDepositPercent(entryPercent);
            }
        }
        
        LOGGER.debug(" >> END findCoefficientFromList ");
        return coefficient;
    }

    public List<Integer> findDelayValues(Long productId, PersonTypeEnum personType) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findDelayValues ");
            
            ProductCoefficientDAO dao = daoFactory(ProductCoefficientDAO.class);
            List<Integer> temp = dao.findDelayValues(productId, personType);
            
            LOGGER.debug(" >> END findDelayValues ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }

    public List<CommissionDTO> findCommissions(Long productId, String personType) throws UnexpectedException {

        try {
            ProductCoefficientDAO dao = super.daoFactory(ProductCoefficientDAO.class);
            List<CommissionDTO> listReturn = dao.findCommissions(productId, personType);
            return listReturn;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public List<Integer> findTerms(Long productId, PersonTypeEnum personType) throws UnexpectedException {
        try {
            ProductCoefficientDAO dao = daoFactory(ProductCoefficientDAO.class);

            return dao.findTerms(productId, personType);
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}

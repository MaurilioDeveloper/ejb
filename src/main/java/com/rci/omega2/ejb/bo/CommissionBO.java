package com.rci.omega2.ejb.bo;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.CommissionDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.CommissionEntity;

@Stateless
public class CommissionBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(CommissionBO.class);

    public CommissionEntity findCommissionByImportCode(String importCode) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findCommissionByImportCode ");
            
            CommissionDAO dao = daoFactory(CommissionDAO.class);
            CommissionEntity temp = dao.findCommissionByImportCode(importCode);
            
            LOGGER.debug(" >> END findCommissionByImportCode ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}

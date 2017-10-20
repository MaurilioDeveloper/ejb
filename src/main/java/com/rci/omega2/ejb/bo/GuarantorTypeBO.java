package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.GuarantorTypeDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.GuarantorTypeEntity;

@Stateless
public class GuarantorTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(GuarantorTypeBO.class);

    public List<GuarantorTypeEntity> findAll() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAll ");
            
            GuarantorTypeDAO dao = daoFactory(GuarantorTypeDAO.class);
            List<GuarantorTypeEntity> temp = dao.findAll(GuarantorTypeEntity.class);
            
            LOGGER.debug(" >> END findAll ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    
    /**
     * 
     * @param guarantorTypeById
     * @return
     * @throws UnexpectedException
     */
    public GuarantorTypeEntity findGuarantorTypeById(Long guarantorTypeById) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findGuarantorTypeById ");
            
            GuarantorTypeDAO dao = daoFactory(GuarantorTypeDAO.class);
            GuarantorTypeEntity guarantorType = dao.find(GuarantorTypeEntity.class, guarantorTypeById);
            
            LOGGER.debug(" >> END findGuarantorTypeById ");
            return guarantorType;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}

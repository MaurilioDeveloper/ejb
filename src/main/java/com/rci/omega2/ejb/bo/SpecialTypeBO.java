package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.SpecialTypeDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.SpecialVehicleTypeEntity;

@Stateless
public class SpecialTypeBO extends BaseBO{
    
private static final Logger LOGGER = LogManager.getLogger(SpecialTypeBO.class);
    
 
    public List<SpecialVehicleTypeEntity> findAllSpecialType()throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllSpecialType ");
            SpecialTypeDAO dao = daoFactory(SpecialTypeDAO.class);
            List<SpecialVehicleTypeEntity> temp = dao.findAll(SpecialVehicleTypeEntity.class);    
            
            LOGGER.debug(" >> END findAllSpecialType ");
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
     * @param description
     * @return
     * @throws UnexpectedException
     */
    public SpecialVehicleTypeEntity findSpecialVehicleTypeByDescription(String description) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSpecialVehicleTypeByDescription ");
            SpecialTypeDAO dao = daoFactory(SpecialTypeDAO.class);
            SpecialVehicleTypeEntity temp = dao.findSpecialVehicleTypeByDescription(description);
            
            LOGGER.debug(" >> END findSpecialVehicleTypeByDescription ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}

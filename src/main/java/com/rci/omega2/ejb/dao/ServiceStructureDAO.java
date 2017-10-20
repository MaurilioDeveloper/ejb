package com.rci.omega2.ejb.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ServiceStructureEntity;

public class ServiceStructureDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(ServiceStructureDAO.class);

    public void updateService(ServiceStructureEntity entity) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT updateService ");
            super.update(entity);
            LOGGER.debug(" >> END updateService ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    public void saveService(ServiceStructureEntity entity) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT saveService ");
            super.save(entity);
            LOGGER.debug(" >> END saveService ");
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
}

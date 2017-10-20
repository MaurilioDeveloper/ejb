package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.SpecialVehicleTypeEntity;

public class SpecialTypeDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(SpecialTypeDAO.class);

    public void updateService(SpecialVehicleTypeEntity entity) throws UnexpectedException{
        
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
    
    public void saveService(SpecialVehicleTypeEntity entity) throws UnexpectedException{
        
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
    
    /**
     * 
     * @param description
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public SpecialVehicleTypeEntity findSpecialVehicleTypeByDescription(String description) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findSpecialVehicleTypeByDescription ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT sv FROM SpecialVehicleTypeEntity sv      ");
            sql.append("    WHERE upper(sv.description) = :description   ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("description", description);

            List<SpecialVehicleTypeEntity> ls = query.getResultList();

            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findSpecialVehicleTypeByDescription ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}

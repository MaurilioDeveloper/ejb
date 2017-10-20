package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ColorEntity;

public class ColorDAO extends BaseDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(ColorDAO.class);

    @SuppressWarnings("unchecked")
    public ColorEntity findColorByDescription(String description) throws UnexpectedException {
       
        try {
            LOGGER.debug(" >> INIT findColorByDescription ");
            
            StringBuilder sql = new  StringBuilder();
            
            sql.append("    SELECT cl FROM ColorEntity cl                   ");
            sql.append("    WHERE upper(cl.description) = :description      ");
        
            Query query =  super.getEntityManager().createQuery(sql.toString());
            query.setParameter("description", description);
            
            List<ColorEntity> ls = query.getResultList();
            
            if(!ls.isEmpty()){
                return ls.get(0);    
            }
            
            LOGGER.debug(" >> END findColorByDescription ");
            return null;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }

}
